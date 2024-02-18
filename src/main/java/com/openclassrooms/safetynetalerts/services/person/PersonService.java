package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPerson();

    boolean savePerson(Person p);

    Person updatePerson(Person p);

    Person deletePerson(Person p);

    List<Person> getPersonsCoveredByFirestation(List<Firestation> firestations);
}
