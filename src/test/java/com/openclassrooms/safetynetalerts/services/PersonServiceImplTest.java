package com.openclassrooms.safetynetalerts.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ChildDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepository;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import com.openclassrooms.safetynetalerts.services.person.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest extends TestUtils {

    @InjectMocks
    private PersonServiceImpl personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private MedicalRecordService medicalRecordService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private FirestationService firestationService;

    private static final List<Person> persons = createPersonList();

    private static final Person person = new Person("John", "Doe");

    @Test
    void getAllPerson() {
        when(personRepository.getListPersons()).thenReturn(persons);

        List<Person> result = personService.getAllPerson();

        assertNotNull(result);
        assertEquals(createPersonList(), result);
        verify(personRepository, times(1)).getListPersons();
    }

    @Test
    void savePerson() {
        Person newPerson = createPerson();
        when(personRepository.savePerson(person)).thenReturn(newPerson);

        Person result = personService.savePerson(person);

        assertEquals(result, newPerson);
        verify(personRepository, times(1)).savePerson(person);
    }

    @Test
    void updatePerson() {
        when(personRepository.updatePerson(person)).thenReturn(person);

        Person result = personService.updatePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
        verify(personRepository, times(1)).updatePerson(person);
    }

    @Test
    void deletePerson() {
        when(personRepository.deletePerson(person)).thenReturn(person);

        Person result = personService.deletePerson(person);

        assertNotNull(result);
        assertEquals(person, result);
        verify(personRepository, times(1)).deletePerson(person);
    }

    @Test
    void getPersonsByLastNameTest() {
        when(personRepository.getPersonsByLastName(anyString())).thenReturn(persons);

        List<Person> result = personService.getPersonsByLastName(anyString());

        assertAll(() -> {
            assertNotNull(result);
            assertEquals(persons, result);
        });
        verify(personRepository, times(1)).getPersonsByLastName(anyString());
    }

    @Test
    void getPersonsByAddressTest() {
        when(personRepository.getPersonByAddress(anyString())).thenReturn(persons);

        List<Person> result = personService.getPersonsByAddress(anyString());

        assertAll(() -> {
            assertNotNull(result);
            assertEquals(persons, result);
        });
        verify(personRepository, times(1)).getPersonByAddress(anyString());
    }

    @Test
    void getPersonInfoByFirstAndLastNameTest() throws JsonProcessingException {
        List<Person> listTest = List.of(person);
        Person personTest = listTest.get(0);
        String lastName = personTest.getLastName();
        String firstName = personTest.getFirstName();
        when(personRepository.getPersonsByLastName(lastName)).thenReturn(listTest);

        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.getMedicalRecordByFirstAndLastName(firstName, lastName)).thenReturn(medicalRecord);

        when(objectMapper.writeValueAsString(any())).thenReturn("test");
        when(objectMapper.readValue(anyString(), eq(PersonInfoDto.class))).thenReturn(new PersonInfoDto());

        List<PersonInfoDto> result = personService.getPersonInfoByFirstAndLastName(firstName, lastName);

        assertEquals(1, result.size());
        PersonInfoDto dto = result.get(0);
        assertEquals(2, dto.getMedications().size());
        assertEquals(2, dto.getAllergies().size());

        verify(personRepository, times(1)).getPersonsByLastName(lastName);
        verify(medicalRecordService, times(1)).getMedicalRecordByFirstAndLastName(firstName, lastName);
        verify(objectMapper, times(1)).writeValueAsString(any());
        verify(objectMapper, times(1)).readValue(anyString(), eq(PersonInfoDto.class));
    }

    @Test
    void getResidentsEmailByCityTest() {
        String city = "Culver";
        String email = "Test";
        List<Person> listTest = createPersonList();
        listTest.forEach(person -> {
            person.setCity(city);
            person.setEmail(email);
        });

        when(personRepository.getListPersons()).thenReturn(listTest);

        List<String> result = personService.getResidentsEmailByCity(city);

        assertEquals(4, result.size());
        result.forEach(string -> assertEquals(email, string));
        verify(personRepository, times(1)).getListPersons();
    }

    @Test
    void getChildrenByAddressTest() {
        String address = "test";
        when(personRepository.getPersonByAddress(anyString())).thenReturn(persons);

        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.getMedicalRecordByFirstAndLastName(anyString(), anyString()))
                .thenReturn(medicalRecord);

        List<ChildDto> result = personService.getChildrenByAddress(address);

        assertEquals(4, result.size());
        assertEquals(result.get(0).getFirstName(), persons.get(0).getFirstName());
        verify(personRepository, times(1)).getPersonByAddress(address);
        verify(medicalRecordService, times(4))
                .getMedicalRecordByFirstAndLastName(anyString(), anyString());
    }

    @Test
    void getPhonesByFireStationNumberTest() {
        int stationNumber = 2;
        String address = "address";
        String phone = "phone";
        List<String> addresses = List.of(address);
        when(firestationService.getAddressesByStationNumber(stationNumber)).thenReturn(addresses);

        List<Person> listTest = createPersonList();
        listTest.forEach(person -> {
            person.setAddress(address);
            person.setPhone(phone);
        });
        when(personRepository.getListPersons()).thenReturn(listTest);

        List<String> results = personService.getPhonesByFireStationNumber(stationNumber);

        assertEquals(4, results.size());
        results.forEach(result -> assertEquals(phone, result));
        verify(firestationService, times(1)).getAddressesByStationNumber(stationNumber);
        verify(personRepository, times(1)).getListPersons();
    }
}