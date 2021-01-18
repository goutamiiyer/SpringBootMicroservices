package com.burgercompany.hamburger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reservations")
@Data
public class Reservation {
	@Id
	private String id;
	private String reservationType;
	private String cost;
	private String status;
}
