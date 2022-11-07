package br.com.hospitalapp.server.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.hospitalapp.server.dtos.DoctorDTO;
import br.com.hospitalapp.server.models.DoctorModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface DoctorMapper {

    DoctorModel dtoToEntity(DoctorDTO doctorDTO);

    @Mapping(source = "doctorId", target = "doctorId")
    DoctorDTO entityToDTO(DoctorModel doctorModel);

    List<DoctorDTO> entityListToDtoList(List<DoctorModel> listAll);

    @Mapping(target = "doctorId", ignore = true)
    DoctorModel toDoctorModel(DoctorDTO doctorDTO, @MappingTarget DoctorModel doctorModel);
}
