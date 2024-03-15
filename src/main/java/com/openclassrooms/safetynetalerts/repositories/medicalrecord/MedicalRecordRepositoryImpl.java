package com.openclassrooms.safetynetalerts.repositories.medicalrecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.repositories.DataObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepositoryImpl.class);

    private final String loggingNameRef = "[MedicalRecordRepository]";

    @Autowired
    private DataObjectRepository dataObjectRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataObjectRepository.getMedicalRecords();
    }

    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        String fullname = medicalRecord.getFirstName() + " " + medicalRecord.getLastName();

        if (!medicalRecords.contains(medicalRecord)) {
            logger.debug("{} - Adding {}'s medical record to the list - {}", loggingNameRef, fullname, medicalRecord);
            medicalRecords.add(medicalRecord);
            return medicalRecord;
        }
        final String error = String.format("%s - %s's medical record already in the list", loggingNameRef, fullname);
        logger.error(error);
        throw new IllegalArgumentException(error);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord toUpdate) {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        String fullname = toUpdate.getFirstName() + " " + toUpdate.getLastName();

        for (MedicalRecord currentMD : medicalRecords) {
            String currentFullName = currentMD.getFirstName() + " " + currentMD.getLastName();
            if (currentFullName.equals(fullname)) {
                int index = medicalRecords.indexOf(currentMD);
                logger.debug("[MedicalRecordRepository] - Adding {}'s medical record to the list:\nOld MD: {}\nNew MD: {}", fullname, currentMD, toUpdate);
                medicalRecords.set(index, toUpdate);
                return medicalRecords.get(index);
            }
        }
        final String error = String.format("%s - %s's medical record not found", loggingNameRef, fullname);
        logger.error(error);
        throw new NoSuchElementException(error);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(MedicalRecord toDelete) {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        String fullname = toDelete.getFirstName() + " " + toDelete.getLastName();
        int index = 0;
        for (MedicalRecord md : medicalRecords) {
            String currentFullName = md.getFirstName() + " " + md.getLastName();
            if (currentFullName.equals(fullname)) {
                logger.debug("{} - Removed {}'s medical record from list", loggingNameRef, fullname);
                return medicalRecords.remove(index);
            }
            index++;
        }
        final String error = String.format("%s - %s's medical record not found!", loggingNameRef, fullname);
        logger.error(error);
        throw new NoSuchElementException(error);
    }
}
