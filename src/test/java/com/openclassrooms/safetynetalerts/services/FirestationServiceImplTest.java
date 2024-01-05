package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.repositories.FirestationRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationServiceImplTest {

    @InjectMocks
    private FirestationServiceImpl firestationService;

    @Mock
    private FirestationRepositoryImpl firestationRepository;

    @Test
    void getAllFirestationTest() {
        when(firestationRepository.getAllFirestation()).thenReturn(createFirestationsList());

        List<Firestation> result = firestationService.getAllFirestation();

        assertEquals(4, result.size());
        assertEquals(createFirestationsList(), result);
    }

    @Test
    void saveFiresationTest() {
        Firestation firestation = new Firestation();
        when(firestationRepository.saveFiresation(any(Firestation.class))).thenReturn(true);

        Boolean result = firestationService.saveFiresation(firestation);

        assertTrue(result);
    }

    @Test
    void updateFirestationTest() {
        Firestation firestation = new Firestation("123", 4);
        when(firestationRepository.updateFirestation(any(Firestation.class))).thenReturn(firestation);

        Firestation result = firestationService.updateFirestation(firestation);

        assertEquals(firestation, result);
    }

    @Test
    void deleteFirestationTest() {
        Firestation firestation = new Firestation();
        when(firestationRepository.deleteFirestation(any(Firestation.class))).thenReturn(true);

        Boolean result = firestationService.deleteFirestation(firestation);

        assertTrue(result);
    }

    private List<Firestation> createFirestationsList() {
        Firestation f1 = new Firestation("123 rue 123", 1);
        Firestation f2 = new Firestation("456 rue 456", 2);
        Firestation f3 = new Firestation("789 rue 789", 3);
        Firestation f4 = new Firestation("032 rue 032", 4);

        return List.of(f1, f2, f3, f4);
    }
}