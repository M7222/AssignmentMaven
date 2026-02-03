package org.example.healthcare.service;

import org.example.healthcare.dao.HospitalDAO;
import org.example.healthcare.dao.MedicalProfessionalDAO;
import org.example.healthcare.dao.PatientDAO;
import org.example.healthcare.model.Hospital;
import org.example.healthcare.model.MedicalProfessional;
import org.example.healthcare.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HealthcareService {

    private final PatientDAO patientDAO;
    private final MedicalProfessionalDAO doctorDAO;
    private final HospitalDAO hospitalDAO;

    public HealthcareService(PatientDAO patientDAO,
                             MedicalProfessionalDAO doctorDAO,
                             HospitalDAO hospitalDAO) {
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;
        this.hospitalDAO = hospitalDAO;
    }

    // -------- PATIENTS --------

    public void addPatient(Patient p) {
        patientDAO.save(p);
    }

    public ArrayList<Patient> getPatients() {
        return patientDAO.getAll();
    }

    public ArrayList<Patient> getMinorPatients() {
        ArrayList<Patient> minors = new ArrayList<>();
        for (Patient p : patientDAO.getAll()) {
            if (p.getAge() < 18) minors.add(p);
        }
        return minors;
    }

    // -------- DOCTORS --------

    public void addDoctor(MedicalProfessional m) {
        doctorDAO.save(m);
    }

    public ArrayList<MedicalProfessional> getDoctors() {
        return doctorDAO.getAll();
    }

    public ArrayList<MedicalProfessional> getDoctorsBySpecialization(String spec) {
        ArrayList<MedicalProfessional> result = new ArrayList<>();
        for (MedicalProfessional m : doctorDAO.getAll()) {
            if (m.getSpecialization().equalsIgnoreCase(spec)) {
                result.add(m);
            }
        }
        return result;
    }

    // -------- HOSPITALS --------

    public void addHospital(Hospital h) {
        hospitalDAO.save(h);
    }

    public ArrayList<Hospital> getHospitals() {
        return hospitalDAO.getAll();
    }

    public ArrayList<Hospital> getHospitalsByDepartment(String dept) {
        ArrayList<Hospital> result = new ArrayList<>();
        for (Hospital h : hospitalDAO.getAll()) {
            for (String d : h.getDepartments()) {
                if (d.equalsIgnoreCase(dept)) {
                    result.add(h);
                    break;
                }
            }
        }
        return result;
    }
}
