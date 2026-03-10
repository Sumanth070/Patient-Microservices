package com.microservice.patient.service.Impl;

import com.microservice.patient.dto.CreatePatientRequest;
import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.entity.Patient;
import com.microservice.patient.exception.DuplicateEmailException;
import com.microservice.patient.exception.InvalidPatientException;
import com.microservice.patient.exception.PatientDeletionException;
import com.microservice.patient.exception.PatientNotFoundException;
import com.microservice.patient.repository.PatientRepository;
import com.microservice.patient.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse createPatient(CreatePatientRequest request) {

        log.debug("checking duplicate Email",request.getEmail());

        if(patientRepository.existsByEmail(request.getEmail())){
            log.warn("duplicate email detected",request.getEmail());
            throw new DuplicateEmailException("Patient with this email already exist" + request.getEmail());
        }

        log.debug("checking Date of birth",request.getDateOfBirth());

        if (request.getDateOfBirth().isAfter(LocalDate.now())){
            log.warn("date of birth error check for the entry date of birth"+request.getDateOfBirth());
            throw new InvalidPatientException("Patient date of birth is incorrect"+request.getDateOfBirth());
        }

        log.info("Saving patient email={}", request.getEmail());
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
        Patient patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patient not found"+id));
        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deletePatient(UUID id) {
       boolean hasActiveAppointments = false;
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException("Patient id dosenot exist to delete"));

        if(hasActiveAppointments){
            throw new PatientDeletionException("patient"+id+"has active appointment cannot be deleted");
        }

        patientRepository.deleteById(patient.getId());
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


