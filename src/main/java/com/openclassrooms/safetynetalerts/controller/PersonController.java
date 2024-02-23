package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.models.dto.ChildDto;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

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

    @GetMapping(path = "childAlert")
    public List<ChildDto> getChildrenByAdress(@RequestParam String address) {
        return personService.getChildrenByAdress(address);
    }

    @GetMapping(path = "phoneAlert")
    public List<String> getPhonesByFirestationNumber(@RequestParam int stationNumber) {
        return personService.getPhonesByFirestationNumber(stationNumber);
    }

}
