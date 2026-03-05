package com.microservice.patient.service;


import com.microservice.patient.dto.CreatePatientRequest;
import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.entity.Patient;
import com.microservice.patient.repository.PatientRepository;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientResponse createPatient(CreatePatientRequest request);
    PatientResponse getPatientById(UUID id);
    List<PatientResponse> getAllPatients();
    void deletePatient(UUID id);
}
