package br.com.hospitalapp.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.models.DoctorModel;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, UUID> {

    Optional<DoctorModel> findByCpf(String cpf);

    Optional<DoctorModel> findByCrm(String crm);
}
