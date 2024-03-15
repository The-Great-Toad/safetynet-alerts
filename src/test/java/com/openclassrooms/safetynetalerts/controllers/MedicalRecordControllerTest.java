package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void getAllMedicalRecordsTest() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].birthdate", is("03/06/1984")))
                .andExpect(jsonPath("$[0].medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("$[0].allergies[0]", is("nillacilan")));
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