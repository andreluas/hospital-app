package br.com.hospitalapp.server.dtos;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppointmentDTO {

    private UUID appointmentId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private UUID doctorId;

    private UUID patientId;

    private String observations;

    public AppointmentDTO() {
    }

    public AppointmentDTO(UUID appointmentId, Date date, UUID doctorId,
            UUID patientId, String observations) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.observations = observations;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
