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

import com.burgercompany.hamburger.model.Location;
import com.burgercompany.hamburger.repository.LocationRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class LocationController {
	@Autowired
	LocationRepository locationRepository;

	@GetMapping("/location")

	public ResponseEntity<List<Location>> getAllLocations(@RequestParam(required = false) String street) {
		try {
			List<Location> locationList = new ArrayList<Location>();
			if (street == null) {
				log.info("getAllLocations()-street is null");
				locationRepository.findAll().forEach(locationList::add);
			} else {
				log.info("getAllLocations()-street is not null");
				locationRepository.findByStreetNameContaining(street).forEach(locationList::add);
			}
			if (locationList.isEmpty()) {
				log.info("getAllLocations()-Location list empty");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(locationList, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("getAllLocations()-"+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Get location by id */
	@GetMapping("/location/{id}")
	public ResponseEntity<Location> getLocation(@PathVariable("id") String id) {
		Optional<Location> location = locationRepository.findById(id);
		if (location.isPresent()) {
			return new ResponseEntity<>(location.get(), HttpStatus.OK);
		} else {
			log.info("getLocations()-Location is not found"+ id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* Insert a location into the mongoDB */
	@PostMapping("/location")
	public ResponseEntity<Location> createLocation(@RequestBody Location location) {
		try {
			Location insertLocation = locationRepository.save(location);
			log.info("createLocation()-Inserted Location");
			return new ResponseEntity<>(insertLocation, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("createLocation()-"+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Update the location by ID */
	@PutMapping("/location/{id}")
	public ResponseEntity<Location> updateLocation(@PathVariable("id") String id, @RequestBody Location location) {
		Optional<Location> locationRepo = locationRepository.findById(id);
		if (locationRepo.isPresent()) {
			Location locationUpdate = locationRepo.get();
			locationUpdate.setStreet(location.getStreet());
			locationUpdate.setCity(location.getCity());
			locationUpdate.setState(location.getState());
			locationUpdate.setPostalcode(location.getPostalCode());
			locationUpdate.setContact(location.getContact());
			log.info("updateLocation()-Updated Location "+ id);
			return new ResponseEntity<>(locationRepository.save(currentLocation), HttpStatus.OK);
		} else {
			log.info("updateLocation()-Location not found "+ id);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Delete the location by ID */
	@DeleteMapping("/location/{id}")
	public ResponseEntity<HttpStatus> deleteLocation(@PathVariable("id") String id) {
		try {
			locationRepository.deleteById(id);
			log.info("deleteLocation()-Deleted Location: "+ id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.debug("deleteLocation()-Location not found "+ id + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* Delete all the locations */
	@DeleteMapping("/location")
	public ResponseEntity<HttpStatus> deleteAllLocations() {
		try {
			locationRepository.deleteAll();
			log.info("deleteLocation()-Deleted Locations");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.debug("deleteLocation()-"+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
