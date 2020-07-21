package com.reservation.bus.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PopulateSeats {
	
	private String busNumber;

	private List<Integer> availableSeats;

	private List<Integer> bookedSeats;
	
	
}
