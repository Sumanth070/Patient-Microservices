package com.microservices.appointment.dto;


import com.microservices.appointment.entity.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentResponse {
    private UUID patientId;
    private Long appointmentId;
    private AppointmentStatus status;
    private String doctorName;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
    private String patientName;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    public AppointmentResponse(UUID patientId,
                               Long appointmentId,
                               AppointmentStatus status,
                               String doctorName,
                               LocalDateTime scheduledAt,
                               LocalDateTime createdAt) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.status = status;
        this.doctorName = doctorName;
        this.scheduledAt = scheduledAt;
        this.createdAt = createdAt;
    }
    public AppointmentResponse(String patientName) {
        this.patientName = patientName;
    }


    public AppointmentResponse() {

    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
