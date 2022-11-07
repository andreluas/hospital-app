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

import br.com.hospitalapp.server.dtos.DoctorDTO;
import br.com.hospitalapp.server.enums.RoleName;
import br.com.hospitalapp.server.mapper.DoctorMapper;
import br.com.hospitalapp.server.models.AppointmentModel;
import br.com.hospitalapp.server.models.DoctorModel;
import br.com.hospitalapp.server.models.RoleModel;
import br.com.hospitalapp.server.models.UserModel;
import br.com.hospitalapp.server.repositories.AppointmentRepository;
import br.com.hospitalapp.server.repositories.DoctorRepository;
import br.com.hospitalapp.server.repositories.RoleRepository;
import br.com.hospitalapp.server.repositories.UserRepository;
import br.com.hospitalapp.server.services.exceptions.DataBaseException;
import br.com.hospitalapp.server.services.exceptions.InternalServerException;
import br.com.hospitalapp.server.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorMapper mapper;

    public DoctorDTO register(DoctorDTO dto) {
        DoctorModel entity = new DoctorModel();
        Optional<DoctorModel> verifyCPF = doctorRepository.findByCpf(dto.getCpf());
        Optional<DoctorModel> verifyCRM = doctorRepository.findByCrm(dto.getCrm());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userDetails = ((UserDetails) principal).getUsername();
        UserModel userModel = userRepository.findByUsername(userDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails));
        if (verifyCPF.isPresent() || verifyCRM.isPresent()) {
            throw new InternalServerException("Médico já cadastrado");
        }
        List<RoleModel> listRole = userModel.getRoles();
        RoleModel roleModel = roleRepository.findByRoleName(RoleName.ROLE_DOCTOR);
        entity = mapper.dtoToEntity(dto);
        entity.setUserId(userModel);
        listRole.add(roleModel);
        userModel.setRoles(listRole);
        doctorRepository.save(entity);
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<DoctorDTO> findAll() {
        List<DoctorModel> list = doctorRepository.findAll();
        return mapper.entityListToDtoList(list);
    }

    @Transactional(readOnly = true)
    public DoctorDTO findById(UUID id) {
        Optional<DoctorModel> op = doctorRepository.findById(id);
        DoctorModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        return mapper.entityToDTO(entity);
    }

    @Transactional(readOnly = true)
    public DoctorDTO findByCpf(String cpf) {
        Optional<DoctorModel> op = doctorRepository.findByCpf(cpf);
        DoctorModel entity = op.orElseThrow(() -> new ResourceNotFoundException("CPF not found " + cpf));
        return mapper.entityToDTO(entity);
    }

    public DoctorDTO update(UUID id, DoctorDTO dto) {
        Optional<DoctorModel> op = doctorRepository.findById(id);
        DoctorModel entity = op.orElseThrow(() -> new ResourceNotFoundException("Entity not found " + id));
        entity = mapper.toDoctorModel(dto, entity);
        doctorRepository.save(entity);
        return mapper.entityToDTO(entity);
    }

    public void delete(UUID id) {
        Optional<DoctorModel> dOptional = doctorRepository.findById(id);
        DoctorModel entity = dOptional.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        Optional<AppointmentModel> aOptional = appointmentRepository.findByDoctor(entity);

        if (aOptional.isPresent()) {
            throw new DataBaseException("Doctor has appointments cannot be excluded");
        } else {
            doctorRepository.deleteById(id);
        }
    }
}
