package com.microservices.appointment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.event.publisher.AppointmentEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*; // ✅ IMPORTANT
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // ✅ FIX
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class AppointmentFeignIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppointmentEventPublisher eventPublisher;

	@Test
	void createAppointment_shouldCallPatientServiceAndPersist() throws Exception {

		UUID patientId = UUID.randomUUID();

		// ✅ FIXED stub
		stubFor(
				get(urlEqualTo("/api/v1/patients/" + patientId))
						.willReturn(
								aResponse()
										.withHeader("Content-Type", "application/json")
										.withBody("""
                                        {
                                          "id":"%s",
                                          "firstName":"Tony",
                                          "lastName":"Stark",
                                          "email":"tony@avengers.com",
                                          "address":"New York",
                                          "dateOfBirth":"1970-05-29",
                                          "registeredDate":"2025-01-01"
                                        }
                                        """.formatted(patientId))
						)
		);

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