package com.openclassrooms.safetynetalerts.services.medicalrecord;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.repositories.medicalrecord.MedicalRecordRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepositoryImpl medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    @Override
    public Boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord toUpdate) {
        return medicalRecordRepository.updateMedicalRecord(toUpdate);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(MedicalRecord toDelete) {
        return medicalRecordRepository.deleteMedicalRecord(toDelete);
    }

    @Override
    public MedicalRecord getMedicalRecordByFirstAndLastName(String firstName, String lastName) {
        return getAllMedicalRecords().stream()
                .filter(medicalRecord -> (firstName+lastName).equalsIgnoreCase(medicalRecord.getFirstName()+medicalRecord.getLastName()))
                .toList()
                .get(0);
    }
}
