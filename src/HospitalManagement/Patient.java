package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter Patient Name");
        String name = scanner.next();

        System.out.println("Enter Patient Age");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline

        System.out.println("Enter Patient Gender");
        String gender = scanner.next();


        try {
            String query = "INSERT INTO patient (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int count = ps.executeUpdate();

            if(count >0){
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Failed to add Patient ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void viewPatient() {
        String query = "SELECT * FROM patient";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patient Details:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.println(id + " " + name + " " + age + " " + gender);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkPatient(int id) {
        String query = "SELECT * FROM patient WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println("Patient Name Exists");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}