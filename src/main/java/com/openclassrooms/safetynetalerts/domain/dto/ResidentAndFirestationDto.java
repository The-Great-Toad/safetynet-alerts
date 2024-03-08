package com.openclassrooms.safetynetalerts.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentAndFirestationDto {

    private List<ResidentDto> residents;

    private int fireStationNumber;
}
