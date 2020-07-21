package com.reservation.bus.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "BusDetails")
public class BusDetails {
	
	@Column(name = "OperatorName")
	private String operatorName;

	@Column(name = "Departure")
	private String departure;
	
	@Id
	@Column(name = "BusNumber")
	private String busNumber;	
	
	@Column(name = "Capacity")
	private Long capacity;
	
	@Column(name = "Available")
	private int available;
	
	@Column(name = "TravelDate")
	private Date travelDate;
	
	@Column(name = "ArrivalTime")
	private String arrivalTime;
	
	@Column(name = "Price")
	private int price;
	
	@Column(name = "Status")
	private int status;
	
	@Column(name = "RouteID")
	private int routeID;
	
}
