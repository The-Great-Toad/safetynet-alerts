package com.openclassrooms.safetynetalerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.PersonDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationServiceImpl;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordServiceImpl;
import com.openclassrooms.safetynetalerts.services.person.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {

	@Autowired
	private PersonServiceImpl personServiceImpl;

	@Autowired
	private FirestationServiceImpl firestationServiceImpl;

	@Autowired
	private MedicalRecordServiceImpl medicalRecordServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
//		List<Person> people = personServiceImpl.getAllPerson();
		PersonsCoveredByFirestation personsCoveredByFirestation = firestationServiceImpl.getPersonCoveredByFirestation(2);
		personsCoveredByFirestation.getPersons().forEach(System.out::println);
		System.out.printf("There is %d adults in the list %n", personsCoveredByFirestation.getNumberAdults());
		System.out.printf("There is %d children in the list %n", personsCoveredByFirestation.getNumberChildren());
//
//		List<Firestation> firestations = firestationServiceImpl.getAllFirestation();
//		firestations.forEach(System.out::println);
//
//		List<MedicalRecord> medicalRecords = medicalRecordServiceImpl.getAllMedicalRecords();
//		medicalRecords.forEach(System.out::println);
	}
}
