package com.openclassrooms.safetynetalerts.repositories.medicalrecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepositoryImpl.class);

    @Autowired
    private ObjectMapper mapper;

    public static List<MedicalRecord> medicalRecords;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }

    @Override
    public Boolean saveMedicalRecord(MedicalRecord medicalRecord) {

        String fullname = medicalRecord.getFirstName() + " " + medicalRecord.getLastName();

        if (!medicalRecords.contains(medicalRecord)) {
            logger.info("[INFO] Adding {}'s medical record to the list - {}", fullname, medicalRecord);
            return medicalRecords.add(medicalRecord);
        }
        logger.error("[ERROR] While adding {}'s medical record to the list\nMedical Record:\n{}", fullname, medicalRecord);
        throw new IllegalArgumentException("%s's medical record has already been saved!".formatted(fullname));
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord toUpdate) {

        String fullname = toUpdate.getFirstName() + " " + toUpdate.getLastName();

        for (MedicalRecord currentMD : medicalRecords) {
            String currentFullName = currentMD.getFirstName() + " " + currentMD.getLastName();
            if (currentFullName.equals(fullname)) {
                if (!currentMD.equals(toUpdate)) {
                    int index = medicalRecords.indexOf(currentMD);
                    logger.info("[INFO] Adding {}'s medical record to the list:\nOld MD: {}\nNew MD: {}", fullname, currentMD, toUpdate);
                    medicalRecords.set(index, toUpdate);
                    return medicalRecords.get(index);
                }
                throw new IllegalArgumentException("%s's medical record already up to date!".formatted(fullname));
            }
        }
        throw new NoSuchElementException("%s's medical record not found!".formatted(fullname));
    }

    @Override
    public MedicalRecord deleteMedicalRecord(MedicalRecord toDelete) {

        String fullname = toDelete.getFirstName() + " " + toDelete.getLastName();
        int index = 0;
        for (MedicalRecord md : medicalRecords) {
            String currentFullName = md.getFirstName() + " " + md.getLastName();
            if (currentFullName.equals(fullname)) {
                logger.info("[INFO] Deleting {}'s medical record from list", fullname);
                return medicalRecords.remove(index);
            }
            index++;
        }
        logger.error("[ERROR - DELETING] Medical Record not found: {}", toDelete);
        throw new NoSuchElementException("%s's medical record not found!".formatted(fullname));
    }
}
