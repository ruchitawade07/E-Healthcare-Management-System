CREATE DATABASE IF NOT EXISTS ehealthcare;
USE ehealthcare;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    contact VARCHAR(20),
    disease VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    doctor_name VARCHAR(100),
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Insert default login
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
