package com.eteration.simplebanking;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class DemoApplication   {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	// Swagger ile api testi icin eklenmistir
	@Bean
	public OpenAPI customAPI(@Value("${application-description}") String description,
							 @Value("${application-version}") String version){
		return new OpenAPI()
				.info(new Info()
						.title("simplebanking")
						.version(version)
						.description(description)
						.license(new License().name("ETERATION")));

	}

}
