package org.example.healthcare.model;


import java.util.Objects;

public class MedicalProfessional {
    private int id;
    private String name;
    private int age;
    private String specialization;

    public MedicalProfessional(int id, String name, int age, String specialization){
        this.id = id;
        this.name = name;
        this.age = age;
        this.specialization = specialization;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getSpecialization() { return specialization; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", name='" + name + "', age=" + age + ", spec='" + specialization + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalProfessional)) return false;
        MedicalProfessional doc = (MedicalProfessional) o;
        return id == doc.id &&
                age == doc.age &&
                Objects.equals(name, doc.name) &&
                Objects.equals(specialization, doc.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, specialization);
    }

}