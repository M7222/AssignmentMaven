package org.example.healthcare.model;

import java.util.Arrays;
import java.util.Objects;

public class Hospital {
    private int id;
    private String name;
    private String address;
    private String headDoctor;
    private String[] departments;

    public Hospital(int id, String address, String headDoctor, String[] departments, String name) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.headDoctor = headDoctor;
        this.departments = departments;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getHeadDoctor() { return headDoctor; }
    public String[] getDepartments() { return departments; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setHeadDoctor(String headDoctor) { this.headDoctor = headDoctor; }
    public void setDepartments(String[] departments) { this.departments = departments; }

    @Override
    public String toString() {
        return "main.java.com.example.healthcare.model.Hospital{id=" + id + ", name='" + name + "', address='" + address +
                "', headDoctor='" + headDoctor + "', departments=" + Arrays.toString(departments) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hospital)) return false;
        Hospital h = (Hospital) o;
        return id == h.id && Objects.equals(name, h.name) &&
                Objects.equals(address, h.address) &&
                Objects.equals(headDoctor, h.headDoctor) &&
                Arrays.equals(departments, h.departments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, address, headDoctor);
        result = 31 * result + Arrays.hashCode(departments);
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private int id;
        private String address;
        private String headDoctor;
        private String[] departments;
        private String name;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder headDoctor(String headDoctor) {
            this.headDoctor = headDoctor;
            return this;
        }

        public Builder departments(String[] departments) {
            this.departments = departments;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Hospital build() {
            return new Hospital(id, address, headDoctor, departments, name);
        }
    }

}