package com.openclassrooms.safetynetalerts.repositories.person;

import com.openclassrooms.safetynetalerts.domain.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> getListPersons();

    Person savePerson(Person p);

    Person updatePerson(Person updatedPerson);

    Person deletePerson(Person p);

    List<Person> getPersonsByLastName(String lastName);

    List<Person> getPersonByAddress(String address);
}
