package org.example.healthcare.dao;

import org.example.healthcare.model.MedicalProfessional;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

import org.example.healthcare.repository.CrudRepository;
import java.util.List;


@Repository
public class MedicalProfessionalDAO implements CrudRepository<MedicalProfessional, Integer> {


    private final DataSource dataSource;

    public MedicalProfessionalDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(MedicalProfessional m) {
        String sql = "INSERT INTO medical_professional (id, name, age, specialization) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, m.getId());
            pstmt.setString(2, m.getName());
            pstmt.setInt(3, m.getAge());
            pstmt.setString(4, m.getSpecialization());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MedicalProfessional> findAllInternal() {

        ArrayList<MedicalProfessional> doctors = new ArrayList<>();
        String sql = "SELECT * FROM medical_professional";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doctors.add(new MedicalProfessional(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public void update(MedicalProfessional m) {
        String sql = "UPDATE medical_professional SET name = ?, age = ?, specialization = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, m.getName());
            pstmt.setInt(2, m.getAge());
            pstmt.setString(3, m.getSpecialization());
            pstmt.setInt(4, m.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM medical_professional WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MedicalProfessional create(MedicalProfessional entity) {
        save(entity);
        return entity;
    }

    @Override
    public MedicalProfessional getById(Integer id) {
        String sql = "SELECT * FROM medical_professional WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new MedicalProfessional(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("specialization")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<MedicalProfessional> getAll() {
        return new ArrayList<>(findAllInternal());
    }


    @Override
    public MedicalProfessional update(Integer id, MedicalProfessional entity) {
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
