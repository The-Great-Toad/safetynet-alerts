package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.Person;
import com.openclassrooms.safetynetalerts.domain.dto.ChildDto;
import com.openclassrooms.safetynetalerts.domain.dto.PersonInfoDto;
import com.openclassrooms.safetynetalerts.services.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PersonService personService;
    private Person person;

    @BeforeEach
    void setUp() {
        person = createPerson();
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.address", is(person.getAddress())));
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
        Person updateTest = createPerson();
        updateTest.setFirstName("TEST");
        updateTest.setLastName("UPDATE");
        personService.savePerson(updateTest);
        updateTest.setEmail("changed-my-email@email.com");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateTest)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(updateTest.getEmail())));
    }

    @Test
    void updatePersonTest_personNotFoundException() throws Exception {
        person.setFirstName("NOT FOUND");
        person.setLastName("TEST");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("NOT FOUND TEST not found")));
    }

    @Test
    void deletePersonTest_success() throws Exception {
        Person deleteTest = createPerson();
        deleteTest.setFirstName("TEST");
        deleteTest.setLastName("DELETE");
        personService.savePerson(deleteTest);

        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteTest)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(deleteTest.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(deleteTest.getLastName())));
    }

    @Test
    void deletePersonTest_personNotFoundException() throws Exception {
        person.setFirstName("NOT FOUND");
        person.setLastName("TEST");

        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
//                .andDo(print()) // for debug
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("NOT FOUND TEST not found")));
    }

    /******************************************************************************************************************
                                                TEST URLs ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getChildrenByAddressTest() throws Exception {
        String address = "1509 Culver St";
        List<ChildDto> children = personService.getChildrenByAddress(address);

        mockMvc.perform(get("/childAlert")
                        .param("address",address)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(children.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(children.get(0).getLastName())))
                .andExpect(jsonPath("$[0].age", is(children.get(0).getAge())));
    }

    @Test
    void getPhonesByFireStationNumberTest() throws Exception {
        int stationNumber = 3;
        List<String> phones = personService.getPhonesByFireStationNumber(stationNumber);

        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", Integer.toString(stationNumber))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(phones.get(0))))
                .andExpect(jsonPath("$[1]", is(phones.get(1))));
    }

    @Test
    void getPersonInfoByFirstAndLastNameTest() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";
        List<PersonInfoDto> results = personService.getPersonInfoByFirstAndLastName(firstName, lastName);
        mockMvc.perform(get("/personInfo")
                        .param("firstName",firstName)
                        .param("lastName",lastName)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is(results.get(0).getLastName())))
                .andExpect(jsonPath("$[0].address", is(results.get(0).getAddress())))
                .andExpect(jsonPath("$[0].age", is(results.get(0).getAge())))
                .andExpect(jsonPath("$[0].email", is(results.get(0).getEmail())));
    }

    @Test
    void getResidentsEmailByCityTest() throws Exception {
        String city = "Culver";
        List<String> emails = personService.getResidentsEmailByCity(city);

        mockMvc.perform(get("/communityEmail")
                        .param("city",city)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()) // for debug
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(emails.get(0))))
                .andExpect(jsonPath("$[1]", is(emails.get(1))));
    }
}