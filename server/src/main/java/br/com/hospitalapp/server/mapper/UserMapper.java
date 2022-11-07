package br.com.hospitalapp.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hospitalapp.server.dtos.UserDTO;
import br.com.hospitalapp.server.models.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    UserModel dtoToEntity(UserDTO userDTO);

    @Mapping(source = "userId", target = "userId")
    UserDTO entityToDTO(UserModel userModel);
}
