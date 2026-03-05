package com.microservice.patient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import java.time.LocalDate;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private  String lastName;

    public Patient(UUID id, String firstName, String lastName, String email, String address, LocalDate dateOfBirth, LocalDate registeredDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.registeredDate = registeredDate;
    }

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String address;

    public Patient() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public @NotNull LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(@NotNull LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }
//@Pattern()

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Column(updatable = false)
    private LocalDate registeredDate;

    @PrePersist
    public void prePersist(){
        this.registeredDate = LocalDate.now();
    }
}
