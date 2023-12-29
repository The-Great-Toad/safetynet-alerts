package com.openclassrooms.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<Medication> medications;
    private List<String> allergies;

}
