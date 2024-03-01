package com.openclassrooms.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
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

    // TODO: 24/02/2024 Test all endpoints

    /******************************************************************************************************************
                                                        TESTS CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getAllFirestationTest() throws Exception {

        mockMvc.perform(get("/firestation/all"))
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

    @Test
    void updateFirestationTest_NoSuchElementException() throws Exception {
        Firestation firestation1 = new Firestation("UPDATE", 2);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation1)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Address UPDATE not found!")));

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

    /******************************************************************************************************************
                                                            TESTS URLs ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getPersonCoveredByFirestationTest() throws Exception {

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "2"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons[0].firstName", is("Jonanathan")))
                .andExpect(jsonPath("$.persons[0].lastName", is("Marrack")))
                .andExpect(jsonPath("$.persons[0].address", is("29 15th St")))
                .andExpect(jsonPath("$.persons[0].phone", is("841-874-6513")))
                .andExpect(jsonPath("$.numberAdults", is(4)))
                .andExpect(jsonPath("$.numberChildren", is(1)));
    }

    @Test
    void getResidentAndFirestationDtoTest() throws Exception {
        String address = "1509 Culver St";
        List<String> medications = List.of("aznol:350mg","hydrapermazol:100mg");
        List<String> allergies = List.of("nillacilan");

        mockMvc.perform(get("/fire")
                        .param("address", address)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$.residents[0].phone", is("841-874-6512")))
                .andExpect(jsonPath("$.residents[0].age", is(40)))
                .andExpect(jsonPath("$.residents[0].medications", is(medications)))
                .andExpect(jsonPath("$.residents[0].allergies", is(allergies)));
    }

    @Test
    void getHomeServedByStationsTest() throws Exception {

        mockMvc.perform(get("/flood")
                        .param("stations", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homes['29 15th St'][0].lastName", is("Marrack")))
                .andExpect(jsonPath("$.homes['29 15th St'][0].phone", is("841-874-6513")))
                .andExpect(jsonPath("$.homes['29 15th St'][0].age", is(35)))
                .andExpect(jsonPath("$.homes['29 15th St'][0].medications").isArray())
                .andExpect(jsonPath("$.homes['29 15th St'][0].medications", hasSize(0)))
                .andExpect(jsonPath("$.homes['29 15th St'][0].allergies").isArray())
                .andExpect(jsonPath("$.homes['29 15th St'][0].allergies", hasSize(0)));
    }
}