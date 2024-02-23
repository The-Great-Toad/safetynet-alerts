package com.openclassrooms.safetynetalerts.services.firestation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.PersonDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepository;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
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

    public List<Firestation> getAllFirestation() {
        return firestationRepository.getAllFirestation();
    }

    @Override
    public Boolean saveFiresation(Firestation firestation) {
        return firestationRepository.saveFiresation(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {
        return firestationRepository.updateFirestation(toUpdate);
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {
        return firestationRepository.deleteFirestation(toDelete);
    }

    public PersonsCoveredByFirestation getPersonCoveredByFirestation(int stationNumber) throws JsonProcessingException {
        List<PersonDto> personsDto;
        List<String> addresses = firestationRepository.getAddressesByStationNumber(stationNumber);

        // Retrieve persons by firestation's address
        List<Person> persons = personService.getAllPerson();
        List<Person> personsCoveredByFirestationProvided = persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        // Map Person to PersonDto
        String personsJson = objectMapper.writeValueAsString(personsCoveredByFirestationProvided);
        personsDto = objectMapper.readValue(personsJson, new TypeReference<>() {});

        // Count the number of people over & under 18 years of age
        int numberAdults = 0;
        int numberChildren = 0;

        List<Integer> ages = personsDto.stream().flatMap(personDto -> {
            MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(personDto.getFirstName(), personDto.getLastName()).get(0);
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
//        for (MedicalRecord md : medicalRecords) {
//            String mdId = md.getFirstName() + " " + md.getLastName();
//            for (PersonDto p : personsDto) {
//                String pId = p.getFirstName() + " " + p.getLastName();
//                if (mdId.equals(pId)) {
//                    int age = Period.between(md.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
//                    if (age >= 18) {
//                        numberAdults++;
//                    } else {
//                        numberChildren++;
//                    }
//                }
//            }
//        }
        return new PersonsCoveredByFirestation(personsDto, numberAdults, numberChildren);
    }

    @Override
    public List<String> getAddressesByStationNumber(int stationNumber) {
        return firestationRepository.getAddressesByStationNumber(stationNumber);
    }
}
