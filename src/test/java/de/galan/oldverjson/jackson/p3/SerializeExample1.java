package de.galan.oldverjson.jackson.p3;

import java.io.IOException;
import java.util.List;

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

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import de.galan.commons.logging.Logr;


//import org.objectweb.asm.attrs.*;

/** x */
public class SerializeExample1 {

	final static Logger LOG = Logr.get();


	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// let start creating the zoo
		Zoo zoo = new Zoo("Samba Wild Park", "Paz");
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		List<Animal> animals = Lists.newArrayList(lion, elephant);
		animals.add(new Turtle("turtle"));
		zoo.setAnimals(animals);

		ObjectMapper mapper = new ObjectMapper();
		//Class<?> mixin = generateMixInAsm();
		//Class<?> mixin = generateMixInJavassist();

		//mapper.disableDefaultTyping();
		//mapper.getSerializationConfig().
		//mapper.getSerializerProvider().

		//configure(SerializationFeature., state)configure(MapperFeature., state)configure(Feature, state)

		//mapper.addMixInAnnotations(Animal.class, mixin);
		mapper.addMixInAnnotations(Animal.class, AnimalMixIn.class);
		String zooString = mapper.writeValueAsString(zoo);
		//mapper.writerWithDefaultPrettyPrinter().writeValue(System/**/./**/out, zoo);// writeValue(new FileWriter(new File(outputFile)), zoo);
		LOG.info(zooString);

		JsonNode node = mapper.readTree(zooString);
		Zoo zoo2 = mapper.treeToValue(node, Zoo.class);
		mapper.readValue(zooString, Zoo.class);
		LOG.info("" + zoo2);
	}


	/*
	protected static Class<?> generateMixInAsm() {
		ClassWriter cw = new ClassWriter(0);
		cw.visit(Opcodes.V1_7, Opcodes.ACC_SUPER + Opcodes.ACC_ABSTRACT, "de/galan/verjson/jackson/p3/AsmGemMixIn", null, "java/lang/Object", null);

		AnnotationVisitor av0 = cw.visitAnnotation("Lcom/fasterxml/jackson/annotation/JsonTypeInfo;", true);
		av0.visitEnum("use", "Lcom/fasterxml/jackson/annotation/JsonTypeInfo$Id;", "NAME");
		av0.visitEnum("include", "Lcom/fasterxml/jackson/annotation/JsonTypeInfo$As;", "PROPERTY");
		av0.visit("property", "$type");
		av0.visitEnd();

		{
			av0 = cw.visitAnnotation("Lcom/fasterxml/jackson/annotation/JsonSubTypes;", true);
			{
				AnnotationVisitor av1 = av0.visitArray("value");
				{
					AnnotationVisitor av2 = av1.visitAnnotation(null, "Lcom/fasterxml/jackson/annotation/JsonSubTypes$Type;");
					av2.visit("value", org.objectweb.asm.Type.getType("Lde/galan/verjson/jackson/p3/Lion;"));
					av2.visit("name", "lion");
					av2.visitEnd();
				}
				{
					AnnotationVisitor av2 = av1.visitAnnotation(null, "Lcom/fasterxml/jackson/annotation/JsonSubTypes$Type;");
					av2.visit("value", org.objectweb.asm.Type.getType("Lde/galan/verjson/jackson/p3/Elephant;"));
					av2.visit("name", "elephant");
					av2.visitEnd();
				}
				av1.visitEnd();
			}
			av0.visitEnd();
		}

		cw.visitInnerClass("com/fasterxml/jackson/annotation/JsonSubTypes$Type", "com/fasterxml/jackson/annotation/JsonSubTypes", "Type", Opcodes.ACC_PUBLIC
				+ Opcodes.ACC_STATIC + Opcodes.ACC_ANNOTATION + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE);

		cw.visitInnerClass("com/fasterxml/jackson/annotation/JsonTypeInfo$As", "com/fasterxml/jackson/annotation/JsonTypeInfo", "As", Opcodes.ACC_PUBLIC
				+ Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_ENUM);

		cw.visitInnerClass("com/fasterxml/jackson/annotation/JsonTypeInfo$Id", "com/fasterxml/jackson/annotation/JsonTypeInfo", "Id", Opcodes.ACC_PUBLIC
				+ Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_ENUM);

		MethodVisitor mv = cw.visitMethod(0, "<init>", "()V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(140, l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(Opcodes.RETURN);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLocalVariable("this", "Lde/galan/verjson/jackson/p3/AsmGemMixIn;", null, l0, l1, 0);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		cw.visitEnd();

		return loadClass(cw.toByteArray());
	}

	private static Class<?> loadClass(byte[] b) {
		//override classDefine (as it is protected) and define the class.
		Class<?> clazz = null;
		try {
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			Class<?> cls = Class.forName("java.lang.ClassLoader");
			java.lang.reflect.Method method = cls.getDeclaredMethod("defineClass", new Class[] {String.class, byte[].class, int.class, int.class});

			// protected method invocaton
			method.setAccessible(true);
			try {
				Object[] args = new Object[] {"de.galan.verjson.jackson.p3.AsmGemMixIn", b, Integer.valueOf(0), Integer.valueOf(b.length)};
				clazz = (Class<?>)method.invoke(loader, args);
			}
			finally {
				method.setAccessible(false);
			}
		}
		catch (Exception e) {
			LOG.error("xxx", e);
			System.exit(1);
		}
		return clazz;
	}
	 */

	protected static Class<?> generateMixInJavassist() {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(Animal.class.getPackage().getName() + ".GenAnimalMixIn");
		ClassFile cf = cc.getClassFile();
		cf.setMajorVersion(ClassFile.JAVA_7);
		cf.setMinorVersion(0);
		ConstPool cp = cf.getConstPool();

		// @JsonTypeInfo
		//annotationInfo.addMemberValue("use", new EnumMemberValue(JsonTypeInfo.Id.NAME.ordinal(), constPool));
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
		// >> dynamic
		Annotation annotationTypeA = new Annotation(Type.class.getName(), cp);
		ClassMemberValue cmvA = new ClassMemberValue(Lion.class.getName(), cp);
		annotationTypeA.addMemberValue("value", cmvA);
		annotationTypeA.addMemberValue("name", new StringMemberValue("lion", cp));

		Annotation annotationTypeB = new Annotation(Type.class.getName(), cp);
		ClassMemberValue cmvB = new ClassMemberValue(Elephant.class.getName(), cp);
		annotationTypeB.addMemberValue("value", cmvB);
		annotationTypeB.addMemberValue("name", new StringMemberValue("elephant", cp));
		// >>

		Annotation annotationSub = new Annotation(JsonSubTypes.class.getName(), cp);
		//MemberValue[] valueSubs = new MemberValue[] {cmvA, cmvB};
		AnnotationMemberValue amvA = new AnnotationMemberValue(cp);
		amvA.setValue(annotationTypeA);
		AnnotationMemberValue amvB = new AnnotationMemberValue(cp);
		amvB.setValue(annotationTypeB);
		ArrayMemberValue arraymv = new ArrayMemberValue(cp);
		MemberValue[] valueSubs = new MemberValue[] {amvA, amvB};
		arraymv.setValue(valueSubs);
		annotationSub.addMemberValue("value", arraymv);

		attr.addAnnotation(annotationSub);
		cf.addAttribute(attr);

		try {
			return cc.toClass();
		}
		catch (CannotCompileException ex) {
			throw new RuntimeException("irks");
		}
	}

	/*
	private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		return pool.get(clazz.getName());
	}
	 */

}


/** y */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "$type", defaultImpl = JsonTypeInfo.None.class)
@JsonSubTypes({@Type(value = Lion.class, name = "lion"), @Type(value = Elephant.class, name = "elephant")})
class AnimalMixIn {
	// noop
}
