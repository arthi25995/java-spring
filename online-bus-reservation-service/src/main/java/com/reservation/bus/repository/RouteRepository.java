package com.reservation.bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reservation.bus.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{

	@Query("select id from Route where origin=:sourceCity and destination=:destinationCity")
	List<Route> findBySourceAndDestination(@Param("sourceCity") String sourceCity, @Param("destinationCity") String destinationCity);
	
}
