package br.com.hospitalapp.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.models.PatientModel;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {

    Optional<PatientModel> findByCpf(String cpf);
}
