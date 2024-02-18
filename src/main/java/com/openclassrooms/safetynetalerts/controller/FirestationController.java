package com.openclassrooms.safetynetalerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("firestation")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("all")
    public List<Firestation> getAllFirestation() {
        return firestationService.getAllFirestation();
    }

    @GetMapping
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(@RequestParam int stationNumber) throws JsonProcessingException {
        return firestationService.getPersonCoveredByFirestation(stationNumber);
    }

    @PostMapping
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationService.saveFiresation(firestation);
    }

    @PutMapping
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationService.updateFirestation(toUpdate);
    }

    @DeleteMapping
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationService.deleteFirestation(toDelete);
    }
}
