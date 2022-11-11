package br.com.hospitalapp.server.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.models.AppointmentModel;
import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.PatientModel;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, UUID> {

    Optional<AppointmentModel> findByDoctor(DoctorModel doctor);

    Optional<AppointmentModel> findByPatient(PatientModel patient);

    @Query("SELECT obj FROM AppointmentModel obj WHERE (obj.date) BETWEEN (:startIn) and (:endIn) ORDER BY (obj.date)")
    List<AppointmentModel> searchBetweenDates(Date startIn, Date endIn);
}
