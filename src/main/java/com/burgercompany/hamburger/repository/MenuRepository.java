package com.burgercompany.hamburger.repository;

import com.burgercompany.hamburger.model.Menu;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String>{
	List<Menu> findByItemNameContaining(String itemName);
	Optional<Menu> findByItemName(String itemName);
	void deleteByItemName(String itemName);
}
