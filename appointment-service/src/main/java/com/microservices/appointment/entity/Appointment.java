package com.microservices.appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @NotBlank
    private String doctorName;

    @NotNull
    private UUID patientId;

    public Appointment(Long appointmentId, String doctorName, UUID patientId, LocalDateTime createdAt, LocalDateTime scheduledAt, AppointmentStatus status) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.createdAt = createdAt;
        this.scheduledAt = scheduledAt;
        this.status = status;
    }

    public Appointment() {

    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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

    public @NotNull LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(@NotNull LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public @NotNull AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull AppointmentStatus status) {
        this.status = status;
    }

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime scheduledAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
