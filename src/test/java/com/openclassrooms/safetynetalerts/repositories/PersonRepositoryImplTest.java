package com.openclassrooms.safetynetalerts.repositories;

import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryImplTest {

    @Autowired
    private PersonRepositoryImpl repository;

    @Test
    void getAllPersonTest() {
        List<Person> result = repository.getListPersons();

        assertNotNull(result);
        assertTrue(result.size() >= 23);
    }

    @Test
    void savePersonTest_success() {
        Person newPerson = new Person("Test", "To be save");

        Person result = repository.savePerson(newPerson);

        assertEquals(result, newPerson);
    }

    @Test
    void savePersonTest_IllegalArgumentException() {
        Person newPerson = new Person("Error", "To be save");
        repository.savePerson(newPerson);

        assertThrows(IllegalArgumentException.class, () -> repository.savePerson(newPerson));
    }

    @Test
    void updatePersonTest_success() {
        Person updated = new Person("To", "update");
        repository.savePerson(updated);
        updated.setEmail("email updated");

        Person result = repository.updatePerson(updated);

        assertNotNull(result);
        assertEquals(updated.getEmail(), "email updated");
    }

    @Test
    void updatePersonTest_PersonNotFoundException() {
        Person updated = new Person("Not", "In the list");

        assertThrows(NoSuchElementException.class, () -> repository.updatePerson(updated));
    }

    @Test
    void deletePersonTest_success() {
        Person toDelete = new Person("To", "delete");

        repository.savePerson(toDelete);
        Person result = repository.deletePerson(toDelete);

        assertNotNull(result);
        assertEquals(toDelete, result);
    }

    @Test
    void getPersonsByLastNameTest() {
        String lastName = "Boyd";

        List<Person> results = repository.getPersonsByLastName(lastName);

        assertEquals(6, results.size());
        results.forEach(result -> assertEquals(lastName, result.getLastName()));
    }

    @Test
    void getPersonByAddressTest() {
        String address = "1509 Culver St";

        List<Person> results = repository.getPersonByAddress(address);

        assertEquals(5, results.size());
        results.forEach(result -> assertEquals(address, result.getAddress()));
    }
}