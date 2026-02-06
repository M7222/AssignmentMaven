package org.example.healthcare.dao;

import org.example.healthcare.model.Patient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

import org.example.healthcare.repository.CrudRepository;
import java.util.List;


@Repository
public class PatientDAO implements CrudRepository<Patient, Integer> {

    private final DataSource dataSource;

    public PatientDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Patient p) {
        String sql = "INSERT INTO patient (id, name, surname, age) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, p.getId());
            pstmt.setString(2, p.getName());
            pstmt.setString(3, p.getSurname());
            pstmt.setInt(4, p.getAge());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Patient> findAllInternal() {
        ArrayList<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public void update(Patient p) {
        String sql = "UPDATE patient SET name = ?, surname = ?, age = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getSurname());
            pstmt.setInt(3, p.getAge());
            pstmt.setInt(4, p.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient create(Patient entity) {
        save(entity);
        return entity;
    }

    @Override
    public Patient getById(Integer id) {
        String sql = "SELECT * FROM patient WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> getAll() {
        return new ArrayList<>(findAllInternal());
    }


    @Override
    public Patient update(Integer id, Patient entity) {
        entity.setId(id);
        update(entity);
        return entity;
    }

    @Override
    public boolean delete(Integer id) {
        delete(id.intValue());
        return true;
    }


}
