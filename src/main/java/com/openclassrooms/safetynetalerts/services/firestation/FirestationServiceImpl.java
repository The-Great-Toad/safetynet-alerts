package com.openclassrooms.safetynetalerts.services.firestation;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepositoryImpl;
import com.openclassrooms.safetynetalerts.services.person.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationServiceImpl implements FirestationService {

    @Autowired
    private FirestationRepositoryImpl firestationRepositoryImpl;

    @Autowired
    private PersonServiceImpl personService;

    public List<Firestation> getAllFirestation() {
        return firestationRepositoryImpl.getAllFirestation();
    }

    @Override
    public Boolean saveFiresation(Firestation firestation) {
        return firestationRepositoryImpl.saveFiresation(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {
        return firestationRepositoryImpl.updateFirestation(toUpdate);
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {
        return firestationRepositoryImpl.deleteFirestation(toDelete);
    }

    public List<Person> getPersonCoveredByFirestation(int stationNumber) {
        List<Person> result = new ArrayList<>();
        // Retrieve firestations by station number
        List<Firestation> firestations = firestationRepositoryImpl.getFirestationByStation(stationNumber);
        firestations.forEach(System.out::println);
        // Retrieve persons by addresses
        List<Person> persons = personService.getAllPerson();
        for (Firestation firestation : firestations) {
            List<Person> people = persons.stream()
                    .filter(person -> person.getAddress().equals(firestation.getAddress()))
                    .toList();
            result.addAll(people);
        }
        // Format the person object; only keep ; prénom, nom, adresse, numéro de téléphone
        // Add décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou moins) dans la zone desservie.
        return result;
    }
}
