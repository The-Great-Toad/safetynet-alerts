package com.openclassrooms.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Medication {

    private String name;
    private String dosage;
}
