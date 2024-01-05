package com.openclassrooms.safetynetalerts.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Medication {

    @NotEmpty(message = "The medication's name is required")
    private String name;

    @NotEmpty(message = "The medication's dosage is required")
    private String dosage;
}
