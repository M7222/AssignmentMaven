package org.example.healthcare.model;

import java.util.Objects;

public class Patient {
    private int id;
    private String name;
    private String surname;
    private int age;

    public Patient(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getAge() { return age; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "main.java.com.example.healthcare.model.Patient{id=" + id + ", name='" + name + "', surname='" + surname + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                age == patient.age &&
                Objects.equals(name, patient.name) &&
                Objects.equals(surname, patient.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age);
    }
}