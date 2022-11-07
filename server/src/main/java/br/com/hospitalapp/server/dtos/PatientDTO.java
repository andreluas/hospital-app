package br.com.hospitalapp.server.dtos;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PatientDTO {

    private UUID patientId;

    @NotBlank(message = "Campo obrigatório")
    private String name;

    @CPF
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birth_date;
    private String sex;

    public PatientDTO() {
    }

    public PatientDTO(UUID patientId, @NotBlank(message = "Campo obrigatório") String name, @CPF String cpf,
            Date birth_date, String sex) {
        this.patientId = patientId;
        this.name = name;
        this.cpf = cpf;
        this.birth_date = birth_date;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
