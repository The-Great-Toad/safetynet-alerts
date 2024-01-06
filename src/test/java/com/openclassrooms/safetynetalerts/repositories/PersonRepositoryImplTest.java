package com.openclassrooms.safetynetalerts.repositories;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryImplTest {

    @Autowired
    private PersonRepositoryImpl repository;

    @Test
    void getAllPersonTest() {
        List<Person> result = repository.getAllPerson();

        assertNotNull(result);
        assertTrue(result.size() >= 23);
    }

    @Test
    void savePersonTest() {
        Person newPerson = new Person("Test", "To be save");

        Person result = repository.savePerson(newPerson);

        assertNotNull(result);
        assertEquals(newPerson, result);
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

        assertThrows(PersonNotFoundException.class, () -> repository.updatePerson(updated));
    }

    @Test
    void deletePersonTest_success() {
        Person toDelete = new Person("To", "delete");

        repository.savePerson(toDelete);
        Person result = repository.deletePerson(toDelete);

        assertNotNull(result);
        assertEquals(toDelete, result);
    }
}

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class PersonRepositoryImplTest {
//
//    @InjectMocks
//    private PersonRepositoryImpl repository;
//    @Mock
//    private ObjectMapper mapper;
//
//    @Test
//    void getAllPersonTest_success() throws IOException {
//        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(createPersonList());
//
//        List<Person> result = repository.getAllPerson();
//
//        assertNotNull(result);
//        assertEquals(createPersonList(), result);
//    }
//
//    @Test
//    void getAllPersonTest_IOException() throws IOException {
//        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(new IOException("file doesn't exits!"));
//
//        List<Person> result = repository.getAllPerson();
//
//        assertEquals(new ArrayList<Person>(), result);
//    }
//
//    @Test
//    void savePersonTest() throws IOException {
//        Person newPerson = new Person("Test", "To be save");
//        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(createPersonList());
//
//        Person result = repository.savePerson(newPerson);
//
//        assertNotNull(result);
//        assertEquals(newPerson, result);
//    }
//
//    @Test
//    void updatePersonTest() {
//    }
//
//    @Test
//    void deletePersonTest() {
//    }
//
//
//    private static List<Person> createPersonList() {
//        Person p1 = new Person("Erick", "Pattisson");
//        Person p2 = new Person("Sam", "Embet");
//        Person p3 = new Person("Todd", "You");
//        Person p4 = new Person("Brad", "Rie");
//
//        return asList(p1, p2, p3, p4);
//    }
//}