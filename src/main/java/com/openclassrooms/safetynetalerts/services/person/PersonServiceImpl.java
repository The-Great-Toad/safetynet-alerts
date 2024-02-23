package com.openclassrooms.safetynetalerts.services.person;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepository;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private FirestationService firestationService;

    public List<Person> getAllPerson() {
        return personRepository.getListPersons();
    }

    public boolean savePerson(Person p){
        return personRepository.savePerson(p);
    }

    public Person updatePerson(Person p) {
        return personRepository.updatePerson(p);
    }

    public Person deletePerson(Person p) {
        return personRepository.deletePerson(p);
    }

    @Override
    public List<ChildDto> getChildrenByAdress(String address) {
        List<Person> persons = personRepository.getPersonByAddress(address);
        return getChildrenFromPersonList(persons);
    }

    @Override
    public List<String> getPhonesByFirestationNumber(int stationNumber) {
        // Récupérer les adresses desservies par la caserne de pompiers
        List<String> addresses = firestationService.getAddressesByStationNumber(stationNumber);

        // Filtrer les personnes par adresse
        return personRepository.getListPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }

    private List<ChildDto> getChildrenFromPersonList(List<Person> persons) {
        List<ChildDto> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();

        persons.forEach(person -> {
            MedicalRecord md = medicalRecordService.getMedicalRecordByFirstAndLastName(person.getFirstName(), person.getLastName());
            int age = calculateAge(md);
            if (age < 18) {
                children.add(new ChildDto(person.getFirstName(), person.getLastName(), age));
            } else {
                adults.add(person);
            }
        });

        if (!children.isEmpty()) {
            children.forEach(childDto -> childDto.getRelatives().addAll(adults));
        }

        return children;
    }

    public Integer calculateAge(MedicalRecord md) {
        LocalDate birthDate = md.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        return getAllPerson().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }
}
