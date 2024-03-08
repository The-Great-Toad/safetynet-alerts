package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.dto.HomeDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentAndFirestationDto;
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
                                                        CRUD ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "firestation/all")
    public List<Firestation> getAllFirestation() {
        return firestationService.getAllFireStation();
    }

    @PostMapping(path = "firestation")
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationService.saveFireStation(firestation);
    }

    @PutMapping(path = "firestation")
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationService.updateFireStation(toUpdate);
    }

    @DeleteMapping(path = "firestation")
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationService.deleteFireStation(toDelete);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping(path = "firestation")
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(@RequestParam int stationNumber) throws JsonProcessingException {
        return firestationService.getPersonCoveredByFireStation(stationNumber);
    }

    @GetMapping("fire")
    public ResidentAndFirestationDto getResidentAndFirestationDto(@RequestParam String address) {
        return firestationService.getResidentAndFireStationDto(address);
    }

    @GetMapping(path = "flood")
    public HomeDto getHomeServedByStations(@RequestParam List<Integer> stations) {
        return firestationService.getHomeServedByStations(stations);
    }
}
