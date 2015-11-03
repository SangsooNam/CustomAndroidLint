package jackson.ignoreunknown;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonIgnoreUnknown {

	public final String name;

	public PersonIgnoreUnknown(@JsonProperty("name") String name) {
		this.name = name;
	}
}
