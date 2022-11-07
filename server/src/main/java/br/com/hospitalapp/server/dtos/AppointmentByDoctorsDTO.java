package br.com.hospitalapp.server.dtos;

public class AppointmentByDoctorsDTO {

    private DoctorDTO doctor;

    public AppointmentByDoctorsDTO() {
    }

    public AppointmentByDoctorsDTO(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
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
        AppointmentByDoctorsDTO other = (AppointmentByDoctorsDTO) obj;
        if (doctor == null) {
            if (other.doctor != null)
                return false;
        } else if (!doctor.equals(other.doctor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
