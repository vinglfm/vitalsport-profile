CREATE DATABASE vitalsport;
CREATE USER 'vitalsport'@'localhost' IDENTIFIED BY 'vitalsport';
GRANT ALL PRIVILEGES ON vitalsport.* TO 'vitalsport'@'localhost';