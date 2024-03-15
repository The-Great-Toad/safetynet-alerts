package com.openclassrooms.safetynetalerts.services;

import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.repositories.medicalrecord.MedicalRecordRepository;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest extends TestUtils {

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Test
    void getAllMedicalRecordsTest() throws ParseException {
        List<MedicalRecord> medicalRecords = List.of(createMedicalRecord());
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        assertNotNull(result);
        assertEquals(medicalRecords, result);
        verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
    }

    @Test
    void saveMedicalRecordTest() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        when(medicalRecordRepository.saveMedicalRecord(medicalRecord)).thenReturn(medicalRecord);

        MedicalRecord result = medicalRecordService.saveMedicalRecord(medicalRecord);

        assertNotNull(result);
        assertEquals(medicalRecord, result);
        verify(medicalRecordRepository, times(1)).saveMedicalRecord(medicalRecord);
    }

    @Test
    void updateMedicalRecordTest() throws ParseException {
        MedicalRecord toUpdate = createMedicalRecord();
        when(medicalRecordRepository.updateMedicalRecord(toUpdate)).thenReturn(toUpdate);

        MedicalRecord result = medicalRecordService.updateMedicalRecord(toUpdate);

        assertNotNull(result);
        assertEquals(toUpdate, result);
        verify(medicalRecordRepository, times(1)).updateMedicalRecord(toUpdate);
    }

    @Test
    void deleteMedicalRecordTest() throws ParseException {
        MedicalRecord toDelete = createMedicalRecord();
        when(medicalRecordRepository.updateMedicalRecord(toDelete)).thenReturn(toDelete);

        MedicalRecord result = medicalRecordService.updateMedicalRecord(toDelete);

        assertNotNull(result);
        assertEquals(toDelete, result);
        verify(medicalRecordRepository, times(1)).updateMedicalRecord(toDelete);
    }

    @Test
    void getMedicalRecordByFirstAndLastNameTest() throws ParseException {
        List<MedicalRecord> medicalRecords = List.of(createMedicalRecord());
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecords);

        String firstName = "Bob";
        String lastName = "Sponge";
        MedicalRecord result = medicalRecordService.getMedicalRecordByFirstAndLastName(firstName, lastName);

        assertNotNull(result);
        assertEquals(result.getFirstName(), firstName);
        assertEquals(result.getLastName(), lastName);
        assertEquals(medicalRecords.get(0), result);
        verify(medicalRecordRepository, times(1)).getAllMedicalRecords();
    }
}