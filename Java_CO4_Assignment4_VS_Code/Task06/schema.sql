CREATE DATABASE IF NOT EXISTS csa09_events;
USE csa09_events;
CREATE TABLE IF NOT EXISTS registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(30) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_student_event (student_id, event_name)
);
