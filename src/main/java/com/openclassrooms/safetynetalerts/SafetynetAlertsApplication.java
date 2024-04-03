package com.openclassrooms.safetynetalerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.*;
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
		/*
		printMessage("List of all people");
		List<Person> people = personService.getAllPerson();
		people.forEach(System.out::println);
		printMessage("");

		printMessage("List of all fire stations");
		List<Firestation> firestations = firestationService.getAllFireStation();
		firestations.forEach(System.out::println);
		printMessage("");

		printMessage("List of all medical records");
		List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
		medicalRecords.forEach(System.out::println);
		printMessage("");

		printMessage("List of people covered by a fire station (2)");
		PersonsCoveredByFirestation personsCoveredByFirestation = firestationService.getPersonCoveredByFireStation(2);
		personsCoveredByFirestation.getPersons().forEach(System.out::println);
		System.out.printf("NumberAdults: %d %n", personsCoveredByFirestation.getNumberAdults());
		System.out.printf("NumberChildren: %d %n", personsCoveredByFirestation.getNumberChildren());
		printMessage("");

		printMessage("List of children by address (1509 Culver St)");
		List<ChildDto> children = personService.getChildrenByAddress("1509 Culver St");
		children.forEach(System.out::println);
		printMessage("");

		printMessage("List of phones number by fire station number (2)");
		List<String> phones = personService.getPhonesByFireStationNumber(2);
		phones.forEach(System.out::println);
		printMessage("");

		printMessage("List of residents and fire station by address (1509 Culver St)");
		ResidentAndFirestationDto residentAndFirestationDto = firestationService.getResidentAndFireStationDto("1509 Culver St");
		residentAndFirestationDto.getResidents().forEach(System.out::println);
		System.out.println("Fire station number: " + residentAndFirestationDto.getFireStationNumber());
		printMessage("");

		printMessage("1 HomeDto served by fire station (3)");
		HomeDto homeDto = firestationService.getHomeServedByStations(List.of(3));
		homeDto.getHomes().forEach((k,v) -> System.out.printf("%s: %s%n", k, v));
		printMessage("3 HomeDto served by fire station (2, 1 & 4)");
		HomeDto homeDto2 = firestationService.getHomeServedByStations(List.of(2, 1, 4));
		homeDto2.getHomes().forEach((k,v) -> System.out.printf("%s: %s%n", k, v));
		printMessage("");

		printMessage("List Person information for John Boyd");
		List<PersonInfoDto> personInfoDtoList = personService.getPersonInfoByFirstAndLastName("John", "Boyd");
		personInfoDtoList.forEach(System.out::println);
		printMessage("");

		printMessage("List of residents email by city (Culver)");
		List<String> emails = personService.getResidentsEmailByCity("Culver");
		emails.forEach(System.out::println);
		printMessage("");
		*/
	}

	private void printMessage(String message) {
		if (Strings.isBlank(message)) {
			System.out.println();
		} else {
			System.out.println("#".repeat(100));
			System.out.println(StringUtils.center(message, 100));
			System.out.println("#".repeat(100));
		}
	}
}
