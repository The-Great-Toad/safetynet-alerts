package com.openclassrooms.safetynetalerts.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    @Autowired
    private ObjectMapper objectMapper;
    private static List<Person> people = new ArrayList<>();
    @Value("${spring.person.filepath}")
    private String personFilePath;

    public List<Person> getAllPerson() {

        if (people.isEmpty()) {
            try {
                people = objectMapper.readValue(new File(personFilePath), new TypeReference<>() {});
            } catch (IOException e) {
                logger.error("[ERROR - PERSON.JSON] WHILE MAPPING DATA : {} ", e.getMessage());
            }
        }
        return people;
    }

    public Person savePerson(Person p) {

        if (people.isEmpty()) {
            getAllPerson();
        }

        System.out.printf("[INFO] Saving person: %s %n", p.toString());
        people.add(p);

        return p;
    }

    public Person updatePerson(Person updatedPerson) {

        if (people.isEmpty()) {
            getAllPerson();
        }

        String id = updatedPerson.getFirstName() + updatedPerson.getLastName();
        for (Person currentPerson : people) {
            if ( (currentPerson.getFirstName() + currentPerson.getLastName()).equals(id) ) {
                updatePersonInformation(currentPerson, updatedPerson);
                System.out.printf("[INFO] Updating person: %s %n", updatedPerson);
                return currentPerson;
            }
        }
        throw new PersonNotFoundException("Person with name %s %s not found!".formatted(updatedPerson.getFirstName(), updatedPerson.getLastName()));
    }

    public Person deletePerson(Person p) {

        if (people.isEmpty()) {
            getAllPerson();
        }

        String id = p.getFirstName() + p.getLastName();
        int index = 0;
        for (Person currentPerson : people) {
            if ( (currentPerson.getFirstName() + currentPerson.getLastName()).equals(id) ) {
                return people.remove(index);
            }
            index++;
        }
        throw new PersonNotFoundException("Person with name %s %s not found!".formatted(p.getFirstName(), p.getLastName()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////                      PRIVATE METHODS                  ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updatePersonInformation(Person current, Person update) {
        current.setLastName(update.getLastName());
        current.setFirstName(update.getFirstName());
        current.setAddress(update.getAddress());
        current.setCity(update.getCity());
        current.setZip(update.getZip());
        current.setEmail(update.getEmail());
        current.setPhone(update.getPhone());
    }

}
