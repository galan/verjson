package de.galan.verjson.jackson.p1;

import static de.galan.commons.time.DateDsl.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import de.galan.commons.logging.Logr;
import de.galan.commons.test.AbstractTestParent;
import de.galan.verjson.example.v1.Example1;
import de.galan.verjson.example.v1.Example1SubA;
import de.galan.verjson.example.v1.Example1SubB;


/**
 * Tests for jackson
 * 
 * @author daniel
 */
public class PolyTest extends AbstractTestParent {

	private final static Logger LOG = Logr.get();


	@Test
	public void testName() throws Exception {
		Example1 e1 = Example1.createSample();
		e1.first = "aaa\nbbbßäöü";
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule module = new SimpleModule("JacksonModule") {

			@Override
			public void setupModule(SetupContext context) {
				super.setupModule(context);
				context.addBeanSerializerModifier(new GenericBeanSerializerModifier());
				context.addBeanDeserializerModifier(new GenericBeanDeserializerModifier());
			} //, new Version(1, 0, 0, null, null, null));

		};

		module.addSerializer(Date.class, new DateSerializer());
		module.addDeserializer(Date.class, new DateDeserializer());
		//module.registerSubtypes(new NamedType(Example1SubA.class, "aaa"));
		//module.registerSubtypes(new NamedType(Example1SubB.class, "bbb"));

		//simpleModule.addDeserializer(Example1SubA.class, new GenericDeserializer());
		//module.addSerializer(new GenericSubclassSerializer(Example1SubA.class, "suba"));
		//module.addSerializer(new GenericSubclassSerializer(Example1SubB.class, "subb"));
		mapper.registerModule(module);
		//mapper.getSubtypeResolver()

		/*
		new SimpleModule("name", new Version(arg0, arg1, arg2, arg3));
		mapper.registerSubtypes(classes);
		mapper.setSubtypeResolver(new SubtypeResolver() {

			@Override
			public void registerSubtypes(Class<?>... classes) {
			}


			@Override
			public void registerSubtypes(NamedType... types) {
			}


			@Override
			public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember property, MapperConfig<?> config, AnnotationIntrospector ai, JavaType baseType) {
				return null;
			}


			@Override
			public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass basetype, MapperConfig<?> config, AnnotationIntrospector ai) {
				return null;
			}


			@Override
			public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember property, MapperConfig<?> config, AnnotationIntrospector ai) {
				return null;
			}
		});
		*/
		String output = mapper.writeValueAsString(e1);
		LOG.info(output);
		Example1 read = mapper.readValue(output, Example1.class);
		LOG.info("" + read);
	}

}


/** xxx */
class GenericBeanDeserializerModifier extends BeanDeserializerModifier {

	@Override
	public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
		//BeanDescription bd = new BasicBeanDescription();
		//beanDesc.getBeanClass()
		return super.modifyDeserializer(config, beanDesc, deserializer);
	}

}


/** z */
class GenericBeanSerializerModifier extends BeanSerializerModifier {

	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
		if (serializer.handledType().equals(Example1SubA.class)) {
			return new ExtraFieldSerializer((BeanSerializerBase)serializer, "a");
		}
		if (serializer.handledType().equals(Example1SubB.class)) {
			return new ExtraFieldSerializer((BeanSerializerBase)serializer, "b");
		}
		return super.modifySerializer(config, beanDesc, serializer);
	}

}


/** zz */
class ExtraFieldSerializer extends BeanSerializerBase {

	private String typename;


	ExtraFieldSerializer(BeanSerializerBase source, String typename) {
		super(source);
		this.typename = typename;
	}


	ExtraFieldSerializer(ExtraFieldSerializer source, ObjectIdWriter objectIdWriter) {
		super(source, objectIdWriter);
	}


	ExtraFieldSerializer(ExtraFieldSerializer source, String[] toIgnore) {
		super(source, toIgnore);
	}


	@Override
	public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
		return new ExtraFieldSerializer(this, objectIdWriter);
	}


	@Override
	protected BeanSerializerBase withIgnorals(String[] toIgnore) {
		return new ExtraFieldSerializer(this, toIgnore);
	}


	@Override
	public void serialize(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		serializeFields(bean, jgen, provider);
		jgen.writeStringField("$type", typename);
		jgen.writeEndObject();
	}


	@Override
	protected BeanSerializerBase asArraySerializer() {
		return null;
	}

}


/** x */
class DateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(from(value).toIso8601Utc());
	}


	@Override
	public Class<Date> handledType() {
		return Date.class;
	}

}


/** x */
class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return dateIso(jp.getText());
	}

}


/** y */
class GenericSubclassSerializer extends StdSerializer<Example1SubA> {

	private String name;


	public GenericSubclassSerializer(Class<Example1SubA> type, String name) {
		super(type);
		this.name = name;
	}


	@Override
	public void serialize(Example1SubA value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeStringField("$type", name);
		//jgen.writeo
		jgen.writeEndObject();
	}


	@Override
	public Class<Example1SubA> handledType() {
		return _handledType;
	}

}


/** x */
class GenericDeserializer extends JsonDeserializer<Object> {

	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		return null;
	}

}
