package com.microservice.patient.controller;

import com.microservice.patient.dto.CreatePatientRequest;
import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.service.PatientService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody CreatePatientRequest request) {
        log.info("Create patient request received email={}", request.getEmail());
        PatientResponse response = patientService.createPatient(request);
        log.info("Patient created successfully id={}", response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable("id") UUID id) {

        log.info("Fetching patient id={}", id);
        PatientResponse response = patientService.getPatientById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {

        List<PatientResponse> patients = patientService.getAllPatients();

        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable("id") UUID id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}