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

import br.com.hospitalapp.server.dtos.DoctorDTO;
import br.com.hospitalapp.server.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @PostMapping("/register")
    public ResponseEntity<DoctorDTO> register(@Valid @RequestBody DoctorDTO dto) {
        DoctorDTO doctorDTO = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(doctorDTO.getDoctorId())
                .toUri();
        return ResponseEntity.created(uri).body(doctorDTO);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll() {
        List<DoctorDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable UUID id) {
        DoctorDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<DoctorDTO> findByCpf(@PathVariable String cpf) {
        DoctorDTO dto = service.findByCpf(cpf);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/worked")
    public ResponseEntity<List<DoctorDTO>> searchDoctorsWorked(@RequestParam Date startIn,
            @RequestParam Date endIn) {
        List<DoctorDTO> list = service.searchDoctorsWorked(startIn, endIn);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/by-patient/{patientId}")
    public ResponseEntity<List<DoctorDTO>> searchDoctorsWorked(@PathVariable UUID patientId) {
        List<DoctorDTO> list = service.searchDoctorsByPatient(patientId);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable UUID id, @Valid @RequestBody DoctorDTO dto) {
        DoctorDTO doctorDTO = service.update(id, dto);
        return ResponseEntity.ok().body(doctorDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
