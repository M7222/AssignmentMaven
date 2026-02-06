package org.example.healthcare.dao;

import org.example.healthcare.model.Hospital;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

import org.example.healthcare.repository.CrudRepository;
import java.util.List;


@Repository
public class HospitalDAO implements CrudRepository<Hospital, Integer> {


    private final DataSource dataSource;

    public HospitalDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Hospital h) {
        String sql = "INSERT INTO hospital (id, name, address, head_doctor, departments) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, h.getId());
            pstmt.setString(2, h.getName());
            pstmt.setString(3, h.getAddress());
            pstmt.setString(4, h.getHeadDoctor());

            String depts = (h.getDepartments() != null)
                    ? String.join(", ", h.getDepartments())
                    : "";
            pstmt.setString(5, depts);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Hospital> findAllInternal(){
    ArrayList<Hospital> hospitals = new ArrayList<>();
        String sql = "SELECT * FROM hospital";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String raw = rs.getString("departments");
                String[] depts = (raw != null && !raw.isEmpty())
                        ? raw.split(", ")
                        : new String[0];

                hospitals.add(new Hospital(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getString("head_doctor"),
                        depts,
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    public void update(Hospital h) {
        String sql = "UPDATE hospital SET name = ?, address = ?, head_doctor = ?, departments = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, h.getName());
            pstmt.setString(2, h.getAddress());
            pstmt.setString(3, h.getHeadDoctor());

            String depts = (h.getDepartments() != null)
                    ? String.join(", ", h.getDepartments())
                    : "";
            pstmt.setString(4, depts);

            pstmt.setInt(5, h.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM hospital WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Hospital create(Hospital entity) {
        save(entity);
        return entity;
    }

    @Override
    public Hospital getById(Integer id) {
        String sql = "SELECT * FROM hospital WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String raw = rs.getString("departments");
                String[] depts = (raw != null && !raw.isEmpty())
                        ? raw.split(", ")
                        : new String[0];

                return new Hospital(
                        rs.getInt("id"),
                        rs.getString("address"),
                        rs.getString("head_doctor"),
                        depts,
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Hospital> getAll() {
        return new ArrayList<>(findAllInternal());
    }

    @Override
    public Hospital update(Integer id, Hospital entity) {
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
