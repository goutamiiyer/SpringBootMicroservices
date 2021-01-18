package com.burgercompany.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
@Data
public class Location {
	@Id
	private String id;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String contact;
}
