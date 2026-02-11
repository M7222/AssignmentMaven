package org.example.healthcare.factory;

import org.example.healthcare.model.Hospital;
import org.example.healthcare.model.MedicalProfessional;
import org.example.healthcare.model.Patient;

public class EntityFactory {

    public static Patient createPatient(int id, String name, String surname, int age) {
        return new Patient(id, name, surname, age);
    }

    public static MedicalProfessional createDoctor(int id, String name, int age, String specialization) {
        return new MedicalProfessional(id, name, age, specialization);
    }

    public static Hospital createHospital(int id, String address, String headDoctor, String[] departments, String name) {
        return Hospital.builder()
                .id(id)
                .address(address)
                .headDoctor(headDoctor)
                .departments(departments)
                .name(name)
                .build();
    }
}
