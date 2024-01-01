package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.exceptions.PersonNotFoundException;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.services.PersonServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServiceImpl personServiceImpl;

    @GetMapping
    public List<Person> getAllPerson() {
        return personServiceImpl.getAllPerson();
    }

    @PostMapping
    public Person savePerson(@Valid @RequestBody Person p) {
        return personServiceImpl.savePerson(p);
    }

    @PutMapping
    public Person updatePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personServiceImpl.updatePerson(p);
    }

    @DeleteMapping
    public Person deletePerson(@Valid @RequestBody Person p) throws PersonNotFoundException {
        return personServiceImpl.deletePerson(p);
    }

}
