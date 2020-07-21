package com.reservation.bus.util;


import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.reservation.bus.exception.ReservationServiceException;
import com.reservation.bus.model.ReserveSeat;


@Component
public class OBRValidation {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	public void validateDate(String travelDate, String returnDate, String uniqueId) throws ReservationServiceException {
		if(!StringUtils.hasText(travelDate)) {
			log.error("{} - validateDate - empty travelDate",uniqueId);
			throw new ReservationServiceException(Constants.KEY_FIELDS_ERROR);
		}
		Utils.convertStringToDate(travelDate);
		if(StringUtils.hasText(returnDate)) {
			Utils.convertStringToDate(returnDate);
		}
		
	}


	public void validateRoute(String sourceCity, String destinationCity, String uniqueId) throws ReservationServiceException {
		if(!StringUtils.hasText(sourceCity) || !StringUtils.hasText(destinationCity) ) {
			log.error("{} - validateRoute - mandatory fields are empty",uniqueId);
			throw new ReservationServiceException(Constants.KEY_FIELDS_ERROR);
		}
	}


	public void validateBusNumber(String busNumber, String uniqueId) throws ReservationServiceException {
		if(!StringUtils.hasText(busNumber)) {
			log.error("{} - validateRoute - mandatory fields are empty",uniqueId);
			throw new ReservationServiceException(Constants.KEY_FIELDS_ERROR);
		}
		
	}


	public void validateReserveSeat(ReserveSeat reserve, String uniqueId) throws ReservationServiceException {
		if(!StringUtils.hasText(reserve.getBusNumber()) || !StringUtils.hasText(reserve.getPid()) 
				|| !StringUtils.hasText(reserve.getTravelDate()) || Objects.isNull(reserve.getSeatNumber()) || Objects.isNull(reserve.getChoice())) {
			log.error("{} - validateReserveSeat - mandatory fields are empty",uniqueId);
			throw new ReservationServiceException(Constants.KEY_FIELDS_ERROR);
		} else if(reserve.getChoice() != 0 && reserve.getChoice() != 1 ) {
			log.error("{} - validateReserveSeat - invalid choice",uniqueId);
			throw new ReservationServiceException(Constants.INVALID_CHOICE);
		}
		
	}
	
	

}
