package com.openclassrooms.safetynetalerts.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecord {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "First name is required")
    private String lastName;

    @Past(message = "Date of birth must be a past date")
    @JsonFormat(pattern = "DD/MM/YYYY")
    private Date birthdate;

//    @Valid
//    private List<@Valid Medication> medications;
    private List<String> medications;

    private List<String> allergies;

}
