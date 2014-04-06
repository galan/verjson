package de.galan.verjson.jackson.p3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
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

import de.galan.commons.logging.Logr;


/** x */
public class SerializeExample1 {

	final static Logger LOG = Logr.get();


	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// let start creating the zoo
		Zoo zoo = new Zoo("Samba Wild Park", "Paz");
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		List<Animal> animals = new ArrayList<>();
		animals.add(lion);
		animals.add(elephant);
		zoo.setAnimals(animals);

		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Animal.class, generateMixIn());
		String zooString = mapper.writeValueAsString(zoo);
		//mapper.writerWithDefaultPrettyPrinter().writeValue(System/**/./**/out, zoo);// writeValue(new FileWriter(new File(outputFile)), zoo);
		LOG.info(zooString);

		JsonNode node = mapper.readTree(zooString);
		Zoo zoo2 = mapper.treeToValue(node, Zoo.class);
		LOG.info("" + zoo2);
	}


	protected static Class<?> generateMixIn() {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass(Animal.class.getPackage().getName() + ".GenAnimalMixIn");
		ClassFile cf = cc.getClassFile();
		ConstPool cp = cf.getConstPool();

		// @JsonTypeInfo
		//annotationInfo.addMemberValue("use", new EnumMemberValue(JsonTypeInfo.Id.NAME.ordinal(), constPool));
		Annotation annotationInfo = new Annotation(JsonTypeInfo.class.getName(), cp);
		EnumMemberValue enumId = new EnumMemberValue(cp);
		enumId.setType(JsonTypeInfo.class.getName());
		enumId.setValue(JsonTypeInfo.Id.NAME.toString());
		annotationInfo.addMemberValue("use", enumId);

		EnumMemberValue enumAs = new EnumMemberValue(cp);
		enumAs.setType(JsonTypeInfo.class.getName());
		enumAs.setValue(As.PROPERTY.toString());
		annotationInfo.addMemberValue("include", enumAs);

		annotationInfo.addMemberValue("property", new StringMemberValue("$_type", cp));
		AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		attr.addAnnotation(annotationInfo);
		cf.addAttribute(attr);

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
		ArrayMemberValue amv = new ArrayMemberValue(cp);
		MemberValue[] valueSubs = new MemberValue[] {cmvA, cmvB};
		amv.setValue(valueSubs);
		annotationSub.addMemberValue("value", amv);

		AnnotationsAttribute attrSub = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		attrSub.addAnnotation(annotationInfo);
		cf.addAttribute(attrSub);

		try {
			Class<?> result = cc.toClass();
			//Class<?> xx = AnimalMixIn.class;
			return result;
		}
		catch (CannotCompileException ex) {
			throw new RuntimeException("irks");
		}
	}


	private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		return pool.get(clazz.getName());
	}

}


/** y */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "$type")
@JsonSubTypes({@Type(value = Lion.class, name = "lion"), @Type(value = Elephant.class, name = "elephant")})
abstract class AnimalMixIn {
	// nada
}
