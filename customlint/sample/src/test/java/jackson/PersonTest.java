package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;

import jackson.ignoreunknown.Person;

import static org.junit.Assert.assertEquals;

public class PersonTest {

	private static String serialize(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writer().writeValueAsString(obj);
	}

	private static <T> T deserialize(String json, Class<T> clazz) throws IOException {
		return new ObjectMapper().readValue(json, clazz);
	}

	@Test
	public void testObject() throws Exception {
		Person person = deserialize("{\"name\":\"John\"}", Person.class);
		assertEquals("John", person.name);
		assertEquals("{\"name\":\"John\"}", serialize(person));
	}

	@Test(expected = com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.class)
	public void testObjectWhenHasUnknownPropertyThenThrowException() throws Exception {
		deserialize("{\"name\":\"John\", \"unknown\":\"unknown\"}", Person.class);
	}
}