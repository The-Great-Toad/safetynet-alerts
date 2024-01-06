package com.openclassrooms.safetynetalerts.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.DataObject;
import com.openclassrooms.safetynetalerts.repositories.firestation.FirestationRepositoryImpl;
import com.openclassrooms.safetynetalerts.repositories.medicalrecord.MedicalRecordRepositoryImpl;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepositoryImpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public class DataObjectRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataObjectRepository.class);

    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.filepath.data}")
    private String filePath;

    private static DataObject data = new DataObject();

    @PostConstruct
    private void initProjectData() {
        try {
            data = mapper.readValue(new File(filePath), new TypeReference<DataObject>() {});
            logger.info("[DataObjectRepository] - Data initialisation completed");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        resetRepositoriesList();
        logger.info("[DataObjectRepository] - All Repositories lists initialisation completed");
    }

    public static void resetRepositoriesList() {
        PersonRepositoryImpl.people = data.getPersons();
        FirestationRepositoryImpl.firestations = data.getFirestations();
        MedicalRecordRepositoryImpl.medicalRecords = data.getMedicalrecords();
    }
}
