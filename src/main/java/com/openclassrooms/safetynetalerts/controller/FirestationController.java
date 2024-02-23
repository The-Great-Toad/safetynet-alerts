package com.openclassrooms.safetynetalerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.models.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.models.dto.ResidentAndFirestationDto;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    /******************************************************************************************************************
                                                        CRUD ENPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "firestation/all")
    public List<Firestation> getAllFirestation() {
        return firestationService.getAllFirestation();
    }

    @PostMapping(path = "firestation")
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationService.saveFiresation(firestation);
    }

    @PutMapping(path = "firestation")
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationService.updateFirestation(toUpdate);
    }

    @DeleteMapping(path = "firestation")
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationService.deleteFirestation(toDelete);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "firestation")
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(@RequestParam int stationNumber) throws JsonProcessingException {
        return firestationService.getPersonCoveredByFirestation(stationNumber);
    }

    @GetMapping("fire")
    public ResidentAndFirestationDto getResidentAndFirestationDto(@RequestParam String address) {
        return firestationService.getResidentAndFirestationDto(address);
    }
}
