package com.openclassrooms.safetynetalerts.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "First name is required")
    private String lastName;

    @Past(message = "Date of birth must be a past date")
    private Date birthdate;

    private List<String> medications;

    private List<String> allergies;

}
