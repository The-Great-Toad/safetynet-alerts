package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private static Person person;

    @BeforeEach
    void setUp() {
        person = new Person("Erick", "Pattisson");
        person.setEmail("erick.patt@gmail.com");
    }

    /******************************************************************************************************************
                                                TEST CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getAllPersonTest() throws Exception {
        mockMvc.perform(get("/person"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void savePersonTest_success() throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void savePersonTest_badRequest() throws Exception {
        Person newPerson = new Person();

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newPerson)))
//                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName", is("First name is required")))
                .andExpect(jsonPath("$.lastName", is("Last name is required")));
    }

    @Test
    void updatePersonTest_success() throws Exception {
        savePersonTest_success();
        person.setEmail("erick.patt@outlook.com");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("erick.patt@outlook.com")));
    }

    @Test
    void updatePersonTest_personNotFoundException() throws Exception {
        person.setLastName("Smith");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Erick Smith not found!")));
    }

    @Test
    void deletePersonTest_success() throws Exception {
        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Erick")))
                .andExpect(jsonPath("$.lastName", is("Pattisson")));
    }

    @Test
    void deletePersonTest_personNotFoundException() throws Exception {
        person.setLastName("Smith");

        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Erick Smith not found!")));
    }

    /******************************************************************************************************************
                                                TEST URLs ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getChildrenByAddressTest() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address","1509 Culver St")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Tenley")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].age", is(12)));
    }

    @Test
    void getPhonesByFireStationNumberTest() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("stationNumber","3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("841-874-6512")))
                .andExpect(jsonPath("$[1]", is("841-874-6513")));
    }

    @Test
    void getPersonInfoByFirstAndLastNameTest() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("firstName","John")
                        .param("lastName","Boyd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].age", is(40)))
                .andExpect(jsonPath("$[0].email", is("jaboyd@email.com")));
    }

    @Test
    void getResidentsEmailByCityTest() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city","Culver")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("jaboyd@email.com")))
                .andExpect(jsonPath("$[1]", is("drk@email.com")));
    }
}