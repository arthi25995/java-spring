package com.reservation.bus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.bus.exception.ReservationServiceException;
import com.reservation.bus.model.SearchResults;

public class Utils {
	
	private static final Logger log = LoggerFactory.getLogger(Utils.class);
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
	public static final String RESOURCE = "resource";
	public static final String MESSAGE = "message";
	
	public static String generateUniqueID() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 19);
	}
	
	public static String prepareResponse(String message, String apiKey, String uniqueId) throws JSONException, JsonProcessingException
	{
		JSONObject response = new JSONObject();
		response.put(RESOURCE, apiKey);
		response.put(MESSAGE, message);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String respString = mapper.writeValueAsString(response);
		log.info("{} - prepareResponse for {} : {}",uniqueId,apiKey,respString);
		return respString;
	}
	
	public static String prepareResponse(String message, String apiKey, Object data, String uniqueId) throws JSONException, JsonProcessingException
	{
		JSONObject response = new JSONObject();
		response.put(RESOURCE, apiKey);
		response.put(MESSAGE, message);
		if(Objects.nonNull(data)) {
			response.put("responseData", data);
			if("searchBus".equals(apiKey)) {
				response.put("NoOfResults", ((List<SearchResults>)data).size());
			}
			
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String respString = mapper.writeValueAsString(response);
		log.info("{} - prepareResponse for {} : {}",uniqueId,apiKey,respString);
		return respString;
	}

	public static Date convertStringToDate(String date) throws ReservationServiceException {
		 SimpleDateFormat formatter1=new SimpleDateFormat(DATE_FORMAT);
		 Date opDate = null;
		 try {
			 opDate = formatter1.parse(date);
		} catch (ParseException e) {
			throw new ReservationServiceException(Constants.INVALID_DATE);
		}
		return opDate;
	}
	
	
	public static String convertDateToString(Date date) {
		 SimpleDateFormat formatter1=new SimpleDateFormat(DB_DATE_FORMAT);
		 String opDate = null;
		 opDate = formatter1.format(date);
     	 return opDate;
	}

}
