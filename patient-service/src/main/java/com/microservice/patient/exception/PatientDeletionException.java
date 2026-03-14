package com.microservice.patient.exception;

import io.micrometer.core.instrument.distribution.StepBucketHistogram;

import java.sql.Struct;

public class PatientDeletionException extends RuntimeException{
    public PatientDeletionException(String message){
        super(message);
    }
}
