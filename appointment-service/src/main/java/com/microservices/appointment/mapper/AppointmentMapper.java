package com.microservices.appointment.mapper;

import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;
import com.microservices.appointment.entity.Appointment;
import org.springframework.stereotype.Component;


@Component
public class AppointmentMapper {

    public Appointment toEntity(CreateAppointmentRequest request){
        Appointment appointment = new Appointment();

        appointment.setDoctorName(request.getDoctorName());
        appointment.setPatientId(request.getPatientId());
        appointment.setScheduledAt(request.getScheduledAt());
        return appointment;
    }

    public AppointmentResponse toResponse(Appointment appointment){
        AppointmentResponse response = new AppointmentResponse();

        response.setAppointmentId(appointment.getAppointmentId());
        response.setDoctorName(appointment.getDoctorName());
        response.setPatientId(appointment.getPatientId());
        response.setScheduledAt(appointment.getScheduledAt());
        response.setCreatedAt(appointment.getCreatedAt());
        response.setStatus(appointment.getStatus());

        return response;

    }
}
