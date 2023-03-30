package com.example.shapeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ShapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShapeApplication.class, args);
	}

}
