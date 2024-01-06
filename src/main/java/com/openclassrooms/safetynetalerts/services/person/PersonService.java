package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.Person;

public interface PersonService {

    Person savePerson(Person p);

    Person updatePerson(Person p);

    Person deletePerson(Person p);
}
