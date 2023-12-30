package com.openclassrooms.safetynetalerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Autowired
    private ObjectMapper objectMapper;
    private static List<Person> people = new ArrayList<>();

    public List<Person> getAllPerson() {

        if (people.isEmpty()) {
            try {
                String filePath = "src/main/resources/person.json";
                people = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
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

    public Person updatePerson(Person updatedPerson) throws PersonNotFoundException {
        String id = updatedPerson.getFirstName() + updatedPerson.getLastName();

        for (Person currentPerson : people) {
            if ( (currentPerson.getFirstName() + currentPerson.getLastName()).equals(id) ) {
                updatePersonInformation(currentPerson, updatedPerson);
                System.out.printf("[INFO] Saving person: %s %n", updatedPerson);
                return currentPerson;
            }
        }
        throw new PersonNotFoundException("Person not find in the list");
    }

    public Person deletePerson(Person p) throws PersonNotFoundException {
        String id = p.getFirstName() + p.getLastName();
        int index = 0;
        for (Person currentPerson : people) {
            if ( (currentPerson.getFirstName() + currentPerson.getLastName()).equals(id) ) {
                return people.remove(index);
            }
            index++;
        }
        throw new PersonNotFoundException("Person not find in the list");
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
