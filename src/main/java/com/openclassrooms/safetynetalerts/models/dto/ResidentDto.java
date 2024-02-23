package com.openclassrooms.safetynetalerts.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidentDto {

    private String lastName;

    private String phone;

    private int age;

    private List<String> medications;

    private List<String> allergies;
}
