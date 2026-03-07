package com.microservices.appointment.service;


import com.microservices.appointment.dto.AppointmentResponse;
import com.microservices.appointment.dto.CreateAppointmentRequest;

import java.util.List;

public interface AppointmentService {

      AppointmentResponse createAppointment(CreateAppointmentRequest request);

      List<AppointmentResponse> getAllAppointments();

      void deleteAppointment(Long appointmentId);

}
