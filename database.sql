CREATE DATABASE task5;

USE task5;

CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    age INT NOT NULL
);

INSERT INTO users (id, name, address, age) VALUES
(1, 'Do Nguyen', 'Tran Phu', 23),
(2, 'Nam Pham', 'Nguyen Trai', 25),
(3, 'Ha Vu', 'Trieu Khuc', 20);