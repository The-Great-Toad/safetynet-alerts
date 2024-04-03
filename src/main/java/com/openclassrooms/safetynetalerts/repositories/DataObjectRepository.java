package com.openclassrooms.safetynetalerts.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.DataObject;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DataObjectRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataObjectRepository.class);
    private final String LOG_ID = "[DataObjectRepository]";


    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.filepath.data}")
    private String filePath;

    private List<Person> people;
    private List<Firestation> firestations;

    private List<MedicalRecord> medicalRecords;

    public List<Person> getPeople() {
        return people;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    @PostConstruct
    private void initProjectData() {
        mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
        DataObject data;
        try {
            data = mapper.readValue(new File(filePath), new TypeReference<>() {});
            logger.debug("{} - Data initialisation completed", LOG_ID);
        } catch (IOException e) {
            logger.error("{} - ERROR: {}", LOG_ID, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        people = data.getPersons();
        medicalRecords = data.getMedicalrecords();
        firestations = data.getFirestations();

        logger.debug("{} - Repositories lists initialisation completed", LOG_ID);
    }
}
