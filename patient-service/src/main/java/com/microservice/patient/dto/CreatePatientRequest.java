package com.microservice.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public class CreatePatientRequest {
    public CreatePatientRequest(String firstName, String lastName, LocalDate dateOfBirth, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
    }
    public @NotNull String getFirstName() {
        return firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public @NotNull String getAddress() {
        return address;
    }

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(@NotNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    @NotNull
    @Email
    private String email;
    @NotNull
    private String address;
}
