package HospitalManagement;

import java.sql.*;
import java.util.Scanner;

public class Driver {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String user = "root";
    private static final String pass = "8287685215";

    public static void main(String[] args) {
        // Load the MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        Scanner sc = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            Patient patient = new Patient(con, sc);
            Doctor doctor = new Doctor(con);

            while (true) {
                System.out.println("\nHOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                // Input validation for menu choice
                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine(); // Consume the leftover newline

                    switch (choice) {
                        case 1:
                            patient.addPatient();
                            break;
                        case 2:
                            patient.viewPatient();
                            break;
                        case 3:
                            doctor.viewDoctor();
                            break;
                        case 4:
                            bookAppointment(con, sc, doctor, patient);
                            break;
                        case 5:
                            System.out.println("Exiting... Goodbye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // Clear invalid input
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static void bookAppointment(Connection con, Scanner sc, Doctor doctor, Patient patient) {
        System.out.print("Enter Patient ID: ");
        int patientID = getIntInput(sc);

        System.out.print("Enter Doctor ID: ");
        int doctorID = getIntInput(sc);

        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = sc.nextLine();

        if (patient.checkPatient(patientID) && doctor.checkDoctor(doctorID)) {
            if (checkDoctorAvailable(doctorID, appointmentDate, con)) {
                String appointmentQuery = "INSERT INTO Appointments(PatientID, DoctorID, AppointmentDate) VALUES (?,?,?)";

                try (PreparedStatement ps = con.prepareStatement(appointmentQuery)) {
                    ps.setInt(1, patientID);
                    ps.setInt(2, doctorID);
                    ps.setString(3, appointmentDate);

                    int count = ps.executeUpdate();
                    if (count > 0) {
                        System.out.println("Appointment Booked Successfully");
                    } else {
                        System.out.println("Appointment Booking Failed");
                    }
                } catch (SQLException e) {
                    System.out.println("Error booking appointment: " + e.getMessage());
                }
            } else {
                System.out.println("Doctor is not available for the desired date");
            }
        } else {
            System.out.println("Patient ID or Doctor ID does not match");
        }
    }

    public static boolean checkDoctorAvailable(int doctorID, String appointmentDate, Connection con) {
        String query = "SELECT COUNT(*) FROM Appointments WHERE DoctorID = ? AND AppointmentDate = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, doctorID);
            ps.setString(2, appointmentDate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking doctor availability: " + e.getMessage());
        }
        return false;
    }

    // Helper method for validated integer input
    public static int getIntInput(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                sc.nextLine(); // Consume the newline
                return input;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear invalid input
            }
        }
    }
}
