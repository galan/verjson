package de.galan.verjson.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.galan.commons.logging.Logr;
import de.galan.commons.util.Pair;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class ObjectMapperFactory {

	private static final Logger LOG = Logr.get();


	public ObjectMapper create(Versions versions) {
		ObjectMapper result = new ObjectMapper();
		SimpleModule module = new SimpleModule("VerjsonModule");
		for (JsonSerializer<?> serializer: versions.getSerializer()) {
			module.addSerializer(serializer);
		}
		for (JsonDeserializer deserializer: versions.getDeserializer()) {
			Method method = null;
			try {
				method = deserializer.getClass().getMethod("deserialize", JsonParser.class, DeserializationContext.class);
				module.addDeserializer(method.getReturnType(), deserializer);
			}
			catch (NullPointerException | NoSuchMethodException | SecurityException ex) {
				String methodName = (method == null) ? "null" : method.getName();
				String returnTypeName = (method == null || method.getReturnType() == null) ? "null" : method.getReturnType().toString();
				LOG.error("Unable to register deserializer for Class<" + returnTypeName + ">." + methodName + "(..)", ex);
			}
		}
		result.registerModule(module);

		for (Class<?> parentClass: versions.getRegisteredSubclasses().keySet()) {
			Class<?> mixin = generateMixIn(parentClass, versions.getRegisteredSubclasses().get(parentClass));
			result.addMixInAnnotations(parentClass, mixin);
		}

		return result;
	}


	protected static Class<?> generateMixIn(Class<?> parent, Set<Pair<Class<?>, String>> childs) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(parent.getPackage().getName() + ".Gen" + parent.getSimpleName() + "MixIn"); // TODO check inner classes in parentname
		ClassFile cf = cc.getClassFile();
		cf.setMajorVersion(ClassFile.JAVA_7);
		cf.setMinorVersion(0);
		ConstPool cp = cf.getConstPool();

		// @JsonTypeInfo
		Annotation annotationInfo = new Annotation(JsonTypeInfo.class.getName(), cp);
		EnumMemberValue enumId = new EnumMemberValue(cp);
		enumId.setType(JsonTypeInfo.Id.class.getName());
		enumId.setValue(JsonTypeInfo.Id.NAME.toString());
		annotationInfo.addMemberValue("use", enumId);

		EnumMemberValue enumAs = new EnumMemberValue(cp);
		enumAs.setType(JsonTypeInfo.As.class.getName());
		enumAs.setValue(As.PROPERTY.toString());
		annotationInfo.addMemberValue("include", enumAs);

		annotationInfo.addMemberValue("property", new StringMemberValue("$type", cp));
		AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		attr.addAnnotation(annotationInfo);

		ClassMemberValue cmvNone = new ClassMemberValue(JsonTypeInfo.None.class.getName(), cp);
		annotationInfo.addMemberValue("defaultImpl", cmvNone);

		// @JsonSubTypes
		List<AnnotationMemberValue> amvs = Lists.newArrayList();
		for (Pair<Class<?>, String> child: childs) {
			Annotation annotationType = new Annotation(Type.class.getName(), cp);
			annotationType.addMemberValue("value", new ClassMemberValue(child.getKey().getName(), cp));
			annotationType.addMemberValue("name", new StringMemberValue(child.getValue(), cp));

			AnnotationMemberValue amv = new AnnotationMemberValue(cp);
			amv.setValue(annotationType);
			amvs.add(amv);
		}

		Annotation annotationSub = new Annotation(JsonSubTypes.class.getName(), cp);
		ArrayMemberValue arraymv = new ArrayMemberValue(cp);
		MemberValue[] valueSubs = amvs.toArray(new MemberValue[] {});
		arraymv.setValue(valueSubs);
		annotationSub.addMemberValue("value", arraymv);

		attr.addAnnotation(annotationSub);
		cf.addAttribute(attr);

		try {
			return cc.toClass();
		}
		catch (CannotCompileException ex) {
			throw new RuntimeException("Failed generating MixIn for registered Subclasses", ex);
		}
	}

}
