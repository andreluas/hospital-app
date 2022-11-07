package br.com.hospitalapp.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hospitalapp.server.enums.RoleName;
import br.com.hospitalapp.server.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    RoleModel findByRoleName(RoleName roleName);
}
