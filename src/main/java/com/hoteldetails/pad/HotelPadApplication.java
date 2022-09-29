package com.hoteldetails.pad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HotelPadApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelPadApplication.class, args);
	}

}
