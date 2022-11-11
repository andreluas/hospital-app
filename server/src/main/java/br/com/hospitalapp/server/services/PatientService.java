package br.com.hospitalapp.server.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hospitalapp.server.dtos.PatientDTO;
import br.com.hospitalapp.server.enums.RoleName;
import br.com.hospitalapp.server.mapper.PatientMapper;
import br.com.hospitalapp.server.models.AppointmentModel;
import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.PatientModel;
import br.com.hospitalapp.server.models.RoleModel;
import br.com.hospitalapp.server.models.UserModel;
import br.com.hospitalapp.server.repositories.AppointmentRepository;
import br.com.hospitalapp.server.repositories.DoctorRepository;
import br.com.hospitalapp.server.repositories.PatientRepository;
import br.com.hospitalapp.server.repositories.RoleRepository;
import br.com.hospitalapp.server.repositories.UserRepository;
import br.com.hospitalapp.server.services.exceptions.DataBaseException;
import br.com.hospitalapp.server.services.exceptions.InternalServerException;
import br.com.hospitalapp.server.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientMapper mapper;

    public PatientDTO register(PatientDTO dto) {
        PatientModel entity = new PatientModel();
        Optional<PatientModel> verifyCPF = patientRepository.findByCpf(dto.getCpf());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userDetails = ((UserDetails) principal).getUsername();
        UserModel userModel = userRepository.findByUsername(userDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails));
        if (verifyCPF.isPresent()) {
            throw new InternalServerException("Paciente já cadastrado");
        }
        List<RoleModel> listRole = userModel.getRoles();
        RoleModel roleModel = roleRepository.findByRoleName(RoleName.ROLE_PATIENT);
        entity = mapper.dtoToEntity(dto);
        entity.setUserId(userModel);
        listRole.add(roleModel);
        userModel.setRoles(listRole);
        patientRepository.save(entity);
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        List<PatientModel> list = patientRepository.findAll();
        return mapper.entityListToDtoList(list);
    }

    @Transactional(readOnly = true)
    public PatientDTO findById(UUID id) {
        Optional<PatientModel> op = patientRepository.findById(id);
        PatientModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public PatientDTO findByCpf(String cpf) {
        Optional<PatientModel> op = patientRepository.findByCpf(cpf);
        PatientModel entity = op.orElseThrow(() -> new ResourceNotFoundException("CPF not found " + cpf));
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> searchPatientByDoctor(UUID doctorId) {
        Optional<DoctorModel> opDoctor = doctorRepository.findById(doctorId);
        DoctorModel doctor = opDoctor.orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        List<PatientModel> list = patientRepository.searchPatientByDoctor(doctor);
        if (list.size() == 0) {
            throw new ResourceNotFoundException("No doctors for this patient");
        }
        return mapper.entityListToDtoList(list);
    }

    public PatientDTO update(UUID id, PatientDTO dto) {
        Optional<PatientModel> op = patientRepository.findById(id);
        PatientModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        entity = mapper.toDoctorModel(dto, entity);
        patientRepository.save(entity);
        return mapper.entityToDTO(entity);
    }

    public void delete(UUID id) {
        Optional<PatientModel> pOptional = patientRepository.findById(id);
        PatientModel entity = pOptional.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        Optional<AppointmentModel> aOptional = appointmentRepository.findByPatient(entity);

        if (aOptional.isPresent()) {
            throw new DataBaseException("Patient has appointments cannot be excluded");
        } else {
            patientRepository.deleteById(id);
        }
    }
}
