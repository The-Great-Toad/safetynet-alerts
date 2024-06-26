package com.openclassrooms.safetynetalerts.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ResidentDto {

    private String lastName;

    private String phone;

    private int age;

    private List<String> medications;

    private List<String> allergies;
}
