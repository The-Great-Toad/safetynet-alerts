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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class FirestationServiceImpl implements FirestationService {

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
        Firestation firestation = firestationRepository.getFirestationByAddress(address);
        List<Person> persons = personService.getPersonsByAddress(address);

        List<ResidentDto> residents = persons.stream().map(person -> {
            MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
            int age = personService.calculateAge(md);
            try {
                String personString = objectMapper.writeValueAsString(person);
                ResidentDto residentDto = objectMapper.readValue(personString, ResidentDto.class);
                residentDto.setAge(age);
                residentDto.setMedications(md.getMedications());
                residentDto.setAllergies(md.getAllergies());
                return residentDto;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return new ResidentAndFirestationDto(residents, firestation.getStation());
    }

    @Override
    public HomeDto getHomeServedByStations(List<Integer> stations) {
        Map<String, List<ResidentDto>> residents = new HashMap<>();

        stations.forEach(station -> {
            // Récupérer l'adresse de la station
            Firestation firestation = firestationRepository.getFirestationByStationNumber(station);
            // Récupérer les personnes by adresse
            List<Person> persons = personService.getPersonsByAddress(firestation.getAddress());

            persons.forEach(person -> {
                try {
                    // récupérer le dossier médicale
                    MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
                    // Calculer l'age
                    int age = personService.calculateAge(medicalRecord);
                    // Convertir les personnes récupérées en ResidentDto
                    String personString = objectMapper.writeValueAsString(person);
                    ResidentDto residentDto = objectMapper.readValue(personString, ResidentDto.class);
                    residentDto.setAge(age);
                    residentDto.setMedications(medicalRecord.getMedications());
                    residentDto.setAllergies(medicalRecord.getAllergies());
                    // Ajouter à la Map
                    String address = person.getAddress();
                    if (residents.containsKey(address)) {
                        residents.get(address).add(residentDto);
                    } else {
                        residents.put(address, new ArrayList<>(List.of(residentDto)));
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        return new HomeDto(residents);
    }
}
