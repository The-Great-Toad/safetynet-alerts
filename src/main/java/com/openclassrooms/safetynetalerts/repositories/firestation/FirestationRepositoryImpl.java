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
            final String error = String.format("%s - ERROR - Fire station already in the list: %s", loggingNameRef, firestation);
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        return firestations.add(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation toUpdate) {
        List<Firestation> firestations = getAllFirestation();

        for (Firestation currentStation: firestations) {
            if (currentStation.getAddress().equals(toUpdate.getAddress())) {
                if (currentStation.getStation() != toUpdate.getStation()) {
                    currentStation.setStation(toUpdate.getStation());
                    logger.debug("{} - Station number updated from {} to {}", loggingNameRef, currentStation.getStation(), toUpdate.getStation());
                    return toUpdate;
                }
                final String error = String.format("%s - ERROR - Station number provided already set for this address: %s", loggingNameRef, toUpdate);
                logger.error(error);
                throw new IllegalArgumentException(error);
            }
        }
        final String error = String.format("%s - ERROR - Address %s not found", loggingNameRef, toUpdate.getAddress());
        logger.error(error);
        throw new NoSuchElementException(error);
    }

    @Override
    public Boolean deleteFirestation(Firestation toDelete) {
        List<Firestation> firestations = getAllFirestation();

        if (firestations.contains(toDelete)) {
            logger.debug("{} - Deleting {}", loggingNameRef, toDelete);
            return firestations.remove(toDelete);
        }
        final String error = String.format("%s - ERROR - Address %s not found", loggingNameRef, toDelete.getAddress());
        logger.error(error);
        throw new NoSuchElementException(error);
    }

    @Override
    public List<String> getAddressesByStationNumber(int stationNumber) {
        List<String> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getStation() == stationNumber)
                .map(Firestation::getAddress)
                .toList();

        if (result.isEmpty()) {
            logger.debug("{} - No address found for station number: {}", loggingNameRef, stationNumber);
        }
        return result;
    }

    @Override
    public List<Firestation> getFirestationByAddress(String address) {
        List<Firestation> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .toList();

        if (result.isEmpty()) {
            logger.debug("{} - No match found for address: {}", loggingNameRef, address);
        }
        return result;
    }

    @Override
    public List<Firestation> getFirestationByStationNumber(Integer station) {
        List<Firestation> result = getAllFirestation().stream()
                .filter(firestation -> firestation.getStation() == station)
                .toList();

        if (result.isEmpty()) {
            logger.debug("{} - No match found for station number: {}", loggingNameRef, station);
        }
        return result;
    }
}
