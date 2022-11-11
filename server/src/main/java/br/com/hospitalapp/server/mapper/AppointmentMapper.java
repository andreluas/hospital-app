package br.com.hospitalapp.server.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.hospitalapp.server.dtos.AppointmentDTO;
import br.com.hospitalapp.server.models.AppointmentModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    @Mapping(target = "appointmentId", source = "appointmentDTO.appointmentId")
    @Mapping(target = "doctor.doctorId", source = "appointmentDTO.doctorId")
    @Mapping(target = "patient.patientId", source = "appointmentDTO.patientId")
    AppointmentModel dtoToEntity(AppointmentDTO appointmentDTO);

    @Mapping(target = "appointmentId", source = "appointmentModel.appointmentId")
    @Mapping(target = "doctorId", source = "appointmentModel.doctor.doctorId")
    @Mapping(target = "patientId", source = "appointmentModel.patient.patientId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppointmentDTO entityToDTO(AppointmentModel appointmentModel);

    List<AppointmentDTO> entityListToDtoList(List<AppointmentModel> listAll);

    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "patient.patientId", source = "appointmentDTO.patientId")
    @Mapping(target = "doctor.doctorId", source = "appointmentDTO.doctorId")
    AppointmentModel toAppointmentModel(AppointmentDTO appointmentDTO,
            @MappingTarget AppointmentModel appointmentModel);
}
