package com.openclassrooms.safetynetalerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.models.dto.ResidentAndFirestationDto;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {

	@Autowired
	private PersonService personService;

	@Autowired
	private FirestationService firestationService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		printMessage("List of all people");
		List<Person> people = personService.getAllPerson();
		people.forEach(System.out::println);
		printMessage("");

		printMessage("List of all fire stations");
		List<Firestation> firestations = firestationService.getAllFirestation();
		firestations.forEach(System.out::println);
		printMessage("");

		printMessage("List of all medical records");
		List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
		medicalRecords.forEach(System.out::println);
		printMessage("");

		printMessage("List of people covered by a fire station");
		PersonsCoveredByFirestation personsCoveredByFirestation = firestationService.getPersonCoveredByFirestation(2);
		personsCoveredByFirestation.getPersons().forEach(System.out::println);
		System.out.printf("NumberAdults: %d %n", personsCoveredByFirestation.getNumberAdults());
		System.out.printf("NumberChildren: %d %n", personsCoveredByFirestation.getNumberChildren());
		printMessage("");

		printMessage("List of children by address");
		List<ChildDto> children = personService.getChildrenByAdress("1509 Culver St");
		children.forEach(System.out::println);
		printMessage("");

		printMessage("List of phones number by fire station number");
		List<String> phones = personService.getPhonesByFirestationNumber(2);
		phones.forEach(System.out::println);
		printMessage("");

		printMessage("List of residents and fire station by address");
		ResidentAndFirestationDto residentAndFirestationDto = firestationService.getResidentAndFirestationDto("1509 Culver St");
		residentAndFirestationDto.getResidents().forEach(System.out::println);
		System.out.println("Fire station number: " + residentAndFirestationDto.getFireStationNumber());

	}

	private void printMessage(String message) {
		if (Strings.isBlank(message)) {
//			System.out.println("#".repeat(100));
			System.out.println();
		} else {
			System.out.println("#".repeat(100));
			System.out.println(StringUtils.center(message, 100));
			System.out.println("#".repeat(100));
		}
	}
}
