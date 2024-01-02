package com.openclassrooms.safetynetalerts.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Firestation {

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Station is required")
    @Digits(integer = 1, fraction = 0, message = "Station is invalid")
    @Min(value = 1, message = "Station should be between 1 and 4")
    @Max(value = 4, message = "Station should be between 1 and 4")
    private int station;
}
