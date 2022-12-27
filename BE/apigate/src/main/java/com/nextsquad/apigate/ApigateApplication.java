package com.nextsquad.apigate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ApigateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigateApplication.class, args);
	}

}
