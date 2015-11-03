package jackson.nonnull;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonNonNull {

	public final String name;
	public final int age;

	public PersonNonNull(@NonNull @JsonProperty("name") String name,
	                     @JsonProperty("age") int age) {
		this.name = name;
		this.age = age;
	}
}
