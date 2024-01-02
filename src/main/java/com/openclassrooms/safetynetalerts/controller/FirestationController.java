package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.services.FirestationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/firestation")
public class FirestationController {

    @Autowired
    private FirestationServiceImpl firestationServiceImpl;

    @GetMapping
    public List<Firestation> getAllFirestation() {
        return firestationServiceImpl.getAllFirestation();
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
