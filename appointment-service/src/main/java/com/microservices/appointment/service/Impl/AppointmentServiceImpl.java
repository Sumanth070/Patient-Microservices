package com.microservices.appointment.service.Impl;

import com.microservices.appointment.client.PatientClient;
import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.dto.PatientResponse;
import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.entity.AppointmentStatus;
import com.microservices.appointment.event.model.AppointmentCreatedEvent;
import com.microservices.appointment.event.model.EventType;
import com.microservices.appointment.event.publisher.AppointmentEventPublisher;
import com.microservices.appointment.exception.AppointmentNotFoundException;
import com.microservices.appointment.exception.PatientNotFoundException;
import com.microservices.appointment.exception.PatientServiceUnavailableException;
import com.microservices.appointment.mapper.AppointmentMapper;
import com.microservices.appointment.repository.AppointmentRepository;
import com.microservices.appointment.service.AppointmentService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientClient patientClient;
    private final AppointmentEventPublisher eventPublisher;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  AppointmentMapper appointmentMapper,
                                  PatientClient patientClient,
                                  AppointmentEventPublisher eventPublisher) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.patientClient = patientClient;
        this.eventPublisher = eventPublisher;
    }

    private PatientResponse validatePatient(UUID id) {
        try {
            return patientClient.getPatientById(id);
        } catch (FeignException.NotFound ex) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        } catch (FeignException ex) {
            throw new PatientServiceUnavailableException("Patient service unavailable");
        }
    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {

        // 1. Validate patient
        PatientResponse patientDetail = validatePatient(request.getPatientId());

        // 2. Map and save
        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setStatus(AppointmentStatus.BOOKED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 3. Map response
        AppointmentResponse response = appointmentMapper.toResponse(savedAppointment);
        response.setPatientName(patientDetail.getFirstName());

        // 4. Build event (IMPORTANT: use savedAppointment)
        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                UUID.randomUUID().toString(),
                EventType.APPOINTMENT_CREATED,
                "v1",
                LocalDateTime.now(),
                "appointment-service",
                new AppointmentCreatedEvent.DataPayload(
                        savedAppointment.getAppointmentId().toString(),
                        patientDetail.getFirstName(),
                        savedAppointment.getDoctorName(),
                        savedAppointment.getScheduledAt()
                )
        );

        // 5. Publish event
        try {
            eventPublisher.publishAppointmentCreatedEvent(event);
            log.info("Published AppointmentCreatedEvent with id={}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to publish event for appointmentId={}", savedAppointment.getAppointmentId(), e);
            // NOTE: Do NOT throw (for now) → avoids breaking API
        }

        return response;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> {

                    AppointmentResponse response = appointmentMapper.toResponse(appointment);

                    try {
                        PatientResponse patientDetail =
                                patientClient.getPatientById(appointment.getPatientId());

                        response.setPatientName(patientDetail.getFirstName());

                    } catch (FeignException e) {
                        log.warn("Failed to fetch patient for id={}", appointment.getPatientId());
                        response.setPatientName("UNKNOWN");
                    }

                    return response;
                })
                .toList();
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException(
                    "Appointment not found with ID: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }
}