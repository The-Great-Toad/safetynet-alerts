package com.openclassrooms.safetynetalerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

}
