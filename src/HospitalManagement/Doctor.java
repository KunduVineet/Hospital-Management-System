package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {

    private final Connection connection;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
    }

    public void viewPatient() {
        String query = "SELECT * FROM doctor";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patient Details:");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specalization");
                System.out.println(id + " " + name + " " + specialization);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean checkDoctor(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
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

