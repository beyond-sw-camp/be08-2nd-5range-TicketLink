package com.beyond.ticketLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity(debug = true)
@SpringBootApplication
public class TicketLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketLinkApplication.class, args);
	}

}
