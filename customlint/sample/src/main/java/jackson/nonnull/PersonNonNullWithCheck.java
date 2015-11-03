package jackson.nonnull;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class PersonNonNullWithCheck {

	public final String name;
	public final int age;

	public PersonNonNullWithCheck(@NonNull @JsonProperty("name") String name,
	                              @JsonProperty("age") int age) {
		this.name = Preconditions.checkNotNull(name);
		this.age = age;
	}
}
