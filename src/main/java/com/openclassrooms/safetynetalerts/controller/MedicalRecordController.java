package com.openclassrooms.safetynetalerts.controller;

import com.openclassrooms.safetynetalerts.models.MedicalRecord;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordServiceImpl medicalRecordService;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    @PostMapping
    public boolean saveMedicalRecord(@Valid @RequestBody MedicalRecord md) {
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
