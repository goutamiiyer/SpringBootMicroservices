package com.burgercompany.hamburger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class HamburgercompanyApplication {
	
//	private final static Logger LOGGER = Logger.getLogger(HamburgercompanyApplication.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(HamburgercompanyApplication.class, args);
		log.info("HamBurger Application");
	}

}
