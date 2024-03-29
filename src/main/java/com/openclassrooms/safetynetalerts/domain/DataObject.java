package com.openclassrooms.safetynetalerts.domain;

import lombok.Data;

import java.util.List;

@Data
public class DataObject {

    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

}
