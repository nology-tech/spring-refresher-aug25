package io.nology.intro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.nology.intro.greeting.GreetingController;
import io.nology.intro.greeting.GreetingService;
import io.nology.intro.greeting.InMemoryNameRepository;

@SpringBootApplication
public class IntroApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntroApplication.class, args);
	}

	

}
