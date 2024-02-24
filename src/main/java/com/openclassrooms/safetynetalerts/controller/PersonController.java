package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;
import com.openclassrooms.safetynetalerts.models.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /******************************************************************************************************************
                                                    CRUD ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "person")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping(path = "person")
    public boolean savePerson(@Valid @RequestBody Person p) {
        return personService.savePerson(p);
    }

    @PutMapping(path = "person")
    public Person updatePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personService.updatePerson(p);
    }

    @DeleteMapping(path = "person")
    public Person deletePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personService.deletePerson(p);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "childAlert")
    public List<ChildDto> getChildrenByAddress(@RequestParam String address) {
        return personService.getChildrenByAddress(address);
    }

    @GetMapping(path = "phoneAlert")
    public List<String> getPhonesByFireStationNumber(@RequestParam int firestation) {
        return personService.getPhonesByFireStationNumber(firestation);
    }

    @GetMapping(path = "personInfo")
    public List<PersonInfoDto> getPersonInfoByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
//        if (Strings.isBlank(lastName)) {
//            return ResponseEntity.badRequest();
        // TODO: 24/02/2024 implements bad request responses for all endpoints
//        }
        return personService.getPersonInfoByFirstAndLastName(firstName, lastName);
    }

    @GetMapping(path = "communityEmail")
    public List<String> getResidentsEmailByCity(@RequestParam String city) {
        return personService.getResidentsEmailByCity(city);
    }

}
