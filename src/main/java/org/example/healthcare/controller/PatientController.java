package org.example.healthcare.controller;

import org.example.healthcare.dao.PatientDAO;
import org.example.healthcare.model.Patient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.example.healthcare.service.HealthcareService;



import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final HealthcareService healthcareService;

    public PatientController(HealthcareService healthcareService) {
        this.healthcareService = healthcareService;
    }


    // GET /patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return healthcareService.getPatients();
    }

    // POST /patients
    @PostMapping
    public void addPatient(@RequestBody Patient patient) {
        healthcareService.addPatient(patient);
    }

    // PUT /patients/{id}
    @PutMapping("/{id}")
    public void updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        patient.setId(id);
    }


    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable int id) {
        healthcareService.deletePatient(id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) {
        return healthcareService.getPatientById(id);
    }

}
