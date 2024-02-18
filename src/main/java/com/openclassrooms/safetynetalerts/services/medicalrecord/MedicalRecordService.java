package com.openclassrooms.safetynetalerts.services.medicalrecord;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecord> getAllMedicalRecords();

    Boolean saveMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord toUpdate);

    MedicalRecord deleteMedicalRecord(MedicalRecord toDelete);
}
