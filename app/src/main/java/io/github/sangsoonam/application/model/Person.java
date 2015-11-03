package io.github.sangsoonam.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

	public final String name;

	public Person(@JsonProperty("name") String name) {
		this.name = name;
	}
}
