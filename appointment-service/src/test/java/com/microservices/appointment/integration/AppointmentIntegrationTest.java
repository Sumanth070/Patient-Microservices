package com.microservices.appointment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.appointment.client.PatientClient;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.dto.PatientResponse;
import com.microservices.appointment.event.publisher.AppointmentEventPublisher;
import com.microservices.appointment.mapper.AppointmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppointmentIntegrationTest {

    @MockBean
    private AppointmentEventPublisher eventPublisher; // ✅ FIX

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @MockBean
    private PatientClient patientClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createAppointment_shouldPersistAppointment() throws Exception {

        UUID patientId = UUID.randomUUID();

        PatientResponse patient = new PatientResponse();
        patient.setId(patientId);
        patient.setFirstName("Tony");
        patient.setLastName("Stark");
        patient.setEmail("tony@avengers.com");
        patient.setAddress("New York");
        patient.setDateOfBirth(LocalDate.of(1970,5,29));
        patient.setRegisteredDate(LocalDate.now());

        when(patientClient.getPatientById(patientId)).thenReturn(patient);

        CreateAppointmentRequest request =
                new CreateAppointmentRequest(
                        patientId,
                        "Dr Strange",
                        LocalDateTime.of(2026,1,1,10,0)
                );

        mockMvc.perform(
                        post("/api/v1/appointments")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }
}