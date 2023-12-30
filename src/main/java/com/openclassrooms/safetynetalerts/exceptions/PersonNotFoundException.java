package com.openclassrooms.safetynetalerts.exceptions;

import lombok.Data;

@Data
public class PersonNotFoundException extends Throwable {


    private final String type;
    private final String message;

    public PersonNotFoundException(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public PersonNotFoundException(String message) {
        this("person", message);
    }


}
