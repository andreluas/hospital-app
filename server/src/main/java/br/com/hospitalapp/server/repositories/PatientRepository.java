package br.com.hospitalapp.server.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.PatientModel;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {

    Optional<PatientModel> findByCpf(String cpf);

    @Query("SELECT obj FROM PatientModel obj INNER JOIN obj.appointments ap WHERE ap.doctor = :doctor")
    List<PatientModel> searchPatientByDoctor(DoctorModel doctor);
}
