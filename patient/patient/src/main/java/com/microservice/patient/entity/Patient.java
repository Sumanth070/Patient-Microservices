package com.microservice.patient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class patientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String FirstName;

    @NotNull
    private  String LastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String Address;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate registeredDate;
    
}
