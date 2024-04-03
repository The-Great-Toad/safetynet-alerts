package com.openclassrooms.safetynetalerts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetalerts.TestUtils;
import com.openclassrooms.safetynetalerts.domain.Firestation;
import com.openclassrooms.safetynetalerts.domain.dto.PersonsCoveredByFirestation;
import com.openclassrooms.safetynetalerts.domain.dto.ResidentAndFirestationDto;
import com.openclassrooms.safetynetalerts.services.firestation.FirestationService;
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
class FirestationControllerTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FirestationService firestationService;

    private static Firestation firestation;

    @BeforeEach
    void setUp() {
        firestation = createFireStation();
    }

    /******************************************************************************************************************
                                                        TESTS CRUD ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getAllFirestationTest() throws Exception {

        List<Firestation> firestations = firestationService.getAllFireStation();

        mockMvc.perform(get("/firestation/all"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is(firestations.get(0).getAddress())))
                .andExpect(jsonPath("$[0].station", is(firestations.get(0).getStation())));
    }

    @Test
    void saveFirestationTest_success() throws Exception {

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void saveFirestationTest_MethodArgumentNotValidException() throws Exception {
        Firestation failedSaveTest = new Firestation();

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(failedSaveTest)))
//                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.address", is("Address is required")))
                .andExpect(jsonPath("$.station", is("Station should be between 1 and 99")));
    }

    @Test
    void updateFirestationTest_success() throws Exception {
        Firestation updateTest = Firestation.builder()
                .address("TO UPDATE")
                .station(5)
                .build();
        firestationService.saveFireStation(updateTest);
        updateTest.setStation(6);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateTest)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is(updateTest.getAddress())))
                .andExpect(jsonPath("$.station", is(updateTest.getStation())));
    }

    @Test
    void updateFirestationTest_NoSuchElementException() throws Exception {
        Firestation failedTest = new Firestation("FAILED UPDATE", 2);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(failedTest)))
//                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Address FAILED UPDATE not found")));
    }

    @Test
    void updateFirestationTest_IllegalArgumentException() throws Exception {
        firestation.setStation(100);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Station is invalid (2 digits max)")));
    }

    @Test
    void deleteFirestationTest_success() throws Exception {
        Firestation toDelete = Firestation.builder()
                .address("TO DELETE")
                .station(5)
                .build();
        firestationService.saveFireStation(toDelete);

        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(toDelete)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void deleteFirestationTest_NoSuchElementException() throws Exception {
        firestation.setAddress("FAILED DELETE");

        mockMvc.perform(delete("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(firestation)))
//                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Address FAILED DELETE not found")));
    }

    /******************************************************************************************************************
                                                            TESTS URLs ENDPOINTS
     ******************************************************************************************************************/

    @Test
    void getPersonCoveredByFirestationTest() throws Exception {
        int stationNumber = 2;
        PersonsCoveredByFirestation expected = firestationService.getPersonCoveredByFireStation(stationNumber);

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", String.valueOf(stationNumber)))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons[0].firstName", is(expected.getPersons().get(0).getFirstName())))
                .andExpect(jsonPath("$.persons[0].lastName", is(expected.getPersons().get(0).getLastName())))
                .andExpect(jsonPath("$.persons[0].address", is(expected.getPersons().get(0).getAddress())))
                .andExpect(jsonPath("$.persons[0].phone", is(expected.getPersons().get(0).getPhone())))
                .andExpect(jsonPath("$.numberAdults", is(expected.getNumberAdults())))
                .andExpect(jsonPath("$.numberChildren", is(expected.getNumberChildren())));
    }

    @Test
    void getResidentAndFirestationDtoTest() throws Exception {
        String address = "1509 Culver St";
        ResidentAndFirestationDto expected = firestationService.getResidentAndFireStationDto(address);

        mockMvc.perform(get("/fire")
                        .param("address", address)
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents[0].lastName", is(expected.getResidents().get(0).getLastName())))
                .andExpect(jsonPath("$.residents[0].phone", is(expected.getResidents().get(0).getPhone())))
                .andExpect(jsonPath("$.residents[0].age", is(expected.getResidents().get(0).getAge())))
                .andExpect(jsonPath("$.residents[0].medications", is(expected.getResidents().get(0).getMedications())))
                .andExpect(jsonPath("$.residents[0].allergies", is(expected.getResidents().get(0).getAllergies())));
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