package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.dto.HomeDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentAndFirestationDto;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Fire Station", description = "the fire station API")
@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    /******************************************************************************************************************
                                                        CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get fire station")
    @GetMapping(path = "firestation/all")
    public List<Firestation> getAllFirestation() {
        return firestationService.getAllFireStation();
    }

    @Operation(summary = "Create fire station")
    @PostMapping(path = "firestation", consumes = { "application/json" })
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationService.saveFireStation(firestation);
    }

    @Operation(summary = "Update fire station")
    @PutMapping(path = "firestation", consumes = { "application/json" }, produces = { "application/json" })
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationService.updateFireStation(toUpdate);
    }

    @Operation(summary = "Delete fire station")
    @DeleteMapping(path = "firestation", consumes = { "application/json" }, produces = { "application/json" })
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationService.deleteFireStation(toDelete);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get residents' information attached to given integer station number")
    @GetMapping(path = "firestation")
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(
            @Parameter(description = "Station number", required = true) @RequestParam int stationNumber) throws JsonProcessingException {
        return firestationService.getPersonCoveredByFireStation(stationNumber);
    }

    @Operation(summary = "Get resident's information and their attached fire station number from given string address")
    @GetMapping("fire")
    public ResidentAndFirestationDto getResidentAndFirestationDto(
            @Parameter(description = "Address", required = true) @RequestParam String address) {
        return firestationService.getResidentAndFireStationDto(address);
    }

    @Operation(summary = "Get list of residents grouped by address attached to given integer fire station number")
    @GetMapping(path = "flood")
    public HomeDto getHomeServedByStations(
            @Parameter(description = "Array of station number", required = true) @RequestParam List<Integer> stations) {
        return firestationService.getHomeServedByStations(stations);
    }
}
