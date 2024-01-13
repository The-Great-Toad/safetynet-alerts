package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.Person;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("firestation")
public class FirestationController {

    @Autowired
    private FirestationServiceImpl firestationServiceImpl;

    @GetMapping("all")
    public List<Firestation> getAllFirestation() {
        return firestationServiceImpl.getAllFirestation();
    }

    @GetMapping
    public List<Person> getPersonCoveredByFirestation(@RequestParam int stationNumber) {
        return firestationServiceImpl.getPersonCoveredByFirestation(stationNumber);
    }

    @PostMapping
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationServiceImpl.saveFiresation(firestation);
    }

    @PutMapping
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationServiceImpl.updateFirestation(toUpdate);
    }

    @DeleteMapping
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationServiceImpl.deleteFirestation(toDelete);
    }
}
