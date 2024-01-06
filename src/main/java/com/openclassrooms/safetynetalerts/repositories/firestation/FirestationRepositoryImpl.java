package com.openclassrooms.safetynetalerts.repositories.firestation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class FirestationRepositoryImpl implements FirestationRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    public static List<Firestation> firestations;

    public List<Firestation> getAllFirestation() {
        return firestations;
    }

    @Override
    public Boolean saveFiresation(Firestation firestation) {

        if (firestations.contains(firestation)) {
            logger.error("[ERROR] - Firestation already in the list: {}", firestation);
            throw new IllegalArgumentException("Firestation already in the list!");
        }
        return firestations.add(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {

        for (Firestation currentStation: firestations) {
            if (currentStation.getAddress().equals(toUpdate.getAddress())) {
                if (currentStation.getStation() != toUpdate.getStation()) {
                    System.out.printf("[INFO] Station number updated from %d to %d%n", currentStation.getStation(), toUpdate.getStation());
                    currentStation.setStation(toUpdate.getStation());
                    return toUpdate;
                }
                throw new IllegalArgumentException("Station number provided already set for this address.");
            }
        }
        throw new NoSuchElementException("Address %s not found!".formatted(toUpdate.getAddress()));
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {

        if (firestations.contains(toDelete)) {
            System.out.printf("[INFO] Deleting %s%n ", toDelete);
            return firestations.remove(toDelete);
        }
        throw new NoSuchElementException("Address %s not found!".formatted(toDelete.getAddress()));
    }
}
