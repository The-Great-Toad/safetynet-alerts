package com.openclassrooms.safetynetalerts.controllers;

import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ChildDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ApiResponse(responseCode = "200", description = "successful operation")
    @GetMapping(path = "person", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @Operation(summary = "Create person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful creation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
    })
    @PostMapping(path = "person", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Person savePerson(@Valid @RequestBody Person p) {
        return personService.savePerson(p);
    }

    @Operation(summary = "Update person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful update",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
    })
    @PutMapping(path = "person", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Person updatePerson(@Valid @RequestBody Person p) {
        return personService.updatePerson(p);
    }

    @Operation(summary = "Delete person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
    })
    @DeleteMapping(path = "person", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Person deletePerson(@Valid @RequestBody Person p) {
        return personService.deletePerson(p);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get children living at given string address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Address not found", content = @Content),
    })
    @GetMapping(path = "childAlert", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<ChildDto> getChildrenByAddress(
            @Parameter(description = "Address", required = true) @NotNull @RequestParam String address) {

        if (Strings.isBlank(address)) {
            throw new IllegalArgumentException("Address parameter required");
        }
        return personService.getChildrenByAddress(address);
    }

    @Operation(summary = "Get residents' phones number attached to given integer station number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Station number not found", content = @Content),
    })
    @GetMapping(path = "phoneAlert", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<String> getPhonesByFireStationNumber(
            @Parameter(description = "Station number", required = true) @NotNull @RequestParam int firestation) {

        if (firestation < 1 || firestation > 99) {
            throw new IllegalArgumentException("Station number invalid. Format expected: 2 digits between 1 and 99");
        }
        return personService.getPhonesByFireStationNumber(firestation);
    }

    @Operation(summary = "Get person information from given string names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content),
    })
    @GetMapping(path = "personInfo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<PersonInfoDto> getPersonInfoByFirstAndLastName(
            @Parameter(description = "Person's first name", required = true) @NotNull @RequestParam String firstName,
            @Parameter(description = "Person's last name", required = true) @NotNull @RequestParam String lastName) {

        if (Strings.isBlank(firstName) && Strings.isBlank(lastName)) {
            throw new IllegalArgumentException("Firstname and LastName  parameter required");
        } else if (Strings.isBlank(firstName)) {
            throw new IllegalArgumentException("Firstname parameter required");
        } else if (Strings.isBlank(lastName)) {
            throw new IllegalArgumentException("LastName parameter required");
        }
        return personService.getPersonInfoByFirstAndLastName(firstName, lastName);
    }

    @Operation(summary = "Get residents' emails from given string city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content),
    })
    @GetMapping(path = "communityEmail", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<String> getResidentsEmailByCity(
            @Parameter(description = "City", required = true)@NotNull @RequestParam String city) {

        if (Strings.isBlank(city)) {
            throw new IllegalArgumentException("City parameter required");
        }
        return personService.getResidentsEmailByCity(city);
    }

}
