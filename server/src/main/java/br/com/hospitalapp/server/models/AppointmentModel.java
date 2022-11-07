package br.com.hospitalapp.server.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_APPOINTMENTS")
public class AppointmentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID appointmentId;

    private Date date;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId")
    private DoctorModel doctor;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patientId")
    private PatientModel patient;

    private String observations;

    public AppointmentModel() {
    }

    public AppointmentModel(UUID appointmentId, Date date, DoctorModel doctor, PatientModel patient,
            String observations) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.observations = observations;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public DoctorModel getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorModel doctor) {
        this.doctor = doctor;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appointmentId == null) ? 0 : appointmentId.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppointmentModel other = (AppointmentModel) obj;
        if (appointmentId == null) {
            if (other.appointmentId != null)
                return false;
        } else if (!appointmentId.equals(other.appointmentId))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (doctor == null) {
            if (other.doctor != null)
                return false;
        } else if (!doctor.equals(other.doctor))
            return false;
        if (patient == null) {
            if (other.patient != null)
                return false;
        } else if (!patient.equals(other.patient))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
