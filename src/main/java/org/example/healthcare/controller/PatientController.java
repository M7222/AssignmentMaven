package org.example.healthcare.controller;

import org.example.healthcare.dao.PatientDAO;
import org.example.healthcare.model.Patient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientDAO patientDAO;

    public PatientController(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    // GET /patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientDAO.getAll();
    }

    // POST /patients
    @PostMapping
    public void addPatient(@RequestBody Patient patient) {
        patientDAO.save(patient);
    }

    // PUT /patients/{id}
    @PutMapping("/{id}")
    public void updatePatient(@PathVariable int id,
                              @RequestBody Patient patient) {
        patient.setId(id);
        patientDAO.update(patient);
    }

    // DELETE /patients/{id}
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable int id) {
        patientDAO.delete(id);
    }
}
