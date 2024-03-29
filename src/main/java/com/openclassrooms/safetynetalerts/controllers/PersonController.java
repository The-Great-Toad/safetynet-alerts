package com.openclassrooms.safetynetalerts.controllers;

import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ChildDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Person", description = "the person API")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /******************************************************************************************************************
                                                    CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get person")
    @GetMapping(path = "person")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @Operation(summary = "Create person")
    @PostMapping(path = "person", consumes = { "application/json" })
    public boolean savePerson(@Valid @RequestBody Person p) {
        return personService.savePerson(p);
    }

    @Operation(summary = "Update person")
    @PutMapping(path = "person", consumes = { "application/json" }, produces = { "application/json" })
    public Person updatePerson(@Valid @RequestBody Person p) {
        return personService.updatePerson(p);
    }

    @Operation(summary = "Delete person")
    @DeleteMapping(path = "person", consumes = { "application/json" }, produces = { "application/json" })
    public Person deletePerson(@Valid @RequestBody Person p) {
        return personService.deletePerson(p);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get children living at given string address")
    @GetMapping(path = "childAlert", produces = { "application/json" })
    public List<ChildDto> getChildrenByAddress(
            @Parameter(description = "Address", required = true) @RequestParam String address) {
        return personService.getChildrenByAddress(address);
    }

    @Operation(summary = "Get residents' phones number attached to given integer station number")
    @GetMapping(path = "phoneAlert", produces = { "application/json" })
    public List<String> getPhonesByFireStationNumber(
            @Parameter(description = "Station number", required = true) @RequestParam int stationNumber) {
        return personService.getPhonesByFireStationNumber(stationNumber);
    }

    @Operation(summary = "Get person information from given string names")
    @GetMapping(path = "personInfo", produces = { "application/json" })
    public List<PersonInfoDto> getPersonInfoByFirstAndLastName(
            @Parameter(description = "Person's first name", required = true) @RequestParam String firstName,
            @Parameter(description = "Person's last name", required = true) @RequestParam String lastName) {
//        if (Strings.isBlank(lastName)) {
//            return ResponseEntity.badRequest();
        // TODO: 24/02/2024 implements bad request responses for all endpoints
//        }
        return personService.getPersonInfoByFirstAndLastName(firstName, lastName);
    }

    @Operation(summary = "Get residents' emails from given string city")
    @GetMapping(path = "communityEmail", produces = { "application/json" })
    public List<String> getResidentsEmailByCity(
            @Parameter(description = "City", required = true) @RequestParam String city) {
        return personService.getResidentsEmailByCity(city);
    }

}
