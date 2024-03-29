package com.openclassrooms.safetynetalerts.controllers;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping(consumes = { "application/json" })
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @Operation(summary = "Create medical records")
    @PostMapping(consumes = { "application/json" }, produces = { "application/json" })
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord saveMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.saveMedicalRecord(md);
    }

    @Operation(summary = "Update medical records")
    @PutMapping(consumes = { "application/json" }, produces = { "application/json" })
    public MedicalRecord updateMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.updateMedicalRecord(md);
    }

    @Operation(summary = "Delete medical records")
    @DeleteMapping(consumes = { "application/json" }, produces = { "application/json" })
    public MedicalRecord deleteMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.deleteMedicalRecord(md);
    }
}
