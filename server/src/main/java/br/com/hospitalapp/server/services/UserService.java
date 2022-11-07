package br.com.hospitalapp.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hospitalapp.server.dtos.UserDTO;
import br.com.hospitalapp.server.enums.RoleName;
import br.com.hospitalapp.server.mapper.UserMapper;
import br.com.hospitalapp.server.models.RoleModel;
import br.com.hospitalapp.server.models.UserModel;
import br.com.hospitalapp.server.repositories.RoleRepository;
import br.com.hospitalapp.server.repositories.UserRepository;
import br.com.hospitalapp.server.services.exceptions.ConstraintViolationException;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO register(UserDTO dto) {
        Optional<UserModel> userVerify = userRepository.findByUsername(dto.getUsername());
        if (!userVerify.isPresent()) {
            UserModel entity = mapper.dtoToEntity(dto);
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (dto.getRoles() == null) {
                RoleModel roleModel = roleRepository.findByRoleName(RoleName.ROLE_USER);
                List<RoleModel> listRole = new ArrayList<>();
                listRole.add(roleModel);
                entity.setRoles(listRole);
                userRepository.save(entity);
            }
            return mapper.entityToDTO(entity);
        } else {
            throw new ConstraintViolationException("Usuário já cadastrado");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
    }

}
