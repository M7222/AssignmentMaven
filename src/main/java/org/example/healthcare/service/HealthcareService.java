package org.example.healthcare.service;

import org.example.healthcare.dao.HospitalDAO;
import org.example.healthcare.dao.MedicalProfessionalDAO;
import org.example.healthcare.dao.PatientDAO;
import org.example.healthcare.model.Hospital;
import org.example.healthcare.model.MedicalProfessional;
import org.example.healthcare.model.Patient;
import org.springframework.stereotype.Service;
import org.example.healthcare.repository.CrudRepository;
import org.example.healthcare.exception.ValidationException;
import org.example.healthcare.exception.NotFoundException;
import java.util.stream.Collectors;
import java.util.Comparator;
import org.example.healthcare.pool.InMemoryDataPool;
import org.example.healthcare.factory.EntityFactory;




import java.util.ArrayList;

@Service
public class HealthcareService {

    private final CrudRepository<MedicalProfessional, Integer> doctorRepository;

    private final CrudRepository<Hospital, Integer> hospitalRepository;

    private final CrudRepository<Patient, Integer> patientRepository;

    public HealthcareService(CrudRepository<Patient, Integer> patientRepository,
                             CrudRepository<MedicalProfessional, Integer> doctorRepository,
                             CrudRepository<Hospital, Integer> hospitalRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }




    public void addPatient(Patient p) {

        patientRepository.requireNonNull(p, "Patient cannot be null");

        if (p == null) {
            throw new ValidationException("Patient cannot be null");
        }
        if (p.getId() <= 0) {
            throw new ValidationException("Patient id must be > 0");
        }
        if (p.getName() == null || p.getName().isBlank()) {
            throw new ValidationException("Patient name is required");
        }
        if (p.getSurname() == null || p.getSurname().isBlank()) {
            throw new ValidationException("Patient surname is required");
        }
        if (p.getAge() < 0 || p.getAge() > 150) {
            throw new ValidationException("Patient age must be between 0 and 150");
        }

        patientRepository.create(p);
    }


    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(patientRepository.getAll());
    }


    public ArrayList<Patient> getMinorPatients() {
        return patientRepository.getAll().stream()
                .filter(p -> p.getAge() < 18)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Patient getPatientById(int id) {
        Patient p = patientRepository.getById(id);
        if (p == null) {
            throw new NotFoundException("Patient with id=" + id + " not found");
        }
        return p;
    }

    public void updatePatient(int id, Patient p) {
        if (p == null) {
            throw new ValidationException("Patient cannot be null");
        }
        if (id <= 0) {
            throw new ValidationException("Patient id must be > 0");
        }
        if (p.getName() == null || p.getName().isBlank()) {
            throw new ValidationException("Patient name is required");
        }
        if (p.getSurname() == null || p.getSurname().isBlank()) {
            throw new ValidationException("Patient surname is required");
        }
        if (p.getAge() < 0 || p.getAge() > 150) {
            throw new ValidationException("Patient age must be between 0 and 150");
        }

        // проверим, что существует
        Patient existing = patientRepository.getById(id);
        if (existing == null) {
            throw new NotFoundException("Patient with id=" + id + " not found");
        }

        p.setId(id);
        patientRepository.update(id, p);
    }

    public void deletePatient(int id) {
        if (id <= 0) throw new ValidationException("Patient id must be > 0");

        boolean deleted = patientRepository.delete(id);
        if (!deleted) {
            throw new NotFoundException("Patient with id=" + id + " not found");
        }
    }


    public ArrayList<Patient> getPatientsSortedByAge() {
        return patientRepository.getAll().stream()
                .sorted(Comparator.comparingInt(p -> p.getAge()))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Patient> getMinorPatientsFromPool() {
        InMemoryDataPool<Patient> pool =
                new InMemoryDataPool<>(patientRepository.getAll());

        return new ArrayList<>(
                pool.filter(p -> p.getAge() < 18)
        );
    }




    public void addDoctor(MedicalProfessional m) {
        doctorRepository.create(m);
    }

    public ArrayList<MedicalProfessional> getDoctors() {
        return new ArrayList<>(doctorRepository.getAll());
    }

    public ArrayList<MedicalProfessional> getDoctorsBySpecialization(String spec) {
        ArrayList<MedicalProfessional> result = new ArrayList<>();
        for (MedicalProfessional m : doctorRepository.getAll()) {
            if (m.getSpecialization().equalsIgnoreCase(spec)) {
                result.add(m);
            }
        }
        return result;
    }



    public void addHospital(Hospital h) {
        hospitalRepository.create(h);
    }

    public ArrayList<Hospital> getHospitals() {
        return new ArrayList<>(hospitalRepository.getAll());
    }

    public ArrayList<Hospital> getHospitalsByDepartment(String dept) {
        ArrayList<Hospital> result = new ArrayList<>();
        for (Hospital h : hospitalRepository.getAll()) {
            for (String d : h.getDepartments()) {
                if (d.equalsIgnoreCase(dept)) {
                    result.add(h);
                    break;
                }
            }
        }
        return result;
    }

    public void createAndAddHospitalViaFactory(int id, String address, String headDoctor, String[] departments, String name) {
        Hospital h = EntityFactory.createHospital(id, address, headDoctor, departments, name);
        hospitalRepository.create(h);
    }


}
