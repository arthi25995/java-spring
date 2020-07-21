package com.reservation.bus.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.bus.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	List<Reservation> findByBusIDAndDt(String busNumber, Date travelDate);
	
}
