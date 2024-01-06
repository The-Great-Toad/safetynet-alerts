package com.openclassrooms.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.Firestation;
import com.openclassrooms.safetynetalerts.repositories.DataObjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static Firestation firestation;

    @BeforeEach
    void setUp() {
        firestation = new Firestation("19 rue de la Tour", 3);
    }

    @Test
    void getAllFirestationTest() throws Exception {

        mockMvc.perform(get("/firestation"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")));
    }

    @Test
    void saveFirestationTest_success() throws Exception {

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void saveFirestationTest_MethodArgumentNotValidException() throws Exception {
        firestation.setAddress("");
        firestation.setStation(7);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.address", is("Address is required")))
                .andExpect(jsonPath("$.station", is("Station should be between 1 and 4")));
    }

    @Test
    void updateFirestationTest_success() throws Exception {
        saveFirestationTest_success();
        firestation.setStation(4);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station", is(4)));
    }

    @Disabled
    void updateFirestationTest_NoSuchElementException() throws Exception {
        firestation.setStation(4);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Address 19 rue de la Tour not found!")));

        // todo: clean context to prevent test failure via une méthode de Data Repo (fonction reset par exemple appelée dans un @Before or @After)
    }

    @Test
    void updateFirestationTest_IllegalArgumentException() throws Exception {
        firestation.setStation(4);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Station number provided already set for this address.")));
    }

    @Test
    void deleteFirestationTest_success() throws Exception {
        saveFirestationTest_success();

        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void deleteFirestationTest_NoSuchElementException() throws Exception {

        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Address 19 rue de la Tour not found!")));
    }
}