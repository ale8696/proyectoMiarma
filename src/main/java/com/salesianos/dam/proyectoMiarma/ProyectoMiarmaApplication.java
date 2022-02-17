package com.salesianos.dam.proyectoMiarma;

import com.salesianos.dam.proyectoMiarma.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class ProyectoMiarmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoMiarmaApplication.class, args);
	}

}
