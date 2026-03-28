package com.microservices.appointment.service;

import com.microservices.appointment.client.PatientClient;
import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.dto.PatientResponse;
import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.mapper.AppointmentMapper;
import com.microservices.appointment.repository.AppointmentRepository;
import com.microservices.appointment.service.Impl.AppointmentServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private PatientClient patientClient;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void createAppointment_shouldCreateAppointment_whenPatientExists() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request =
                new CreateAppointmentRequest(
                        patientId,
                        "Dr Strange",
                        LocalDateTime.now()
                );

        PatientResponse patient = new PatientResponse();
        patient.setId(patientId);
        patient.setFirstName("Tony");
        patient.setLastName("Stark");
        patient.setEmail("tony@avengers.com");
        patient.setAddress("New York");
        patient.setDateOfBirth(LocalDate.of(1970, 5, 29));
        patient.setRegisteredDate(LocalDate.now());

        Appointment entity = new Appointment();
        Appointment savedEntity = new Appointment();

        AppointmentResponse response = new AppointmentResponse();

        when(patientClient.getPatientById(patientId)).thenReturn(patient);
        when(appointmentMapper.toEntity(request)).thenReturn(entity);
        when(appointmentRepository.save(entity)).thenReturn(savedEntity);
        when(appointmentMapper.toResponse(savedEntity)).thenReturn(response);

        AppointmentResponse result = appointmentService.createAppointment(request);

        assertNotNull(result);
        assertEquals("Tony", result.getPatientName());

        verify(patientClient).getPatientById(patientId);
        verify(appointmentRepository).save(entity);
        verify(appointmentMapper).toEntity(request);
        verify(appointmentMapper).toResponse(savedEntity);
    }

    @Test
    void createAppointment_shouldThrowException_whenPatientNotFound() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request =
                new CreateAppointmentRequest(
                        patientId,
                        "Dr Strange",
                        LocalDateTime.now()
                );

        when(patientClient.getPatientById(patientId))
                .thenThrow(FeignException.NotFound.class);

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> appointmentService.createAppointment(request));

        assertTrue(exception.getMessage().contains("Patient not found"));
    }

    @Test
    void createAppointment_shouldPropagateException_whenRepositoryFails() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request =
                new CreateAppointmentRequest(
                        patientId,
                        "Dr Strange",
                        LocalDateTime.now()
                );

        PatientResponse patient = new PatientResponse();
        patient.setFirstName("Bruce");

        Appointment entity = new Appointment();

        when(patientClient.getPatientById(patientId)).thenReturn(patient);
        when(appointmentMapper.toEntity(request)).thenReturn(entity);
        when(appointmentRepository.save(entity))
                .thenThrow(new RuntimeException("Database failure"));

        assertThrows(RuntimeException.class,
                () -> appointmentService.createAppointment(request));
    }

    @Test
    void getAllAppointments_shouldReturnAppointmentsWithPatientNames() {

        UUID patientId = UUID.randomUUID();

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);

        AppointmentResponse response = new AppointmentResponse();

        PatientResponse patient = new PatientResponse();
        patient.setFirstName("Peter");

        when(appointmentRepository.findAll())
                .thenReturn(List.of(appointment));

        when(appointmentMapper.toResponse(appointment))
                .thenReturn(response);

        when(patientClient.getPatientById(patientId))
                .thenReturn(patient);

        List<AppointmentResponse> results =
                appointmentService.getAllAppointments();

        assertEquals(1, results.size());
        assertEquals("Peter", results.get(0).getPatientName());

        verify(appointmentRepository).findAll();
        verify(patientClient).getPatientById(patientId);
    }

    @Test
    void getAllAppointments_shouldReturnEmptyList_whenNoAppointmentsExist() {

        when(appointmentRepository.findAll()).thenReturn(List.of());

        List<AppointmentResponse> results =
                appointmentService.getAllAppointments();

        assertTrue(results.isEmpty());
    }

    @Test
    void deleteAppointment_shouldCallRepositoryDelete() {

        Long appointmentId = 10L;

        when(appointmentRepository.existsById(appointmentId))
                .thenReturn(true);

        appointmentService.deleteAppointment(appointmentId);

        verify(appointmentRepository).deleteById(appointmentId);
    }



}
