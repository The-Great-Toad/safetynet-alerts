package com.openclassrooms.safetynetalerts.services.medicalrecord;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecord> getAllMedicalRecords();

    Boolean saveMedicalRecord(MedicalRecord medicalRecord);

    MedicalRecord updateMedicalRecord(MedicalRecord toUpdate);

    MedicalRecord deleteMedicalRecord(MedicalRecord toDelete);

    MedicalRecord getMedicalRecordByFirstAndLastName(String firstName, String lastName);
}
