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

import com.burgercompany.hamburger.model.Reservation;
import com.burgercompany.hamburger.repository.ReservationRepository;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class ReservationController {
	@Autowired
	ReservationRepository ReservationRepository;

	@GetMapping("/reservations")
	public ResponseEntity<List<Reservation>> getAllReservations(
			@RequestParam(required = false) String reservationType) {
		try {
			List<Reservation> reservationList = new ArrayList<Reservation>();
			if (reservationType == null) {
				log.info("getAllReservations() ---> reservationType is null");
				ReservationRepository.findAll().forEach(reservationList::add);
			} else {
				log.info("getAllReservations()-reservationType is not null");
				ReservationRepository.findByreservationTypeContaining(reservationType).forEach(reservationList::add);
			}
			if (reservationList.isEmpty()) {
				log.info(	"getAllReservations()-reservationList is empty");
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(reservationList, HttpStatus.OK);

		} catch (Exception e) {
			log.info("getAllReservations()-"+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<Reservation> getReservations(@PathVariable("id") String id) {
		Optional<Reservation> reservation = ReservationRepository.findById(id);
		if (reservation.isPresent()) {
			return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
		} else {
			log.info("getReservations()-Reservation not found "+ id);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservations(@RequestBody Reservation Reservation) {
		try {
			Reservation createReservation = ReservationRepository.save(Reservation);
			log.info("createReservations()-Created Reservation");
			return new ResponseEntity<>(createReservation, HttpStatus.OK);
		} catch (Exception e) {
			log.info("createReservations()-"+ e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/reservations/{reservationType}")
	public ResponseEntity<Reservation> updateReservations(@PathVariable("reservationType") String reservationType,
			@RequestBody Reservation Reservation) {
		Optional<Reservation> reservationRepo = ReservationRepository.findByreservationType(reservationType);
		if (reservationRepo.isPresent()) {
			Reservation reservationUpdate = reservationRepo.get();
			reservationUpdate.setreservationType(Reservation.getreservationType());
			reservationUpdate.setPrice(Reservation.getCost());
			reservationUpdate.setStatus(Reservation.getStatus());
			log.info("updateReservations()-Updated reservation "+ reservationType);
			return new ResponseEntity<>(ReservationRepository.save(currentReservation), HttpStatus.OK);
		} else {
			log.info("updateReservations()-Reservation not found "+ reservationType);
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/reservations/{reservationType}")
	public ResponseEntity<HttpStatus> deleteReservations(@PathVariable("reservationType") String reservationType) {
		try {
			ReservationRepository.deleteByreservationType(reservationType);
			log.info("deleteReservations()-Deleted Reservation "+ reservationType);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("deleteReservations()-"+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/reservations")
	public ResponseEntity<HttpStatus> deleteAllReservations() {
		try {
			ReservationRepository.deleteAll();
			log.info("deleteAllReservations()-Deleted all reservations");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.info("deleteAllReservations()-"+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
