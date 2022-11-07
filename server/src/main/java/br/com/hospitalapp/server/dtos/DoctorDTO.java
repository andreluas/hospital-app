package br.com.hospitalapp.server.dtos;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DoctorDTO {

    private UUID doctorId;

    @NotBlank(message = "Campo obrigatório")
    private String name;

    @CPF
    private String cpf;

    @Pattern(regexp = "[0-9]{6}.[A-Z]{2}", message = "O CRM é inválido. Formato válido: 123456/RJ")
    private String crm;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birth_date;
    private String sex;

    public DoctorDTO() {
    }

    public DoctorDTO(UUID doctorId, @NotBlank(message = "Campo obrigatório") String name, @CPF String cpf,
            @Pattern(regexp = "[0-9]{6}.[A-Z]{2}", message = "O CRM é inválido. Formato válido: 123456/RJ") String crm,
            Date birth_date, String sex) {
        this.doctorId = doctorId;
        this.name = name;
        this.cpf = cpf;
        this.crm = crm;
        this.birth_date = birth_date;
        this.sex = sex;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
