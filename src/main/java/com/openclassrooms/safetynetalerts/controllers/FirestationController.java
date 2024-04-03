package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.dto.HomeDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentAndFirestationDto;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ApiResponse(responseCode = "200", description = "successful operation")
    @GetMapping(path = "firestation/all")
    public List<Firestation> getAllFirestation() {
        return firestationService.getAllFireStation();
    }

    @Operation(summary = "Create fire station")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful creation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Firestation.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "firestation", consumes = { "application/json" })
    public Boolean saveFirestation(@Valid @RequestBody Firestation firestation) {
        return firestationService.saveFireStation(firestation);
    }

    @Operation(summary = "Update fire station")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful update",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Firestation.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fire station not found", content = @Content),
    })
    @PutMapping(path = "firestation", consumes = { "application/json" }, produces = { "application/json" })
    public Firestation updateFirestation(@Valid @RequestBody Firestation toUpdate) {
        return firestationService.updateFireStation(toUpdate);
    }

    @Operation(summary = "Delete fire station")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Firestation.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fire station not found", content = @Content),
    })
    @DeleteMapping(path = "firestation", consumes = { "application/json" }, produces = { "application/json" })
    public Boolean deleteFirestation(@Valid @RequestBody Firestation toDelete) {
        return firestationService.deleteFireStation(toDelete);
    }

    /******************************************************************************************************************
                                                    URLs ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get residents' information attached to given integer station number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Station number not found", content = @Content),
    })
    @GetMapping(path = "firestation")
    public PersonsCoveredByFirestation getPersonCoveredByFirestation(
            @Parameter(description = "Station number", required = true) @NotNull @RequestParam int stationNumber) throws JsonProcessingException {

        if (stationNumber < 1 || stationNumber > 99) {
            throw new IllegalArgumentException("Station number invalid. Format expected: 2 digits between 1 and 99");
        }
        return firestationService.getPersonCoveredByFireStation(stationNumber);
    }

    @Operation(summary = "Get resident's information and their attached fire station number from given string address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Address not found", content = @Content),
    })
    @GetMapping("fire")
    public ResidentAndFirestationDto getResidentAndFirestationDto(
            @Parameter(description = "Address", required = true) @NotNull @RequestParam String address) {

        if (Strings.isBlank(address)) {
            throw new IllegalArgumentException("Address parameter required");
        }
        return firestationService.getResidentAndFireStationDto(address);
    }

    @Operation(summary = "Get list of residents grouped by address attached to given integer fire station number")
    @GetMapping(path = "flood")
    public HomeDto getHomeServedByStations(
            @Parameter(description = "Array of station number", required = true) @NotNull @RequestParam List<Integer> stations) {

        if (stations.isEmpty()) {
            throw new IllegalArgumentException("At least 1 station number required");
        }
        return firestationService.getHomeServedByStations(stations);
    }
}
