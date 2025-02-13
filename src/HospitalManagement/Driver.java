package HospitalManagement;

import java.sql.*;
import java.util.Scanner;

public class Driver {
    private static final String url ="jdbc:mysql://localhost:3306/hospital";
    private static final String user ="root";
    private static final String pass ="8287685215";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Patient patient = new Patient(con,sc);
            Doctor doctor = new Doctor(con);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                    case 4:
                       bookAppointment( con, sc, doctor, patient);
                       System.out.println();
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void bookAppointment(Connection con, Scanner sc, Doctor doctor, Patient patient) {
        System.out.println("Enter Patient ID: ");
        int patientID = sc.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorID = sc.nextInt();
        System.out.println("Enter Appointment Date(YYYY-MM-DD): ");
        String appointmentDate = sc.next();

        if(patient.checkPatient(patientID) && doctor.checkDoctor(doctorID)) {
            if(checkDoctorAvailable(doctorID, appointmentDate, con)){
                String appointment_query = "INSERT INTO Appointments(PatientID, DoctorID, AppointmentDate) VALUES (?,?,?)";
                try {
                    PreparedStatement ps = con.prepareStatement(appointment_query);
                    ps.setInt(1, patientID);
                    ps.setInt(2, doctorID);
                    ps.setString(3, appointmentDate);

                    int count = ps.executeUpdate();
                    if(count >0) {
                        System.out.println("Appointment Booked Successfully");
                    } else {
                        System.out.println("Appointment Booking Failed");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("Doctor is not available for desired date");
            }
        }else {
            System.out.println("Patient ID or Doctor ID does not match");
        }
    }

    public static boolean checkDoctorAvailable(int doctorID, String appointmentDate, Connection con) {
        String query = "SELECT COUNT(*) FROM Appointments WHERE DoctorID = ? AND AppointmentDate = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, doctorID);
            ps.setString(2, appointmentDate);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int count = rs.getInt(1);
                if(count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
