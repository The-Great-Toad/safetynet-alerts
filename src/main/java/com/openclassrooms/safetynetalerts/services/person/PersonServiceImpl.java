package com.openclassrooms.safetynetalerts.services.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ChildDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepository;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final String LOG_ID = "[PersonServiceImpl]";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private FirestationService firestationService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<Person> getAllPerson() {
        return personRepository.getListPersons();
    }

    public Person savePerson(Person p){
        return personRepository.savePerson(p);
    }

    public Person updatePerson(Person p) {
        return personRepository.updatePerson(p);
    }

    public Person deletePerson(Person p) {
        return personRepository.deletePerson(p);
    }

    @Override
    public List<Person> getPersonsByLastName(String lastName) {
        return personRepository.getPersonsByLastName(lastName);
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        return personRepository.getPersonByAddress(address);
    }

    @Override
    public List<PersonInfoDto> getPersonInfoByFirstAndLastName(String firstName, String lastName) {
        List<PersonInfoDto> personsInfoDto = new ArrayList<>();
        List<Person> persons = getPersonsByLastName(lastName);
        persons.forEach(person -> {
            MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
            int age = calculateAge(medicalRecord);
            try {
                String personString = objectMapper.writeValueAsString(person);
                PersonInfoDto personInfoDto = objectMapper.readValue(personString, PersonInfoDto.class);
                personInfoDto.setAge(age);
                personInfoDto.setMedications(medicalRecord.getMedications());
                personInfoDto.setAllergies(medicalRecord.getAllergies());
                personsInfoDto.add(personInfoDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return personsInfoDto;
    }

    @Override
    public List<String> getResidentsEmailByCity(String city) {
        List<String> emails = getAllPerson().stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .toList();
        if (emails.isEmpty()) {
            final String error = String.format("No match found for city: %s", city);
            logger.error("{} - {}", LOG_ID, error);
            throw new NoSuchElementException(error);
        }
        return emails;
    }

    @Override
    public List<ChildDto> getChildrenByAddress(String address) {
        List<Person> persons = personRepository.getPersonByAddress(address);
        return getChildrenFromPersonList(persons);
    }

    @Override
    public List<String> getPhonesByFireStationNumber(int stationNumber) {
        // Récupérer les adresses desservies par la caserne de pompiers
        List<String> addresses = firestationService.getAddressesByStationNumber(stationNumber);

        // Filtrer les personnes par adresse
        return personRepository.getListPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .toList();
    }

    private List<ChildDto> getChildrenFromPersonList(List<Person> persons) {
        List<ChildDto> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();

        persons.forEach(person -> {
            MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
            int age = calculateAge(md);
            if (age < 18) {
                children.add(new ChildDto(person.getFirstName(), person.getLastName(), age));
            } else {
                adults.add(person);
            }
        });

        if (!children.isEmpty()) {
            children.forEach(childDto -> childDto.getRelatives().addAll(adults));
        }

        return children;
    }

    public Integer calculateAge(MedicalRecord md) {
//        LocalDate birthDate = md.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(md.getBirthdate(), LocalDate.now()).getYears();
    }
}
