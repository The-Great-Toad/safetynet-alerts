package com.openclassrooms.safetynetalerts.services.firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.*;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepository;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class FirestationServiceImpl implements FirestationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirestationService.class);
    private final String LOG_ID = "[FirestationService]";

    @Autowired
    private FirestationRepository firestationRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Firestation> getAllFireStation() {
        return firestationRepository.getAllFirestation();
    }

    @Override
    public Boolean saveFireStation(Firestation firestation) {
        return firestationRepository.saveFiresation(firestation);
    }

    @Override
    public Firestation updateFireStation(Firestation toUpdate) {
        return firestationRepository.updateFirestation(toUpdate);
    }

    @Override
    public Boolean deleteFireStation(Firestation toDelete) {
        return firestationRepository.deleteFirestation(toDelete);
    }

    @Override
    public PersonsCoveredByFirestation getPersonCoveredByFireStation(int stationNumber) throws JsonProcessingException {
        List<PersonDto> personsDto;
        List<String> addresses = firestationRepository.getAddressesByStationNumber(stationNumber);

        // Retrieve persons by firestation's address
        List<Person> persons = personService.getAllPerson();
        List<Person> personsCoveredByFirestationProvided = persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();

        // Map Person to PersonDto
        String personsJson = objectMapper.writeValueAsString(personsCoveredByFirestationProvided);
        personsDto = objectMapper.readValue(personsJson, new TypeReference<>() {});

        // Count the number of people over & under 18 years of age
        int numberAdults = 0;
        int numberChildren = 0;

        List<Integer> ages = personsDto.stream().flatMap(personDto -> {
            MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(personDto.getFirstName(), personDto.getLastName());
            if (md != null) {
                return Stream.of(personService.calculateAge(md));
            } else {
                return Stream.empty();
            }
        }).toList();

        for (Integer age : ages) {
            if (age >= 18) {
                numberAdults++;
            } else {
                numberChildren++;
            }
        }

        return new PersonsCoveredByFirestation(personsDto, numberAdults, numberChildren);
    }

    @Override
    public List<String> getAddressesByStationNumber(int stationNumber) {
        return firestationRepository.getAddressesByStationNumber(stationNumber);
    }

    @Override
    public ResidentAndFirestationDto getResidentAndFireStationDto(String address) {
        List<Firestation> firestationList = firestationRepository.getFirestationByAddress(address);

        if (!firestationList.isEmpty()) {

            Firestation firestation = firestationList.get(0);
            List<Person> persons = personService.getPersonsByAddress(address);

            List<ResidentDto> residents = new ArrayList<>();
            
            persons.forEach(person -> {
                try {
                    residents.add(mapPersonToResidentDto(person));
                    
                } catch (JsonProcessingException e) {
                    LOGGER.error("{} - ERROR - Parsing error encounter with personString: {}", LOG_ID, e.getMessage());
                }
            });

            return new ResidentAndFirestationDto(residents, firestation.getStation());
        }
        return new ResidentAndFirestationDto();
    }

    @Override
    public HomeDto getHomeServedByStations(List<Integer> stations) {
        Map<String, List<ResidentDto>> residents = new HashMap<>();

        stations.forEach(station -> {

            List<Firestation> firestationList = firestationRepository.getFirestationByStationNumber(station);

            if (!firestationList.isEmpty()) {

                Firestation firestation = firestationList.get(0);
                List<Person> persons = personService.getPersonsByAddress(firestation.getAddress());

                persons.forEach(person -> {
                    try {
                        ResidentDto residentDto = mapPersonToResidentDto(person);
                        String address = person.getAddress();

                        if (residents.containsKey(address)) {
                            residents.get(address).add(residentDto);

                        } else {
                            residents.put(address, new ArrayList<>(List.of(residentDto)));
                        }
                    } catch (JsonProcessingException e) {
                        final String error = String.format("%s - ERROR - Parsing error encounter with personString: %s", LOG_ID, e.getMessage());
                        LOGGER.error(error);
                    }
                });
            }
        });
        return new HomeDto(residents);
    }

    private ResidentDto mapPersonToResidentDto(Person person) throws JsonProcessingException {
        MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
        int age = personService.calculateAge(md);

        String personString = objectMapper.writeValueAsString(person);
        ResidentDto residentDto = objectMapper.readValue(personString, ResidentDto.class);

        residentDto.setAge(age);
        residentDto.setMedications(md.getMedications());
        residentDto.setAllergies(md.getAllergies());

        return residentDto;
    }
}
