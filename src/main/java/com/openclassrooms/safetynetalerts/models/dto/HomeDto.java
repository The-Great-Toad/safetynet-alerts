package com.openclassrooms.safetynetalerts.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeDto {

    private Map<String, List<ResidentDto>> homes;

}
