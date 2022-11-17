package edu.eskisehir.teklifyap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TeklifyapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeklifyapApplication.class, args);
		log.info("Application is just started now!");

	}

}
