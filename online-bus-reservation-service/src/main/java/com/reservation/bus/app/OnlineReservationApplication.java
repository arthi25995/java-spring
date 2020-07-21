package com.reservation.bus.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.reservation.bus"})
@EnableJpaRepositories("com.reservation.bus.repository")
@EntityScan("com.reservation.bus.entity")
public class OnlineReservationApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineReservationApplication.class, args);
	}

}
