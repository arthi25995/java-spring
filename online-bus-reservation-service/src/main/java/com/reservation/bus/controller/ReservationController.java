package com.reservation.bus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reservation.bus.exception.ReservationServiceException;
import com.reservation.bus.model.PopulateSeats;
import com.reservation.bus.model.ReserveSeat;
import com.reservation.bus.model.SearchResults;
import com.reservation.bus.service.ReservationService;
import com.reservation.bus.util.Constants;
import com.reservation.bus.util.Utils;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/booking",produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	@GetMapping(value = "/searchBus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> searchBus(@RequestParam String sourceCity, @RequestParam String destinationCity, @RequestParam String travelDate, @RequestParam(required = false) String returnDate) 
			throws ReservationServiceException, JsonProcessingException, JSONException{
		String uniqueId = Utils.generateUniqueID();
		log.info("{} - booking/searchBus - starts",uniqueId);
		List<SearchResults> list = reservationService.searchBus(sourceCity, destinationCity, travelDate, returnDate, uniqueId);
		log.info("{} - booking/searchBus - ends",uniqueId);
		return new ResponseEntity<>(Utils.prepareResponse(Constants.FETCH_BUS_DETAILS, "searchBus", list, uniqueId), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/viewBus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> viewBus(@RequestParam String busNumber, @RequestParam String travelDate) throws ReservationServiceException, JsonProcessingException, JSONException{
		String uniqueId = Utils.generateUniqueID();
		log.info("{} - booking/viewBus - starts",uniqueId);
		PopulateSeats list = reservationService.viewBus(busNumber, travelDate, uniqueId);
		log.info("{} - booking/viewBus - ends",uniqueId);
		return new ResponseEntity<>(Utils.prepareResponse(Constants.FETCH_SEAT_AVAILABILITY, "viewBus", list, uniqueId), HttpStatus.OK);
		
	}
	
	
	@PostMapping(value = "/reserveBus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> reserveBus(@RequestBody ReserveSeat reserve) throws ReservationServiceException, JsonProcessingException, JSONException{
		String uniqueId = Utils.generateUniqueID();
		log.info("{} - booking/reserveBus - starts",uniqueId);
		reservationService.reserveBus(reserve, uniqueId);
		log.info("{} - booking/reserveBus - ends",uniqueId);
		return new ResponseEntity<>(Utils.prepareResponse(Constants.SUCCESS_BOOKING, "reserveBus", uniqueId), HttpStatus.OK);
		
	}
	
	

}
