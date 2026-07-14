package com.kickoff.kickoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KickoffApplication {

	public static void main(String[] args) {
		SpringApplication.run(KickoffApplication.class, args);
	}

}
