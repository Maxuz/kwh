package dev.maxuz.kwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dev.maxuz.kwh"})
public class KwhApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwhApplication.class, args);
	}

}
