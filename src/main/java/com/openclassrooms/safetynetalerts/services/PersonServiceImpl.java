package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.PersonRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepositoryImpl personRepositoryImpl;

    public List<Person> getAllPerson() {
        return personRepositoryImpl.getAllPerson();
    }

    public Person savePerson(Person p){
        return personRepositoryImpl.savePerson(p);
    }

    public Person updatePerson(Person p) {
        return personRepositoryImpl.updatePerson(p);
    }

    public Person deletePerson(Person p) {
        return personRepositoryImpl.deletePerson(p);
    }
}
