package com.microservices.appointment.repository;

import com.microservices.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsById(Long appointmentId);
}
