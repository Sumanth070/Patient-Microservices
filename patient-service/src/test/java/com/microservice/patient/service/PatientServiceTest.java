package com.microservice.patient.service;

import com.microservice.patient.dto.CreatePatientRequest;
import com.microservice.patient.dto.PatientResponse;
import com.microservice.patient.entity.Patient;
import com.microservice.patient.repository.PatientRepository;
import com.microservice.patient.service.Impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void createPatient_shouldSavePatientAndReturnResponse(){

        // Arrange where we need to arrange or keep the things to test
        CreatePatientRequest request = new CreatePatientRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setAddress("John city, new york, 533105");
        request.setEmail("john@test.com");
        request.setDateOfBirth(LocalDate.of(1990, 1, 21));

        Patient savedPatient = new Patient();
        savedPatient.setId(UUID.randomUUID());
        savedPatient.setFirstName("John");
        savedPatient.setLastName("Doe");
        savedPatient.setEmail("john@test.com");
        savedPatient.setAddress("221B Baker Street, London, UK");
        savedPatient.setDateOfBirth(LocalDate.of(1990, 1, 21));
        savedPatient.setRegisteredDate(LocalDate.now());

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        //Act act on the the object
        PatientResponse response = patientService.createPatient(request);

        // Asssert
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("john@test.com", response.getEmail());

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void getPatientById_shouldThrowException_whenPatientNotFound(){
        UUID patientId = UUID.randomUUID();

        when(patientRepository.findById(patientId))
                .thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        patientService.getPatientById(patientId)
                );

        assertEquals("Patient not found" + patientId, exception.getMessage());

        verify(patientRepository).findById(patientId);
        assertTrue(exception.getMessage().contains(patientId.toString()));
    }
    @Test
    void getPatientById_shouldReturnPatient_whenPatientExist(){

        UUID patientId = UUID.randomUUID();
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setId(patientId);
        patient.setLastName("Doe");
        patient.setAddress("new york, park squaree");
        patient.setDateOfBirth(LocalDate.of(1990,1,21));
        patient.setRegisteredDate(LocalDate.now());
        patient.setEmail("John@test.com");

        when(patientRepository.findById(patientId))
                .thenReturn(Optional.of(patient));
        PatientResponse response = patientService.getPatientById(patientId);
        assertNotNull(response);
        assertEquals("John",patient.getFirstName());
        assertEquals("John@test.com", response.getEmail());

        verify(patientRepository).findById(patientId);
    }


}
