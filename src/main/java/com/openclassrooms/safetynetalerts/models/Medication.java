package com.openclassrooms.safetynetalerts.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Medication {

    private String name;
    private String dosage;
}
