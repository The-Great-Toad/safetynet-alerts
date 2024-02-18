package com.openclassrooms.safetynetalerts.repositories.person;

import com.openclassrooms.safetynetalerts.models.Person;

public interface PersonRepository {

    boolean savePerson(Person p);

    Person updatePerson(Person updatedPerson);

    Person deletePerson(Person p);
}
