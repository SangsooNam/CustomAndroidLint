package jackson.nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

	public final String name;
	public final int age;

	public Person(@JsonProperty("name") String name,
	              @JsonProperty("age") int age) {
		this.name = name;
		this.age = age;
	}
}
