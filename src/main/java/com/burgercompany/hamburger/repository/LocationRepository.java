package com.burgercompany.hamburger.repository;

import com.burgercompany.hamburger.model.Location;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends MongoRepository<Location, String>{
	List<Location> findByStreetNameContaining(String street);
}
