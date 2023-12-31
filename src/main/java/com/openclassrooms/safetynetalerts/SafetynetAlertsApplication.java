package com.openclassrooms.safetynetalerts;

import com.openclassrooms.safetynetalerts.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {

	@Autowired
	private PersonServiceImpl personServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		List<Person> people = personService.getAllPersons();
//		System.out.println(people.toString());
	}
}
