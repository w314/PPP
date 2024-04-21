package com.revature.PPP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.revature.PPP")
@EntityScan("com.revature.PPP.models")
@EnableJpaRepositories("com.revature.PPP.daos")
public class PppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PppApplication.class, args);
	}

}
