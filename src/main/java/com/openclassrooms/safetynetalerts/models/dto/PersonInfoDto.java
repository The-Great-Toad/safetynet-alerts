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
public class PersonInfoDto {

    private String lastName;

    private String address;

    private int age;

    private String email;

    private List<String> medications;

    private List<String> allergies;


}
