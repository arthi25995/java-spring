package com.reservation.bus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ReserveSeat {
	
	private String busNumber;

	private String pid;

	private int choice;
	
	private String travelDate;
	
	private int seatNumber;
	
}
