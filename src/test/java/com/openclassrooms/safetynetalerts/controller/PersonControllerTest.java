package com.openclassrooms.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.models.Person;
import org.junit.jupiter.api.BeforeAll;
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
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
        person.setFirstName("Erick");
        person.setLastName("Pattisson");
        person.setEmail("erick.patt@gmail.com");
    }

    @Test
    void getAllPersonTest() throws Exception {
        mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void savePersonTest_success() throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Erick")))
                .andExpect(jsonPath("$.lastName", is("Pattisson")))
                .andExpect(jsonPath("$.email", is("erick.patt@gmail.com")));
    }

    @Test
    void savePersonTest_badRequest() throws Exception {
        Person newPerson = new Person();

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newPerson)))
                .andDo(print())
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("erick.patt@outlook.com")));
    }

    @Test
    void updatePersonTest_personNotFoundException() throws Exception {
        person.setEmail("erick.patt@outlook.com");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Person with name Erick Pattisson not found!")));
    }

    @Test
    void deletePersonTest_success() throws Exception {
        savePersonTest_success();

        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Erick")))
                .andExpect(jsonPath("$.lastName", is("Pattisson")));
    }

    @Test
    void deletePersonTest_personNotFoundException() throws Exception {
        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Person with name Erick Pattisson not found!")));
    }
}