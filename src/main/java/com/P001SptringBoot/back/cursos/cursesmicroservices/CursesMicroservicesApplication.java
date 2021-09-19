package com.P001SptringBoot.back.cursos.cursesmicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.P001SpringBoot.back.models.entity",
"com.P001SptringBoot.back.cursos.cursesmicroservices.models.entity"})
public class CursesMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursesMicroservicesApplication.class, args);
	}

}
