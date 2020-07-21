package com.reservation.bus.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "Route")
@Data
public class Route {
	
	@Id
	@Column(name = "RouteID")
	private int id;
	
	@Column(name = "Source")
	private String origin;
	
	@Column(name = "Destination")
	private String destination;

}
