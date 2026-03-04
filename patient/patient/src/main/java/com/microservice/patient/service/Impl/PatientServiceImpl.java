package com.microservice.patient.service.Impl;

import com.microservice.patient.dto.CreatePatientRequest;
import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.entity.Patient;
import com.microservice.patient.repository.PatientRepository;
import com.microservice.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setDateOfBirth(request.getDateOfBirth());

        Patient savePatient = patientRepository.save(patient);
        return mapToResponse(savePatient);
    }

    @Override
    public PatientResponse getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));
        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return new PatientResponse( patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getDateOfBirth(),
                patient.getRegisteredDate());
    }

}


