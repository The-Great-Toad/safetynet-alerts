package com.openclassrooms.safetynetalerts.repositories.medicalrecord;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository {

    Boolean saveMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord toUpdate);

    MedicalRecord deleteMedicalRecord(MedicalRecord toDelete);
}
