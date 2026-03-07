package com.microservices.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateAppointmentRequest {

    @NotNull
    private UUID patientId;

    @NotBlank
    private String doctorName;

    @NotNull
    private LocalDateTime scheduledAt;

    public CreateAppointmentRequest(UUID patientId, String doctorName, LocalDateTime scheduledAt) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.scheduledAt = scheduledAt;
    }
    public CreateAppointmentRequest(){

    }


    public @NotNull LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(@NotNull LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public @NotBlank String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(@NotBlank String doctorName) {
        this.doctorName = doctorName;
    }

    public @NotNull UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull UUID patientId) {
        this.patientId = patientId;
    }

}
