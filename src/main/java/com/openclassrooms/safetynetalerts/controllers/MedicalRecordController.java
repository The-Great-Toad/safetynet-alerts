package com.openclassrooms.safetynetalerts.controllers;

import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordServiceImpl medicalRecordService;

    /******************************************************************************************************************
                                                        CRUD ENDPOINTS
     ******************************************************************************************************************/

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord saveMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.saveMedicalRecord(md);
    }

    @PutMapping
    public MedicalRecord updateMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.updateMedicalRecord(md);
    }

    @DeleteMapping
    public MedicalRecord deleteMedicalRecord(@Valid @RequestBody MedicalRecord md) {
        return medicalRecordService.deleteMedicalRecord(md);
    }
}
