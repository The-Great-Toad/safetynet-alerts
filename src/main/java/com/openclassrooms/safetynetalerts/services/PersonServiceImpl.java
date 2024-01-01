package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.PersonRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

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
