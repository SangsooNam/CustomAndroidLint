package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;

import jackson.ignoreunknown.PersonIgnoreUnknown;

import static org.junit.Assert.assertEquals;

public class PersonIgnoreUnknownTest {

	private static String serialize(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writer().writeValueAsString(obj);
	}

	private static <T> T deserialize(String json, Class<T> clazz) throws IOException {
		return new ObjectMapper().readValue(json, clazz);
	}

	@Test
	public void testObject() throws Exception {
		PersonIgnoreUnknown person = deserialize("{\"name\":\"John\"}", PersonIgnoreUnknown.class);
		assertEquals("John", person.name);
		assertEquals("{\"name\":\"John\"}", serialize(person));
	}

	@Test
	public void testObjectWhenHasUnknownPropertyThenIgnore() throws Exception {
		PersonIgnoreUnknown person = deserialize("{\"name\":\"John\", \"unknown\":\"unknown\"}", PersonIgnoreUnknown.class);
		assertEquals("John", person.name);
		assertEquals("{\"name\":\"John\"}", serialize(person));
	}
}