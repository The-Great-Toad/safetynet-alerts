package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.PersonRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl service;
    @Mock
    private PersonRepositoryImpl repository;

    private static final List<Person> persons = createPersonList();

    private static final Person person = new Person("John", "Doe");

    @Test
    void getAllPerson() {
        when(repository.getAllPerson()).thenReturn(persons);

        List<Person> result = service.getAllPerson();

        assertNotNull(result);
        assertEquals(createPersonList(), result);
    }

    @Test
    void savePerson() {
        when(repository.savePerson(person)).thenReturn(person);

        Person result = service.savePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
    }

    @Test
    void updatePerson() {
        when(repository.updatePerson(person)).thenReturn(person);

        Person result = service.updatePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
    }

    @Test
    void deletePerson() {
        when(repository.deletePerson(person)).thenReturn(person);

        Person result = service.deletePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
    }



    private static List<Person> createPersonList() {
        Person p1 = new Person("Erick", "Pattisson");
        Person p2 = new Person("Sam", "Embet");
        Person p3 = new Person("Todd", "You");
        Person p4 = new Person("Brad", "Rie");

        return asList(p1, p2, p3, p4);
    }
}