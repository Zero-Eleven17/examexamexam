package com.teacher.dao;

import com.teacher.model.Teacher;
import com.teacher.util.DatabaseUtil;

import java.sql.*;

public class TeacherDAO {
    
    public Teacher findByUsername(String username) {
        String sql = "SELECT * FROM teachers WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getLong("id"));
                teacher.setTeacherName(rs.getString("teacher_name"));
                teacher.setSchool(rs.getString("school"));
                teacher.setSubject(rs.getString("subject"));
                teacher.setPhone(rs.getString("phone"));
                teacher.setClassName(rs.getString("class"));
                teacher.setUsername(rs.getString("username"));
                teacher.setPassword(rs.getString("password"));
                teacher.setCreatedAt(rs.getTimestamp("created_at"));
                teacher.setUpdatedAt(rs.getTimestamp("updated_at"));
                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean register(Teacher teacher) {
        String sql = "INSERT INTO teachers (teacher_name, school, subject, phone, class, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, teacher.getTeacherName());
            stmt.setString(2, teacher.getSchool());
            stmt.setString(3, teacher.getSubject());
            stmt.setString(4, teacher.getPhone());
            stmt.setString(5, teacher.getClassName());
            stmt.setString(6, teacher.getUsername());
            stmt.setString(7, teacher.getPassword());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM teachers WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 