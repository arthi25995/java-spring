package com.reservation.bus.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.bus.entity.BusDetails;

public interface BusDetailsRepository extends JpaRepository<BusDetails, String>{

	@Query("select * from BusDetails where routeID=:id and travelDate=:date")
	List<BusDetails> findAllByRouteID(@Param("id") int id, @Param("date") Date date);

	BusDetails findByBusNumberAndTravelDate(String busNumber, Date travelDate);


}
