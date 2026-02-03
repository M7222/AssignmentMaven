package org.example.healthcare.controller;

import org.example.healthcare.dao.MedicalProfessionalDAO;
import org.example.healthcare.model.MedicalProfessional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class MedicalProfessionalController {

    private final MedicalProfessionalDAO doctorDAO;

    public MedicalProfessionalController(MedicalProfessionalDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @GetMapping
    public List<MedicalProfessional> getAllDoctors() {
        return doctorDAO.getAll();
    }

    @PostMapping
    public void addDoctor(@RequestBody MedicalProfessional doctor) {
        doctorDAO.save(doctor);
    }

    @PutMapping("/{id}")
    public void updateDoctor(@PathVariable int id,
                             @RequestBody MedicalProfessional doctor) {
        doctor.setId(id);
        doctorDAO.update(doctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable int id) {
        doctorDAO.delete(id);
    }
}
