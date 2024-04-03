package com.openclassrooms.safetynetalerts.repositories.firestation;

import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.repositories.DataObjectRepository;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class FirestationRepositoryImpl implements FirestationRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
    private final String loggingNameRef = "[FireStationRepository]";

    @Autowired
    private DataObjectRepository dataObjectRepository;

    public List<Firestation> getAllFirestation() {
        return dataObjectRepository.getFirestations();
    }

    @Override
    public Boolean saveFiresation(Firestation firestation) {
        List<Firestation> firestations = getAllFirestation();

        if (firestations.contains(firestation)) {
            final String error = String.format("Fire station already in the list: %s", firestation);
            logger.error("{} - {}", loggingNameRef, error);
            throw new IllegalArgumentException(error);
        }

        return firestations.add(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {
        List<Firestation> firestations = getAllFirestation();

        for (Firestation currentStation: firestations) {
            if (currentStation.getAddress().equals(toUpdate.getAddress())) {
                currentStation.setStation(toUpdate.getStation());
                logger.debug("{} - Station number updated from {} to {}", loggingNameRef, currentStation.getStation(), toUpdate.getStation());
                return toUpdate;
            }
        }
        final String error = String.format("Address %s not found", toUpdate.getAddress());
        logger.error("{} - {} - {}", loggingNameRef, error, toUpdate);
        throw new NoSuchElementException(error);
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {
        List<Firestation> firestations = getAllFirestation();

        if (firestations.contains(toDelete)) {
            logger.debug("{} - Deleting {}", loggingNameRef, toDelete);
            return firestations.remove(toDelete);
        }
        final String error = String.format("Address %s not found", toDelete.getAddress());
        logger.error("{} - {} - {}", loggingNameRef, error, toDelete);
        throw new NoSuchElementException(error);
    }

    @Override
    public List<String> getAddressesByStationNumber(int stationNumber) {
        List<String> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getStation() == stationNumber)
                .map(Firestation::getAddress)
                .toList();

        if (result.isEmpty()) {
            final String error = String.format("Station number not found: %s", stationNumber);
            logger.error("{} - {}", loggingNameRef, error);
            throw new NoSuchElementException(error);
        }
        return result;
    }

    @Override
    public List<Firestation> getFirestationByAddress(String address) {
        List<Firestation> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .toList();

        if (result.isEmpty()) {
            final String error = String.format("No match found for address: %s", address);
            logger.error("{} - {}", loggingNameRef, error);
            throw new NoSuchElementException(error);
        }
        return result;
    }

    @Override
    public List<Firestation> getFirestationByStationNumber(Integer station) {
        List<Firestation> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getStation() == station)
                .toList();

        if (result.isEmpty()) {
            final String error = String.format("Fire station not found. Invalid station number: %s", station);
            logger.error("{} - {}", loggingNameRef, error);
            throw new NoSuchElementException(error);
        }
        return result;
    }
}
