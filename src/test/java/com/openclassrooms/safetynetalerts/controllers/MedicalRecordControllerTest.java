package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import com.openclassrooms.safetynetalerts.services.medicalrecord.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordControllerTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    void getAllMedicalRecordsTest() throws Exception {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        mockMvc.perform(get("/medicalRecord"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(medicalRecords.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(medicalRecords.get(0).getLastName())))
                .andExpect(jsonPath("$[0].birthdate", is(medicalRecords.get(0).getBirthdate().format(formatter))))
                .andExpect(jsonPath("$[0].medications", is(medicalRecords.get(0).getMedications())))
                .andExpect(jsonPath("$[0].allergies", is(medicalRecords.get(0).getAllergies())));
    }

    @Test
    void saveMedicalRecordTest() throws Exception {
        MedicalRecord medicalRecord = createMedicalRecord();

        mockMvc.perform(post("/medicalRecord")
                        .content(mapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Bob")))
                .andExpect(jsonPath("$.lastName", is("Sponge")))
                .andExpect(jsonPath("$.birthdate", is("03/06/1984")))
                .andExpect(jsonPath("$.medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("$.allergies[0]", is("nillacilan")));
    }

    @Test
    void updateMedicalRecordTest() throws Exception {
        MedicalRecord medicalRecord = createMedicalRecord();
        saveMedicalRecordTest();
        medicalRecord.setMedications(List.of("trileptal: 600mg"));

        mockMvc.perform(put("/medicalRecord")
                        .content(mapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Bob")))
                .andExpect(jsonPath("$.lastName", is("Sponge")))
                .andExpect(jsonPath("$.birthdate", is("03/06/1984")))
                .andExpect(jsonPath("$.medications[0]", is("trileptal: 600mg")))
                .andExpect(jsonPath("$.allergies[0]", is("nillacilan")));

    }

    @Test
    void deleteMedicalRecordTest() throws Exception {
        MedicalRecord medicalRecord = createMedicalRecord();

        mockMvc.perform(delete("/medicalRecord")
                        .content(mapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Bob")))
                .andExpect(jsonPath("$.lastName", is("Sponge")))
                .andExpect(jsonPath("$.birthdate", is("03/06/1984")))
                .andExpect(jsonPath("$.medications[0]", is("trileptal: 600mg")))
                .andExpect(jsonPath("$.allergies[0]", is("nillacilan")));
    }
}