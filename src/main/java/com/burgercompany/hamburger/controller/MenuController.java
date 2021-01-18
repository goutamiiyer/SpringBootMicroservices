package com.burgercompany.hamburger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.burgercompany.hamburger.model.Menu;
import com.burgercompany.hamburger.repository.MenuRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class MenuController {
	@Autowired
	MenuRepository menuRepository;

	@GetMapping("/menu")
	public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(required = false) String itemName) {
		try {
			List<Menu> menuList = new ArrayList<Menu>();
			if (itemName == null) {
				log.info("FROM com.burgercompany.hamburger.controller.MenuController/getAllMenus()-itemName is null");
				menuRepository.findAll().forEach(menuList::add);
			} else {
				log.info("FROM com.burgercompany.hamburger.controller.MenuController/getAllMenus()-itemName is not null");
				menuRepository.findByItemNameContaining(itemName).forEach(menuList::add);
			}
			if (menuList.isEmpty()) {
				log.info("FROM com.burgercompany.hamburger.controller.MenuController/getAllMenus()-menuList is empty");
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(menuList, HttpStatus.OK);

		} catch (Exception e) {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/getAllMenus()-" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/menu/{id}")
	public ResponseEntity<Menu> getMenus(@PathVariable("id") String id) {
		Optional<Menu> menu = menuRepository.findById(id);
		if (menu.isPresent()) {
			return new ResponseEntity<>(menu.get(), HttpStatus.OK);
		} else {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/getMenus()-Menu item not found "+ id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/menu")
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
		try {
			Menu createMenu = menuRepository.save(menu);
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/createMenu()-Created Menu item");
			return new ResponseEntity<>(createMenu, HttpStatus.OK);
		} catch (Exception e) {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/createMenu()-" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/menu/{itemName}")
	public ResponseEntity<Menu> updateMenu(@PathVariable("itemName") String itemName, @RequestBody Menu menu) {
		Optional<Menu> menuRepo = menuRepository.findByItemName(itemName);
		if (menuRepo.isPresent()) {
			Menu menuUpdate = menuRepo.get();
			menuUpdate.setItemName(menu.getItemName());
			menuUpdate.setCategory(menu.getCategory());
			menuUpdate.setPrice(menu.getPrice());
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/updateMenu()-Updated " + itemName);
			return new ResponseEntity<>(menuRepository.save(menuUpdate), HttpStatus.OK);
		} else {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/updateMenu()-The Item not found is "+ itemName);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/menu/{itemName}")
	public ResponseEntity<HttpStatus> deleteMenu(@PathVariable("itemName") String itemName) {
		try {
			menuRepository.deleteByItemName(itemName);
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/deleteMenu()-Deleted " + itemName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/deleteMenu()-The Item not found is "+ itemName);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/menu")
	public ResponseEntity<HttpStatus> deleteAllMenus() {
		try {
			menuRepository.deleteAll();
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/deleteAllMenus()-Deleted items");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("FROM com.burgercompany.hamburger.controller.MenuController/deleteAllMenus()-"+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
