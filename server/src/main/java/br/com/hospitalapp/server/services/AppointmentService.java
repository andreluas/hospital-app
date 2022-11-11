package br.com.hospitalapp.server.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hospitalapp.server.dtos.AppointmentDTO;
import br.com.hospitalapp.server.mapper.AppointmentMapper;
import br.com.hospitalapp.server.models.AppointmentModel;
import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.PatientModel;
import br.com.hospitalapp.server.repositories.AppointmentRepository;
import br.com.hospitalapp.server.repositories.DoctorRepository;
import br.com.hospitalapp.server.repositories.PatientRepository;
import br.com.hospitalapp.server.services.exceptions.ResourceNotFoundException;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentMapper mapper;

    @Transactional
    public AppointmentDTO insert(AppointmentDTO dto) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findById(dto.getDoctorId());
        Optional<PatientModel> patientOptional = patientRepository.findById(dto.getPatientId());
        AppointmentModel entity = mapper.dtoToEntity(dto);
        entity.setDoctor(
                doctorOptional.orElseThrow(() -> new ResourceNotFoundException("Id not found " + dto.getDoctorId())));
        entity.setPatient(
                patientOptional.orElseThrow(() -> new ResourceNotFoundException("Id not found " + dto.getPatientId())));
        appointmentRepository.save(entity);
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        List<AppointmentModel> list = appointmentRepository.findAll();
        return mapper.entityListToDtoList(list);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> searchBetweenDates(Date startIn, Date endIn) {
        List<AppointmentModel> list = appointmentRepository.searchBetweenDates(startIn, endIn);
        if (list.size() == 0) {
            throw new ResourceNotFoundException("No appointments on this date");
        }
        return mapper.entityListToDtoList(list);
    }

    @Transactional(readOnly = true)
    public AppointmentDTO findById(UUID id) {
        Optional<AppointmentModel> op = appointmentRepository.findById(id);
        AppointmentModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return mapper.entityToDTO(entity);
    }

    public void delete(UUID id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public AppointmentDTO update(UUID id, AppointmentDTO dto) {
        Optional<AppointmentModel> op = appointmentRepository.findById(id);
        AppointmentModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        entity = mapper.toAppointmentModel(dto, entity);
        appointmentRepository.save(entity);
        return mapper.entityToDTO(entity);
    }
}
