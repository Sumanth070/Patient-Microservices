package com.microservices.appointment.service;

import com.microservices.appointment.client.PatientClient;
import com.microservices.appointment.dto.*;
import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.entity.AppointmentStatus;
import com.microservices.appointment.event.model.AppointmentCreatedEvent;
import com.microservices.appointment.event.publisher.AppointmentEventPublisher;
import com.microservices.appointment.exception.PatientNotFoundException;
import com.microservices.appointment.mapper.AppointmentMapper;
import com.microservices.appointment.repository.AppointmentRepository;
import com.microservices.appointment.service.Impl.AppointmentServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private PatientClient patientClient;

    @Mock
    private AppointmentEventPublisher eventPublisher;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    // 🔥 1. SUCCESS TEST
    @Test
    void createAppointment_success_shouldSaveAndPublishEvent() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(patientId);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setDoctorName("Dr. Strange");
        appointment.setScheduledAt(LocalDateTime.now());

        AppointmentResponse response = new AppointmentResponse();

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("John");

        when(patientClient.getPatientById(patientId)).thenReturn(patientResponse);
        when(appointmentMapper.toEntity(request)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toResponse(appointment)).thenReturn(response);

        AppointmentResponse result = appointmentService.createAppointment(request);

        // ✅ verify DB save
        verify(appointmentRepository).save(appointment);

        // ✅ verify event published
        verify(eventPublisher).publishAppointmentCreatedEvent(any(AppointmentCreatedEvent.class));

        // ✅ verify response
        assertNotNull(result);
        assertEquals("John", result.getPatientName());

        // ✅ verify status set
        assertEquals(AppointmentStatus.BOOKED, appointment.getStatus());
    }

    // 🔥 2. PATIENT NOT FOUND TEST
    @Test
    void createAppointment_patientNotFound_shouldThrowException() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(patientId);

        when(patientClient.getPatientById(patientId))
                .thenThrow(mock(FeignException.NotFound.class));

        assertThrows(PatientNotFoundException.class,
                () -> appointmentService.createAppointment(request));

        // event should NOT be published
        verify(eventPublisher, never()).publishAppointmentCreatedEvent(any());
    }

    // 🔥 3. EVENT FAILURE TEST (VERY IMPORTANT)
    @Test
    void createAppointment_eventFails_shouldStillReturnResponse() {

        UUID patientId = UUID.randomUUID();

        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(patientId);

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setDoctorName("Dr. Strange");
        appointment.setScheduledAt(LocalDateTime.now());

        AppointmentResponse response = new AppointmentResponse();

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("John");

        when(patientClient.getPatientById(patientId)).thenReturn(patientResponse);
        when(appointmentMapper.toEntity(request)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toResponse(appointment)).thenReturn(response);

        // simulate SNS failure
        doThrow(new RuntimeException("SNS down"))
                .when(eventPublisher)
                .publishAppointmentCreatedEvent(any());

        AppointmentResponse result = appointmentService.createAppointment(request);

        // ✅ still returns response
        assertNotNull(result);
        assertEquals("John", result.getPatientName());

        // ✅ event was attempted
        verify(eventPublisher).publishAppointmentCreatedEvent(any());
    }
}