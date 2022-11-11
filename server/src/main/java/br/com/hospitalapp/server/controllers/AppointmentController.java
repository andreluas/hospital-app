package br.com.hospitalapp.server.controllers;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.hospitalapp.server.dtos.AppointmentDTO;
import br.com.hospitalapp.server.services.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping()
    public ResponseEntity<AppointmentDTO> register(@Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO appointmentDTO = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appointmentDTO.getAppointmentId())
                .toUri();
        return ResponseEntity.created(uri).body(appointmentDTO);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll() {
        List<AppointmentDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/date")
    public ResponseEntity<List<AppointmentDTO>> searchBetweenDates(@RequestParam Date startIn,
            @RequestParam Date endIn) {
        List<AppointmentDTO> list = service.searchBetweenDates(startIn, endIn);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable UUID id) {
        AppointmentDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable UUID id, @Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO appointmentDTO = service.update(id, dto);
        return ResponseEntity.ok().body(appointmentDTO);
    }
}
