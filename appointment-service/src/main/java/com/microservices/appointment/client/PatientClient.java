package com.microservices.appointment.client;

import com.microservices.appointment.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "patient-service",
        url = "${patient.service.url}")
public interface PatientClient {

    @GetMapping("/api/v1/patients/{id}")
    PatientResponse getPatientById(@PathVariable("id") UUID id);

    @GetMapping("/api/v1/patients")
    List<PatientResponse> getAllPatients();

}
