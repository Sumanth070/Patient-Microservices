package com.microservice.patient.controller;

import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.exception.PatientNotFoundException;
import com.microservice.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    void createPatient_shouldReturnCreatedPatient() throws Exception {

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("John");
        patientResponse.setLastName("Doe");
        patientResponse.setEmail("john@test.com");
        patientResponse.setDateOfBirth(LocalDate.of(1991,1,21));
        patientResponse.setRegisteredDate(LocalDate.now());
        patientResponse.setAddress("Newyork, esstate");

        when(patientService.createPatient(any()))
                .thenReturn(patientResponse);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "firstName": "John",
                        "lastName": "Doe",
                        "email": "john@test.com",
                        "address": "Newyork, esstate",
                        "dateOfBirth": "1991-01-21"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    @Test
    void createPatient_shouldReturnBadRequest_whenValidationFails() throws Exception {

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName": "",
                    "lastName": "Doe",
                    "email": "invalid-email",
                    "address": "New York",
                    "dateOfBirth": "1990-01-21"
                }
            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPatient_shouldCallService() throws Exception {

        PatientResponse response = new PatientResponse();
        response.setId(UUID.randomUUID());
        response.setFirstName("John");
        response.setEmail("john@test.com");

        when(patientService.createPatient(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "firstName":"John",
                    "lastName":"Doe",
                    "email":"john@test.com",
                    "address":"New York",
                    "dateOfBirth":"1990-01-21"
                }
            """))
                .andExpect(status().isCreated());

        verify(patientService).createPatient(any());
    }

    @Test
    void getPatientById_shouldReturnJsonStructure() throws Exception {

        UUID id = UUID.randomUUID();

        PatientResponse response = new PatientResponse();
        response.setId(id);
        response.setFirstName("John");
        response.setEmail("john@test.com");

        when(patientService.getPatientById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/patients/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }

    @Test
    void getPatientById_shouldReturn404_whenPatientDoesNotExist() throws Exception {

        UUID id = UUID.randomUUID();

        when(patientService.getPatientById(id))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        mockMvc.perform(get("/api/v1/patients/" + id))
                .andExpect(status().isNotFound());
    }
}
