package com.openclassrooms.safetynetalerts.service;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPerson() {
        return personRepository.getAllPerson();
    }

    public Person savePerson(Person p) throws Exception {
        if (validatePersonInfo(p)) {
            return personRepository.savePerson(p);
        } else {
            throw new Exception("Person's information invalid ! Must have unique first & last name.");
        }
    }

    private boolean validatePersonInfo(Person p) {
        List<Person> people = personRepository.getAllPerson();

        // Check for valid first & last name
        if (Objects.isNull(p.getFirstName()) || Objects.isNull(p.getLastName())) {
            return false;
        }
        // Check if p already in the list
        for (Person person : people) {
            if ((person.getLastName() + person.getLastName()).equals(p.getLastName() + p.getLastName())) {
                return false;
            }
        }
        return true;
    }
}
