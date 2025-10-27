package com.rmorpoz2909.aad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class JdbcSimpleExample implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(JdbcSimpleExample.class, args);
    }

    @Override
    public void run(String... args) {
        String url = "jdbc:postgresql://localhost:5432/ud2db";
        String user = "postgres";
        String pass = "1234";
        String sqlInsert = "INSERT INTO students(name, course) VALUES (?, ?)";
        String sqlSelect = "SELECT id, name, course FROM students";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sqlInsert);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlSelect)) {
            System.out.println("Connection established successfully.");
            // INSERT operation
            ps.setString(1, "Lucy");
            ps.setString(2, "2nd Year");
            ps.executeUpdate();
            System.out.println("New student inserted successfully.");
            // SELECT operation
            System.out.println("\nList of students:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String course = rs.getString("course");
                System.out.println(id + " - " + name + " (" + course + ")");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
