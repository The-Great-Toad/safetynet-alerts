package com.openclassrooms.safetynetalerts.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Firestation {

    private String address;
    private String station;
}
