package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepositoryImpl personRepositoryImpl;

    public List<Person> getAllPerson() {
        return personRepositoryImpl.getAllPerson();
    }

    public boolean savePerson(Person p){
        return personRepositoryImpl.savePerson(p);
    }

    public Person updatePerson(Person p) {
        return personRepositoryImpl.updatePerson(p);
    }

    public Person deletePerson(Person p) {
        return personRepositoryImpl.deletePerson(p);
    }

    public List<Person> getPersonsCoveredByFirestation(List<Firestation> firestations) {
        List<Person> persons = getAllPerson();
        List<Person> personsCoveredByFirestationProvided = new ArrayList<>();
        for (Firestation firestation : firestations) {
            List<Person> tempList = persons.stream()
                    .filter(person -> person.getAddress().equals(firestation.getAddress()))
                    .toList();
            personsCoveredByFirestationProvided.addAll(tempList);
        }
        return personsCoveredByFirestationProvided;
    }
}
