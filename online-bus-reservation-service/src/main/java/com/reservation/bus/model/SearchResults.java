package com.reservation.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(Include.NON_NULL)
public class SearchResults {
	
	private int noOfResults;

	private String operatorName;

	private String busNumber;
	
	private String departure;
	
	private String arrivalTime;
	
	private int price;
	
	private int available;
	
	private String duration;
	
}
