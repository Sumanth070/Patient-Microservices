package com.microservices.appointment.repository;

import com.microservices.appointment.entity.Appointment;
import com.microservices.appointment.entity.AppointmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Test
    void shouldSaveAppointment() {

        Appointment appointment = new Appointment();
        appointment.setPatientId(UUID.randomUUID());
        appointment.setDoctorName("Dr Strange");
        appointment.setScheduledAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());

        Appointment saved = appointmentRepository.save(appointment);

        assertNotNull(saved.getAppointmentId());
        assertEquals("Dr Strange", saved.getDoctorName());
    }


    @Test
    void shouldFindAllAppointments() {

        Appointment appointment = new Appointment();
        appointment.setPatientId(UUID.randomUUID());
        appointment.setDoctorName("Dr Who");
        appointment.setScheduledAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentRepository.save(appointment);

        var appointments = appointmentRepository.findAll();

        assertFalse(appointments.isEmpty());
    }


    @Test
    void shouldDeleteAppointment() {

        Appointment appointment = new Appointment();
        appointment.setPatientId(UUID.randomUUID());
        appointment.setDoctorName("Dr House");
        appointment.setScheduledAt(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());

        Appointment saved = appointmentRepository.save(appointment);

        appointmentRepository.deleteById(saved.getAppointmentId());

        var result = appointmentRepository.findById(saved.getAppointmentId());

        assertTrue(result.isEmpty());
    }
}