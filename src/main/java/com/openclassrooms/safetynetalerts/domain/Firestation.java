package com.openclassrooms.safetynetalerts.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Firestation {

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Station is required")
    @Digits(integer = 2, fraction = 0, message = "Station is invalid (2 digits max)")
    @Min(value = 1, message = "Station should be between 1 and 99")
//    @Max(value = 99, message = "Station should be between 1 and 99")
    private int station;
}
