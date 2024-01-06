package com.openclassrooms.safetynetalerts.services.medicalrecord;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;

public interface MedicalRecordService {

    Boolean saveMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord toUpdate);

    MedicalRecord deleteMedicalRecord(MedicalRecord toDelete);
}
