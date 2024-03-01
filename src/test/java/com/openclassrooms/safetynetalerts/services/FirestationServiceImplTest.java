package com.openclassrooms.safetynetalerts.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.*;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepositoryImpl;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationServiceImpl;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirestationServiceImplTest extends TestUtils {

    @InjectMocks
    private FirestationServiceImpl firestationService;
    @Mock
    private FirestationRepositoryImpl firestationRepository;
    @Mock
    private PersonService personService;
    @Mock
    private MedicalRecordService medicalRecordService;
    @Mock
    private ObjectMapper mapper;

    @Test
    void getAllFirestationTest() {
        when(firestationRepository.getAllFirestation()).thenReturn(createFirestationsList());

        List<Firestation> result = firestationService.getAllFireStation();

        assertEquals(4, result.size());
        assertEquals(createFirestationsList(), result);
    }

    @Test
    void saveFiresationTest() {
        Firestation firestation = new Firestation();
        when(firestationRepository.saveFiresation(any(Firestation.class))).thenReturn(true);

        Boolean result = firestationService.saveFireStation(firestation);

        assertTrue(result);
    }

    @Test
    void updateFirestationTest() {
        Firestation firestation = new Firestation("123", 4);
        when(firestationRepository.updateFirestation(any(Firestation.class))).thenReturn(firestation);

        Firestation result = firestationService.updateFireStation(firestation);

        assertEquals(firestation, result);
    }

    @Test
    void deleteFirestationTest() {
        Firestation firestation = new Firestation();
        when(firestationRepository.deleteFirestation(any(Firestation.class))).thenReturn(true);

        Boolean result = firestationService.deleteFireStation(firestation);

        assertTrue(result);
    }

    @Test
    void getPersonCoveredByFireStationTest() throws JsonProcessingException, ParseException {
        String address = "Test";
        List<String> addresses = List.of(address);
        when(firestationRepository.getAddressesByStationNumber(anyInt())).thenReturn(addresses);

        List<Person> personList = createPersonList();
        personList.forEach(person -> person.setAddress(address));
        when(personService.getAllPerson()).thenReturn(personList);

        String personJson = "test";
        when(mapper.writeValueAsString(any())).thenReturn(personJson);
        PersonDto personDto = PersonDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        List<PersonDto> personDtoList = List.of(personDto);
        when(mapper.readValue(eq("test"), any(TypeReference.class))).thenReturn(personDtoList);

        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.getMedicalRecordByFirstAndLastName(anyString(), anyString()))
                .thenReturn(medicalRecord);

        PersonsCoveredByFirestation expectedResult = new PersonsCoveredByFirestation(personDtoList, 0, 1);
        PersonsCoveredByFirestation result = firestationService.getPersonCoveredByFireStation(2);

        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(firestationRepository, times(1)).getAddressesByStationNumber(anyInt());
        verify(personService, times(1)).getAllPerson();
        verify(mapper, times(1)).writeValueAsString(any());
        verify(mapper, times(1)).readValue(eq("test"), any(TypeReference.class));
        verify(medicalRecordService, times(1)).getMedicalRecordByFirstAndLastName(anyString(), anyString());
    }

    @Test
    void getAddressesByStationNumberTest() {
        List<String> expectedResult = new ArrayList<>();
        when(firestationRepository.getAddressesByStationNumber(anyInt())).thenReturn(expectedResult);

        List<String> result = firestationService.getAddressesByStationNumber(anyInt());

        assertEquals(expectedResult, result);
        verify(firestationRepository, times(1)).getAddressesByStationNumber(anyInt());
    }

    @Test
    void getResidentAndFireStationDtoTest() throws ParseException, JsonProcessingException {
        String address = "";
        Firestation firestation = new Firestation(address, 2);
        when(firestationRepository.getFirestationByAddress(anyString())).thenReturn(firestation);

        when(personService.getPersonsByAddress(anyString())).thenReturn(List.of(createPerson()));
        when(medicalRecordService.getMedicalRecordByFirstAndLastName(anyString(), anyString()))
                .thenReturn(getMedicalRecord());
        when(personService.calculateAge(any(MedicalRecord.class))).thenReturn(15);
        when(mapper.writeValueAsString(any())).thenReturn("test");

        ResidentDto residentDto = createResidentDto();
        when(mapper.readValue(anyString(), eq(ResidentDto.class))).thenReturn(residentDto);

        List<ResidentDto> residentDtoList = List.of(residentDto);
        ResidentAndFirestationDto expectedResult = new ResidentAndFirestationDto(residentDtoList, 2);
        ResidentAndFirestationDto result = firestationService.getResidentAndFireStationDto(address);

        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(firestationRepository, times(1)).getFirestationByAddress(anyString());
        verify(personService, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(1)).getMedicalRecordByFirstAndLastName(anyString(), anyString());
        verify(personService, times(1)).calculateAge(any(MedicalRecord.class));
        verify(mapper, times(1)).writeValueAsString(any());
        verify(mapper, times(1)).readValue(anyString(), eq(ResidentDto.class));
    }

    @Test
    void getHomeServedByStationsTest() throws ParseException, JsonProcessingException {
        List<Integer> stations = List.of(2);
        Firestation firestation = createFireStation();
        when(firestationRepository.getFirestationByStationNumber(anyInt())).thenReturn(firestation);

        String address = "123 test";
        Person person = createPerson();
        person.setAddress(address);
        List<Person> personList = List.of(person);
        when(personService.getPersonsByAddress(anyString())).thenReturn(personList);

        MedicalRecord medicalRecord = getMedicalRecord();
        when(medicalRecordService.getMedicalRecordByFirstAndLastName(anyString(), anyString()))
                .thenReturn(medicalRecord);

        List<ResidentDto> residents = new ArrayList<>();
        Map<String, List<ResidentDto>> homes = new HashMap<>();
        personList.forEach(p -> {
            try {
                String personString = mapper.writeValueAsString(p);
                when(mapper.writeValueAsString(eq(p))).thenReturn(personString);
                String phone = "123-456-789";
                ResidentDto residentDto = ResidentDto.builder()
                        .lastName(p.getLastName())
                        .phone(phone)
                        .build();
                residents.add(residentDto);
                when(mapper.readValue(eq(personString), eq(ResidentDto.class))).thenReturn(residentDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        homes.put(address, residents);
        HomeDto expectedResult = HomeDto.builder()
                .homes(homes)
                .build();
        HomeDto result = firestationService.getHomeServedByStations(stations);

        assertNotNull(result);
        assertEquals(expectedResult, result);

        verify(firestationRepository, times(1)).getFirestationByStationNumber(anyInt());
        verify(personService, times(1)).getPersonsByAddress(anyString());
        verify(medicalRecordService, times(1)).getMedicalRecordByFirstAndLastName(anyString(), anyString());
        verify(mapper, times(2)).writeValueAsString(eq(person));
        verify(mapper, times(2)).readValue(
                eq(mapper.writeValueAsString(person)), eq(ResidentDto.class));
    }
}