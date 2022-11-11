package br.com.hospitalapp.server.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.PatientModel;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, UUID> {

    Optional<DoctorModel> findByCpf(String cpf);

    Optional<DoctorModel> findByCrm(String crm);

    @Query("SELECT obj FROM DoctorModel obj INNER JOIN obj.appointments ap WHERE ap.date BETWEEN :startIn and :endIn GROUP BY obj.id")
    List<DoctorModel> searchDoctorsWorked(Date startIn, Date endIn);

    @Query("SELECT obj FROM DoctorModel obj INNER JOIN obj.appointments ap WHERE ap.patient = :patient")
    List<DoctorModel> searchDoctorsByPatient(PatientModel patient);
}
