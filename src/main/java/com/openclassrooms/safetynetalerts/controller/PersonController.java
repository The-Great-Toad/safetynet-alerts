package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping
    public Person savePerson(@Valid @RequestBody Person p) {
        return personService.savePerson(p);
    }

    @PutMapping
    public Person updatePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personService.updatePerson(p);
    }

    @DeleteMapping
    public Person deletePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personService.deletePerson(p);
    }

}
