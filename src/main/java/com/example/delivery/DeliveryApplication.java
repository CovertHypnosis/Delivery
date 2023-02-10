package com.example.delivery;

import com.example.delivery.services.MailSendService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Log4j2
public class DeliveryApplication implements CommandLineRunner {
	@Autowired
	MailSendService sendService;

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started");
//		sendService.sendMail();
//		heelo().subscribe();
	}

	public Flux<Integer> heelo() {
		// pub-sub subscribe
		log.info("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		return Flux.just(1,2,3);
	}
}
