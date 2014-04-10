package de.galan.oldverjson.jackson.p3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.EnumMemberValue;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import de.galan.commons.logging.Logr;


/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 */
public class AnnotationMain {

	final static Logger LOG = Logr.get();


	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(AnnotationMain.class.getPackage().getName() + ".GenTest");
		ClassFile cf = cc.getClassFile();
		//cf.setMajorVersion(ClassFile.JAVA_7);
		//cf.setMinorVersion(0);
		ConstPool cp = cf.getConstPool();

		// @JsonTypeInfo
		//annotationInfo.addMemberValue("use", new EnumMemberValue(JsonTypeInfo.Id.NAME.ordinal(), constPool));
		Annotation annotation = new Annotation(Dummy.class.getName(), cp);
		EnumMemberValue emv = new EnumMemberValue(cp);
		emv.setType(Abc.class.getName());
		emv.setValue(Abc.A.toString());
		annotation.addMemberValue("abc", emv);

		AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		attr.addAnnotation(annotation);
		cf.addAttribute(attr);

		try {
			Class<?> result = cc.toClass();
			LOG.info("Ann: " + result.getAnnotations().length);
		}
		catch (CannotCompileException ex) {
			throw new RuntimeException("irks");
		}
	}

}


/** z */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
@interface Dummy {

	public Abc abc();

}


/** z */
enum Abc {
	A,
	B,
	C;
}
