package com.reservation.bus.exception;


public class ReservationServiceException extends Exception{
	
	
	public ReservationServiceException( String msg){
		super(msg);
	}
	
	public ReservationServiceException( String msg , Throwable cause){
		super(msg, cause);
	}
	
}
