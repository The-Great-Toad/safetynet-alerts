package com.openclassrooms.safetynetalerts.repositories;

import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestationRepositoryImplTest {

    @Autowired
    private FirestationRepositoryImpl repository;

    @Test
    void getAllFirestationTest() {
        List<Firestation> firestations = repository.getAllFirestation();

        assertFalse(firestations.isEmpty());
        assertTrue(firestations.size() >= 13);
    }

    @Test
    void saveFiresationTest_success() {
        Firestation firestation = new Firestation("test save", 1);

        Boolean result = repository.saveFiresation(firestation);

        assertTrue(result);
    }

    @Test
    void saveFiresationTest_IllegalArgumentException() {
        Firestation firestation = new Firestation("1509 Culver St", 3);

        assertThrows(IllegalArgumentException.class, () -> repository.saveFiresation(firestation));
    }

    @Test
    void updateFirestationTest_success() {
        Firestation firestation = new Firestation("1509 Culver St", 4);

        Firestation result = repository.updateFirestation(firestation);

        assertEquals(firestation, result);
    }

    @Test
    void updateFiresationTest_NoSuchElementException() {
        Firestation firestation = new Firestation("test update", 3);

        assertThrows(NoSuchElementException.class, () -> repository.updateFirestation(firestation));
    }

    @Test
    void deleteFirestationTest() {
        Firestation firestation = new Firestation("test delete", 1);
        repository.saveFiresation(firestation);

        Boolean result = repository.deleteFirestation(firestation);

        assertTrue(result);
    }

    @Test
    void deleteFiresationTest_NoSuchElementException() {
        Firestation firestation = new Firestation("test delete", 2);

        assertThrows(NoSuchElementException.class, () -> repository.deleteFirestation(firestation));
    }

    @Test
    void getAddressesByStationNumberTest() {
        int stationNumber = 2;
        List<String> addresses = repository.getAddressesByStationNumber(stationNumber);
        assertAll(() -> {
            assertNotNull(addresses);
            assertEquals(3, addresses.size());
        });
    }

    @Test
    void getAddressesByStationNumberTest_NoSuchElementException() {
        int stationNumber = 99;

        assertThrows(NoSuchElementException.class, () -> repository.getAddressesByStationNumber(stationNumber));
    }

    @Test
    void getFirestationByAddressTest() {
        String address = "1509 Culver St";
        Firestation result = repository.getFirestationByAddress(address).get(0);
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(address, result.getAddress());
        });
    }

    @Test
    void getFirestationByAddressTest_NoSuchElementException() {
        String address = "Not Found";

        assertThrows(NoSuchElementException.class, () -> repository.getFirestationByAddress(address));
    }

    @Test
    void getFirestationByStationNumberTest() {
        int stationNumber = 2;
        List<Firestation> result = repository.getFirestationByStationNumber(stationNumber);
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(stationNumber, result.get(0).getStation());
        });
    }

    @Test
    void getFirestationByStationNumberTest_NoSuchElementException() {
        int stationNumber = 99;

        assertThrows(NoSuchElementException.class, () -> repository.getAddressesByStationNumber(stationNumber));
    }
}