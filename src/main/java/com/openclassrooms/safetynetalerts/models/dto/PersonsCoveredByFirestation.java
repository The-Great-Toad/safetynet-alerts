package com.openclassrooms.safetynetalerts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonsCoveredByFirestation {

    private List<PersonDto> persons;

    private int numberAdults;

    private int numberChildren;
}
