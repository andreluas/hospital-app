package br.com.hospitalapp.server.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_DOCTOR")
public class DoctorModel extends Person {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID doctorId;

    @Column(nullable = false, unique = true)
    private String crm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<AppointmentModel> appointments = new ArrayList<>();

    public DoctorModel() {
    }

    public DoctorModel(String name, String cpf, Date birth_date, String sex, UserModel userId, UUID doctorId,
            String crm) {
        super(name, cpf, birth_date, sex, userId);
        this.doctorId = doctorId;
        this.crm = crm;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((doctorId == null) ? 0 : doctorId.hashCode());
        result = prime * result + ((crm == null) ? 0 : crm.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoctorModel other = (DoctorModel) obj;
        if (doctorId == null) {
            if (other.doctorId != null)
                return false;
        } else if (!doctorId.equals(other.doctorId))
            return false;
        if (crm == null) {
            if (other.crm != null)
                return false;
        } else if (!crm.equals(other.crm))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
