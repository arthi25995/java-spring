package com.reservation.bus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.bus.entity.BusDetails;
import com.reservation.bus.entity.Reservation;
import com.reservation.bus.entity.Route;
import com.reservation.bus.exception.ReservationServiceException;
import com.reservation.bus.model.PopulateSeats;
import com.reservation.bus.model.ReserveSeat;
import com.reservation.bus.model.SearchResults;
import com.reservation.bus.repository.BusDetailsRepository;
import com.reservation.bus.repository.ReservationRepository;
import com.reservation.bus.repository.RouteRepository;
import com.reservation.bus.util.Utils;
import com.reservation.bus.util.Constants;
import com.reservation.bus.util.OBRValidation;

@Service
public class ReservationService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static final Integer MAX_SEAT_PER_BUS = 40;
	public static final Float EXTRA_CHARGE_PER = 10.0f;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	BusDetailsRepository busDetailsRepository;
	
	@Autowired
	RouteRepository routeRepository;
	
	@Autowired
	BusDetailsRepository busRepository;
	
	@Autowired
	OBRValidation obrValidation;
	
	@Autowired
	ObjectMapper mapper;

	public List<SearchResults> searchBus(String sourceCity, String destinationCity, String travelDate,
			String returnDate, String uniqueId) throws ReservationServiceException {
		log.info("{} - searchBus - starts",uniqueId);
		log.info("{} - searchBus - request :: sourceCity {}, destinationCity {}, travelDate {}, returnDate {}",uniqueId,sourceCity,destinationCity,travelDate,returnDate);
		obrValidation.validateRoute(sourceCity, destinationCity, uniqueId);
		obrValidation.validateDate(travelDate, returnDate, uniqueId);
		List<Route> routeList = routeRepository.findBySourceAndDestination(sourceCity, destinationCity);
		
		List<List<BusDetails>> busesList = new ArrayList<>();
		if(routeList.isEmpty()) {
			throw new ReservationServiceException(Constants.NO_ROUTES_ERROR);
		}
		routeList.forEach( r -> {
			List<BusDetails> buses = null;
				try {
					buses = busRepository.findAllByRouteID(r.getId(), Utils.convertStringToDate(travelDate));
				} catch (ReservationServiceException e) {
					log.error("Invalid Date", e);
				}
			if(Objects.nonNull(buses)) {
				busesList.add(buses);
			}
		});
		//list<List<bus>>
		if(busesList.isEmpty()) {
			throw new ReservationServiceException(Constants.NO_BUSES_EXISTS_ERROR);
		}
		List<BusDetails> busStream = busesList.stream().flatMap(List<BusDetails>::stream).collect(Collectors.toList());
		List<SearchResults> searchResults = new ArrayList<>();
		busStream.forEach(b -> {
			SearchResults bus = new SearchResults();
			bus.setArrivalTime(b.getArrivalTime());
			bus.setDeparture(b.getDeparture());
			bus.setBusNumber(b.getBusNumber());
			bus.setOperatorName(b.getOperatorName());
			bus.setPrice(b.getPrice());
			bus.setAvailable(b.getAvailable());
			searchResults.add(bus);
		});
		log.info("{} - searchBus - ends",uniqueId);
		return searchResults;
	}

	public PopulateSeats viewBus(String busNumber, String travelDate, String uniqueId) throws ReservationServiceException {
		log.info("{} - viewBus - starts",uniqueId);
		log.info("{} - viewBus - request :: busNumber {}, travelDate {}",uniqueId,busNumber,travelDate);
		obrValidation.validateDate(travelDate, null, uniqueId);
		obrValidation.validateBusNumber(busNumber, uniqueId);
		
		Optional<BusDetails> optionalBus = busRepository.findById(busNumber);
		optionalBus.orElseThrow(() -> new ReservationServiceException(Constants.INVALID_BUSNUM_ERROR));
		
		PopulateSeats popSeats = new PopulateSeats();
		List<Reservation> reservList = reservationRepository.findByBusIDAndDt(busNumber, Utils.convertStringToDate(travelDate));
		List<Integer> bookedSeat = new ArrayList<>();
		reservList.forEach( r -> bookedSeat.add(r.getSeat()) );
		Collections.sort(bookedSeat);
		List<Integer> availableSeat = new ArrayList<>();
		int i = 1;
		while(i <= MAX_SEAT_PER_BUS) {
			if(!bookedSeat.contains(i)) {
				availableSeat.add(i);
				i++;
			}
		}
		popSeats.setAvailableSeats(availableSeat);
		popSeats.setBookedSeats(bookedSeat);
		popSeats.setBusNumber(busNumber);
		log.info("{} - viewBus - ends",uniqueId);
		return popSeats;
	}

	public void reserveBus(ReserveSeat reserve, String uniqueId) throws ReservationServiceException, JsonProcessingException {
		log.info("{} - reserveBus - starts",uniqueId);
		obrValidation.validateReserveSeat(reserve, uniqueId);
		log.info("{} - reserveBus - request :: ",uniqueId, mapper.writeValueAsString(reserve));
		int totAmount = 0;
		BusDetails bus = busRepository.findByBusNumberAndTravelDate(reserve.getBusNumber(), Utils.convertStringToDate(reserve.getTravelDate()));
		Reservation reservation = new Reservation();
		if(reserve.getChoice() == 1) {
			int fee = (int)(bus.getPrice() * (EXTRA_CHARGE_PER / 100.0f));
			reservation.setFee(fee);
			totAmount = bus.getPrice() + fee;
		} else {
			totAmount = bus.getPrice();
		}
		
		reservation.setRate(bus.getPrice());
		reservation.setPassengerID(Integer.parseInt(reserve.getPid()));
		reservation.setBusID(reserve.getBusNumber());
		reservation.setDt(Utils.convertStringToDate(reserve.getTravelDate()));
		reservation.setSeat(reserve.getSeatNumber());
		reservation.setId(RandomUtils.nextLong(0, 1000000));
		reservation.setAmount(totAmount);
		
		reservationRepository.saveAndFlush(reservation);
		log.info("{} - reserveBus - ends",uniqueId);
		
	}

}
