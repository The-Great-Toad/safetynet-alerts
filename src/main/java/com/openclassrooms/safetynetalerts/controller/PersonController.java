package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.model.Person;
import com.openclassrooms.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "/person")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping("/person")
    public Person savePerson(@RequestBody Person p) throws Exception {
        return personService.savePerson(p);
    }

    @ExceptionHandler({Exception.class})
    public String handleExcpetion() {
        return "invalid information !";
    }

}
