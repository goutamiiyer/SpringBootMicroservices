package com.burgercompany.hamburger.repository;

import com.burgercompany.hamburger.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String>{
	List<Reservation> findByReservationTypeContaining(String reservationType);
	Optional<Reservation> findByReservationType(String reservationType);
	void deleteByPartyType(String reservationType);
}
