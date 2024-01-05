package com.openclassrooms.safetynetalerts;

import com.openclassrooms.safetynetalerts.services.firestation.FirestationServiceImpl;
import com.openclassrooms.safetynetalerts.services.person.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {

	@Autowired
	private PersonServiceImpl personServiceImpl;

	@Autowired
	private FirestationServiceImpl firestationServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		List<Person> people = personServiceImpl.getAllPerson();
//		people.forEach(person -> System.out.println(person.toString()));

//		List<Firestation> firestations = firestationServiceImpl.getAllFirestation();
//		firestations.forEach(firestation -> System.out.println(firestation.toString()));
	}
}
