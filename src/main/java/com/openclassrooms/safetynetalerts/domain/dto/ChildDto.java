package com.openclassrooms.safetynetalerts.domain.dto;

import com.openclassrooms.safetynetalerts.domain.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChildDto {

    private String firstName;

    private String lastName;

    private int age;

    private List<Person> relatives = new ArrayList<>();

    public ChildDto(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
