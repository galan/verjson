package de.galan.verjson.jackson;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * @author daniel
 */
public class JacksonUtilsImpl implements JacksonUtils {

	@Override
	public byte[] marshall(Collection<Base> data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream() {

			@Override
			public byte[] toByteArray() {
				return buf;
			}
		};

		getObjectMapperForSerialization().writerWithType(new TypeReference<Collection<Base>>() {}).writeValue(out, data);
		return out.toByteArray();
	}


	@Override
	public String marshallIntoString(Collection<Base> data) throws IOException {
		return getObjectMapperForSerialization().writeValueAsString(data);
	}


	protected ObjectMapper getObjectMapperForSerialization() {
		ObjectMapper mapper = new ObjectMapper();

		StdTypeResolverBuilder typeResolverBuilder = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
		typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
		typeResolverBuilder.init(JsonTypeInfo.Id.CLASS, new ClassNameIdResolver(SimpleType.construct(Base.class), TypeFactory.defaultInstance()));
		mapper.setDefaultTyping(typeResolverBuilder);

		return mapper;
	}


	protected ObjectMapper getObjectMapperForDeserialization() {
		ObjectMapper mapper = new ObjectMapper();

		StdTypeResolverBuilder typeResolverBuilder = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
		typeResolverBuilder = typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
		typeResolverBuilder.init(JsonTypeInfo.Id.CLASS, new ClassNameIdResolver(SimpleType.construct(Base.class), TypeFactory.defaultInstance()) {

			private HashMap<Class, Class> classes = new HashMap<Class, Class>() {

				{
					put(ConcreteA.class, ConcreteAAdapter.class);
					put(ConcreteB.class, ConcreteBAdapter.class);
					put(ConcreteC.class, ConcreteCAdapter.class);
				}
			};


			@Override
			public String idFromValue(Object value) {
				return (classes.containsKey(value.getClass())) ? value.getClass().getName() : null;
			}


			@Override
			public JavaType typeFromId(String id) {
				try {
					return classes.get(Class.forName(id)) == null ? super.typeFromId(id) : _typeFactory.constructSpecializedType(_baseType,
						classes.get(Class.forName(id)));
				}
				catch (ClassNotFoundException e) {
					// todo catch the e
				}
				return super.typeFromId(id);
			}
		});
		mapper.setDefaultTyping(typeResolverBuilder);
		return mapper;
	}


	@Override
	public Object unmarshall(byte[] json) throws IOException {
		return getObjectMapperForDeserialization().readValue(json, Object.class);
	}


	@Override
	public <T> T unmarshall(InputStream source, TypeReference<T> typeReference) throws IOException {
		return getObjectMapperForDeserialization().readValue(source, typeReference);
	}


	@Override
	public <T> T unmarshall(byte[] json, TypeReference<T> typeReference) throws IOException {
		return getObjectMapperForDeserialization().readValue(json, typeReference);
	}


	@Override
	public <T> Collection<? extends T> unmarshall(String json, Class<? extends Collection<? extends T>> klass) throws IOException {
		return getObjectMapperForDeserialization().readValue(json, klass);
	}


	@Override
	public <T> Collection<? extends T> unmarshall(String json, TypeReference typeReference) throws IOException {
		return getObjectMapperForDeserialization().readValue(json, typeReference);
	}
}
