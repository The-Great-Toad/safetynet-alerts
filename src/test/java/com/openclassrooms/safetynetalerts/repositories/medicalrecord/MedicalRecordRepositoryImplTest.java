package com.openclassrooms.safetynetalerts.repositories.medicalrecord;

import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalRecordRepositoryImplTest extends TestUtils {

    @Autowired
    private MedicalRecordRepositoryImpl repository;

    @Test
    void getAllMedicalRecordsTest() {
        List<MedicalRecord> records = repository.getAllMedicalRecords();

        assertNotNull(records);
        assertTrue(records.size() >= 23);
    }

    @Test
    void saveMedicalRecordTest_success() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        MedicalRecord result = repository.saveMedicalRecord(medicalRecord);

        assertEquals(medicalRecord, result);
    }

    @Test
    void saveMedicalRecordTest_IllegalArgumentException() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        medicalRecord.setLastName("SAVE - already in list");
        repository.saveMedicalRecord(medicalRecord);

        assertThrows(IllegalArgumentException.class, () -> repository.saveMedicalRecord(medicalRecord));
    }
    @Test
    void updateMedicalRecordTest_success() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        medicalRecord.setLastName("UPDATE");
        repository.saveMedicalRecord(medicalRecord);

        medicalRecord.setBirthdate(getDate("09/03/1975"));
        MedicalRecord result = repository.updateMedicalRecord(medicalRecord);

        assertNotNull(result);
        assertEquals(medicalRecord, result);
    }

    @Test
    void updateMedicalRecordTest_NoSuchElementException() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        medicalRecord.setLastName("UPDATE failed");

        assertThrows(NoSuchElementException.class, () -> repository.updateMedicalRecord(medicalRecord));

    }

    @Test
    void deleteMedicalRecordTest_success() throws ParseException {
        MedicalRecord medicalRecord = createMedicalRecord();
        medicalRecord.setLastName("DELETE");
        repository.saveMedicalRecord(medicalRecord);

        MedicalRecord result = repository.deleteMedicalRecord(medicalRecord);

        assertNotNull(result);
        assertEquals(medicalRecord, result);
    }

    @Test
    void deleteMedicalRecordTest_NoSuchElementException() throws ParseException {
        MedicalRecord medicalRecord = getMedicalRecord();

        assertThrows(NoSuchElementException.class, () -> repository.deleteMedicalRecord(medicalRecord));

    }
}