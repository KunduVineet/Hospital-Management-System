# Hospital Management System

This is a simple console-based Hospital Management System built using Java and MySQL. It allows the user to manage patients, view doctors, and book appointments. It demonstrates basic CRUD operations and JDBC usage.

---

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Project Structure](#project-structure)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Classes Overview](#classes-overview)
- [Dependencies](#dependencies)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Features
- Add new patients
- View existing patients
- View list of doctors
- Book an appointment with a doctor
- Input validation and error handling

---

## Prerequisites
- **Java Development Kit (JDK) 8 or above**
- **MySQL Server and MySQL Workbench**
- **IDE (e.g., IntelliJ, Eclipse, or VSCode with Java extensions)**
- **MySQL Connector/J** (JDBC Driver for MySQL)

---

## Database Setup
1. **Create Database**  
Open MySQL Workbench and run:
```sql
CREATE DATABASE hospital;
USE hospital;
```

2. **Create Tables**  
Execute the following queries to create necessary tables:
```sql
CREATE TABLE patient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10)
);

CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    specialization VARCHAR(50)
);

CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT,
    DoctorID INT,
    AppointmentDate DATE,
    FOREIGN KEY (PatientID) REFERENCES patient(id),
    FOREIGN KEY (DoctorID) REFERENCES doctors(id)
);
```

3. **Insert Sample Data (Optional)**
```sql
INSERT INTO doctors (name, specialization) VALUES 
('Dr. John Doe', 'Cardiologist'),
('Dr. Jane Smith', 'Dermatologist'),
('Dr. Alice Johnson', 'Pediatrician');
```

---

## Project Structure
```
HospitalManagement/
│   Driver.java
│   Patient.java
│   Doctor.java
│   README.md
└───lib/
        mysql-connector-java-x.x.x.jar
```

---

## Installation and Setup
1. **Clone the Repository**
```bash
git clone <repository_url>
cd HospitalManagement
```

2. **Add MySQL Connector/J JAR**  
Download [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) and place the JAR file in the `lib` folder.

3. **Configure Database Credentials**  
Update the following variables in `Driver.java`:
```java
private static final String url = "jdbc:mysql://localhost:3306/hospital";
private static final String user = "root";
private static final String pass = "your_password_here";
```

4. **Compile and Run**  
```bash
javac -cp "lib/mysql-connector-java-x.x.x.jar" HospitalManagement/*.java
java -cp ".;lib/mysql-connector-java-x.x.x.jar" HospitalManagement.Driver
```

---

## Usage
1. **Main Menu**  
```
HOSPITAL MANAGEMENT SYSTEM
1. Add Patient
2. View Patient
3. View Doctors
4. Book Appointment
5. Exit
Enter your choice:
```

2. **Add Patient**  
- Enter patient name, age, and gender.

3. **View Patient**  
- Displays all registered patients.

4. **View Doctors**  
- Displays a list of all available doctors.

5. **Book Appointment**  
- Enter Patient ID, Doctor ID, and Appointment Date (YYYY-MM-DD format).
- Checks for doctor's availability on the specified date.

6. **Exit**  
- Terminates the program.

---

## Classes Overview

### 1. `Driver.java`
- Entry point of the application.
- Manages the main menu and user input.
- Establishes database connection using JDBC.
- Calls methods from `Patient` and `Doctor` classes for CRUD operations.

### 2. `Patient.java`
- Handles patient-related operations:
  - `addPatient()` - Adds a new patient.
  - `viewPatient()` - Displays all patients.
  - `checkPatient(int id)` - Checks if a patient exists by ID.

### 3. `Doctor.java`
- Handles doctor-related operations:
  - `viewDoctor()` - Displays all doctors.
  - `checkDoctor(int id)` - Checks if a doctor exists by ID.

### 4. `Database Design`
- `patient`: Stores patient details (id, name, age, gender).
- `doctors`: Stores doctor details (id, name, specialization).
- `appointments`: Stores appointment details (PatientID, DoctorID, AppointmentDate).

---

## Dependencies
- **Java 8 or higher**
- **MySQL Connector/J** - JDBC driver for connecting Java to MySQL.

---

## Troubleshooting
- **Error: MySQL Driver not found**  
  Ensure the MySQL Connector/J JAR is correctly placed in the `lib` folder and added to the classpath.

- **Database Connection Failed**  
  - Check if MySQL server is running.
  - Verify database credentials in `Driver.java`.

- **Invalid Input**  
  - Ensure to enter numeric values where required.
  - Follow date format as `YYYY-MM-DD`.

---

## License
This project is licensed under the MIT License. Feel free to use and modify it as per your needs.

---

## Contribution
Contributions are welcome! Feel free to submit a pull request or raise an issue for any bug fixes or feature enhancements.

---

## Acknowledgments
- Java Documentation: [https://docs.oracle.com/javase/8/docs/](https://docs.oracle.com/javase/8/docs/)
- MySQL Documentation: [https://dev.mysql.com/doc/](https://dev.mysql.com/doc/)
- JDBC Guide: [https://docs.oracle.com/javase/tutorial/jdbc/](https://docs.oracle.com/javase/tutorial/jdbc/)

---

## Contact
For any questions or support, feel free to reach out.

---
