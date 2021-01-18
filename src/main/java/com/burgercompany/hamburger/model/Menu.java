package com.burgercompany.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "menu")
public class Menu {
	@Id
	private String id;
	private String itemName;
	private String category;
	private String cost;
}
