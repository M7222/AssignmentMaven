package org.example.healthcare.controller;

import org.example.healthcare.dao.HospitalDAO;
import org.example.healthcare.model.Hospital;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalDAO hospitalDAO;

    public HospitalController(HospitalDAO hospitalDAO) {
        this.hospitalDAO = hospitalDAO;
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalDAO.getAll();
    }

    @PostMapping
    public void addHospital(@RequestBody Hospital hospital) {
        hospitalDAO.save(hospital);
    }

    @PutMapping("/{id}")
    public void updateHospital(@PathVariable int id,
                               @RequestBody Hospital hospital) {
        hospital.setId(id);
        hospitalDAO.update(hospital);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable int id) {
        hospitalDAO.delete(id);
    }
}
