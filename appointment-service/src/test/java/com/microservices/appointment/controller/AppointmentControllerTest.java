package com.microservices.appointment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createAppointment_shouldReturn201_whenRequestIsValid() throws Exception {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request =
                new CreateAppointmentRequest(
                        patientId,
                        "Dr Strange",
                        LocalDateTime.of(2026,1,1,10,0)
                );

        AppointmentResponse response = new AppointmentResponse();

        when(appointmentService.createAppointment(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }


    @Test
    void createAppointment_shouldReturn400_whenValidationFails() throws Exception {

        CreateAppointmentRequest invalidRequest =
                new CreateAppointmentRequest(
                        null,           // patientId should be @NotNull
                        "",             // doctorName should be @NotBlank
                        null            // scheduledAt should be @NotNull
                );

        mockMvc.perform(
                        post("/api/v1/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void getAllAppointments_shouldReturn200() throws Exception {

        when(appointmentService.getAllAppointments())
                .thenReturn(List.of(new AppointmentResponse()));

        mockMvc.perform(
                        get("/api/v1/appointments")
                )
                .andExpect(status().isOk());
    }


    @Test
    void deleteAppointment_shouldReturn204() throws Exception {

        Long appointmentId = 10L;

        mockMvc.perform(delete("/api/v1/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isNoContent());
        verify(appointmentService).deleteAppointment(appointmentId);
    }
}
