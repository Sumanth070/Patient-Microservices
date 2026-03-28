package com.microservices.appointment.event.model;


import java.time.LocalDateTime;

public class AppointmentCreatedEvent {

    private String eventId;
    private EventType eventType;
    private String version;
    private LocalDateTime timestamp;
    private String source;
    private DataPayload payload;

    public AppointmentCreatedEvent() {
    }
    public AppointmentCreatedEvent(String eventId, EventType eventType, String version, LocalDateTime timestamp, String source, DataPayload payload) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.version = version;
        this.timestamp = timestamp;
        this.source = source;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public DataPayload getPayload() {
        return payload;
    }

    public void setPayload(DataPayload payload) {
        this.payload = payload;
    }

    public static class DataPayload
    {
        private String appointmentId;
        private String doctorName;
        private String patientId;
        private LocalDateTime scheduledAt;

        public DataPayload() {
        }

        public DataPayload(String appointmentId, String doctorName, String patientId, LocalDateTime scheduledAt) {
            this.appointmentId = appointmentId;
            this.doctorName = doctorName;
            this.patientId = patientId;
            this.scheduledAt = scheduledAt;
        }

        public String getAppointmentId() {
            return appointmentId;
        }

        public void setAppointmentId(String appointmentId) {
            this.appointmentId = appointmentId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public LocalDateTime getScheduledAt() {
            return scheduledAt;
        }

        public void setScheduledAt(LocalDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
        }
    }

}
