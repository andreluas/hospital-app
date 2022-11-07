package br.com.hospitalapp.server.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppointmentDatesDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    public AppointmentDatesDTO() {
    }

    public AppointmentDatesDTO(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        AppointmentDatesDTO other = (AppointmentDatesDTO) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
