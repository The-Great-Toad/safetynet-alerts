package com.openclassrooms.safetynetalerts.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openclassrooms.safetynetalerts.configuration.CustomLocalDateConfig;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @JsonDeserialize(using = CustomLocalDateConfig.CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateConfig.LocalDateSerializer.class)
    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;

}
