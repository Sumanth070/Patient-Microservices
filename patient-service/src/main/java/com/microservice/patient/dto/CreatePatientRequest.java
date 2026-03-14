package com.microservice.patient.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public class CreatePatientRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String firstName;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "address cannot be empty")
    private String address;

    public CreatePatientRequest(String firstName, String lastName, LocalDate dateOfBirth, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
    }
    public CreatePatientRequest(){

    }

    public @NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 100) @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 100) @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 100) @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Name cannot be empty") @Size(min = 2, max = 100) @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank String address) {
        this.address = address;
    }
}
