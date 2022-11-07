package br.com.hospitalapp.server.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.hospitalapp.server.dtos.PatientDTO;
import br.com.hospitalapp.server.models.PatientModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PatientMapper {

    PatientModel dtoToEntity(PatientDTO patientDTO);

    @Mapping(source = "patientId", target = "patientId")
    PatientDTO entityToDTO(PatientModel patientModel);

    List<PatientDTO> entityListToDtoList(List<PatientModel> listAll);

    @Mapping(target = "patientId", ignore = true)
    PatientModel toDoctorModel(PatientDTO doctorDTO, @MappingTarget PatientModel doctorModel);
}
