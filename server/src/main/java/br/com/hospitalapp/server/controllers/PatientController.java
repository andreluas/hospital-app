package br.com.hospitalapp.server.controllers;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.hospitalapp.server.dtos.PatientDTO;
import br.com.hospitalapp.server.services.PatientService;
import br.com.hospitalapp.server.utils.pdf.PatientReport.service.PatientReport;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService service;

    @Autowired
    private PatientReport report;

    @PostMapping("/register")
    public ResponseEntity<PatientDTO> register(@Valid @RequestBody PatientDTO dto) {
        PatientDTO patientDTO = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(patientDTO.getPatientId())
                .toUri();
        return ResponseEntity.created(uri).body(patientDTO);
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() {
        List<PatientDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable UUID id) {
        PatientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<PatientDTO> findByCpf(@PathVariable String cpf) {
        PatientDTO dto = service.findByCpf(cpf);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable UUID id, @Valid @RequestBody PatientDTO dto) {
        PatientDTO PatientDTO = service.update(id, dto);
        return ResponseEntity.ok().body(PatientDTO);
    }

    @GetMapping(value = "/by-doctor/{doctorId}")
    public ResponseEntity<List<PatientDTO>> searchDoctorsWorked(@PathVariable UUID doctorId) {
        List<PatientDTO> list = service.searchPatientByDoctor(doctorId);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> teste() {
        ByteArrayInputStream bis = report.pdfGenerate();
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=patient.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
