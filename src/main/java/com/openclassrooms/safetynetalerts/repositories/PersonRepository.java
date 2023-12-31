package com.openclassrooms.safetynetalerts.repositories;

import com.openclassrooms.safetynetalerts.models.Person;

public interface PersonRepository {

    Person savePerson(Person p);

    Person updatePerson(Person updatedPerson);

    Person deletePerson(Person p);
}
