package com.openclassrooms.safetynetalerts.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.DataObject;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.repositories.person.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class DataObjectRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataObjectRepository.class);

    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.filepath.data}")
    private String filePath;

    @Autowired
    private PersonRepository personRepository;

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
        mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        DataObject data;
        try {
            data = mapper.readValue(new File(filePath), new TypeReference<>() {});
            logger.debug("[DataObjectRepository] - Data initialisation completed");
        } catch (IOException e) {
            logger.error("[DataObjectRepository] - ERROR: {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        people = data.getPersons();
        medicalRecords = data.getMedicalrecords();
        firestations = data.getFirestations();

        logger.debug("[DataObjectRepository] - All Repositories lists initialisation completed");
    }
}
