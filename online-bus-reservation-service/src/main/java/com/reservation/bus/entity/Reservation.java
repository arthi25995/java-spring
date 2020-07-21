package com.reservation.bus.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "Reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "PassengerID")
	private int passengerID;
	
	@Column(name = "BusNumber")
	private String busID;
	
	@Column(name = "TravelDate")
	private Date dt;
	
	@CreatedDate
	@Column(name = "BookingDate")
	private Date date;
	
	@Column(name = "SeatID")
	private int seat;
	
	@Column(name = "Rate")
	private int rate;
	
	@Column(name = "ExtraCharges")
	private int fee;
	
	@Column(name = "TotalAmount")
	private int amount;
}
