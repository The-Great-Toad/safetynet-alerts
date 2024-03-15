package com.openclassrooms.safetynetalerts.repositories.medicalrecord;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository {

    List<MedicalRecord> getAllMedicalRecords();
    MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord toUpdate);

    MedicalRecord deleteMedicalRecord(MedicalRecord toDelete);
}
