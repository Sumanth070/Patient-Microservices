package com.microservices.appointment.service.Impl;

import com.microservices.appointment.client.PatientClient;
import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.dto.PatientResponse;
import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.mapper.AppointmentMapper;
import com.microservices.appointment.repository.AppointmentRepository;
import com.microservices.appointment.service.AppointmentService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final  AppointmentMapper appointmentMapper;

    private final PatientClient patientClient;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, PatientClient patientClient) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.patientClient = patientClient;
    }


    private PatientResponse validatePatient(UUID id) {
        try {
            return patientClient.getPatientById(id);
        } catch (FeignException.NotFound ex) {
            throw new RuntimeException("Patient not found: " + id);
        }

    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        PatientResponse patientDetail= validatePatient(request.getPatientId());
        Appointment appointment = appointmentMapper.toEntity(request);
        Appointment saveAppointment = appointmentRepository.save(appointment);
        AppointmentResponse response = appointmentMapper.toResponse(saveAppointment);
        response.setPatientName(patientDetail.getFirstName());
        return response;
        }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> {
                    AppointmentResponse response =
                            appointmentMapper.toResponse(appointment);
                    PatientResponse patientDetail =
                            patientClient.getPatientById(appointment.getPatientId());
                    response.setPatientName(patientDetail.getFirstName());
                    return response;
                })
                .toList();
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
