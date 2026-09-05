package edu.unicesumar.aep;

import org.springframework.boot.SpringApplication;

public class TestAepApplication {

	public static void main(String[] args) {
		SpringApplication.from(AepApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
