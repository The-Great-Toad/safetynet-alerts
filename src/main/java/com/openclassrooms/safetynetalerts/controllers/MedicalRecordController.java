package com.openclassrooms.safetynetalerts.controllers;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Medical Record", description = "the medical record API")
@RestController
@RequestMapping("medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /******************************************************************************************************************
                                                        CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Operation(summary = "Get medical records")
    @ApiResponse(responseCode = "200", description = "successful operation")
    @GetMapping(produces = { "application/json" })
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @Operation(summary = "Create medical records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful creation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MedicalRecord.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = { "application/json" }, produces = { "application/json" })
    public MedicalRecord saveMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.saveMedicalRecord(md);
    }

    @Operation(summary = "Update medical records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful update",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MedicalRecord.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medical record not found", content = @Content),
    })
    @PutMapping(consumes = { "application/json" }, produces = { "application/json" })
    public MedicalRecord updateMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.updateMedicalRecord(md);
    }

    @Operation(summary = "Delete medical records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MedicalRecord.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid attributes supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medical record not found", content = @Content),
    })
    @DeleteMapping(consumes = { "application/json" }, produces = { "application/json" })
    public MedicalRecord deleteMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.deleteMedicalRecord(md);
    }
}
