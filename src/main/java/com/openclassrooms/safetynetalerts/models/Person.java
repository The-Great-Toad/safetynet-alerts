package com.openclassrooms.safetynetalerts.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @NotBlank(message = "First name is required")
//    @Pattern(regexp = "^[a-zA-Z]$")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String address;

    private String city;

    @Digits(integer = 4, fraction = 0, message = "Zip is invalid")
    private String zip;

    @Digits(integer = 12, fraction = 0, message = "Phone is invalid")
    private String phone;

    @Email(message = "Email is invalid")
    private String email;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
