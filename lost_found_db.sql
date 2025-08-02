CREATE DATABASE lost_found_db;
USE lost_found_db;

CREATE TABLE lost_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    description TEXT,
    date_lost DATE
);

CREATE TABLE found_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    description TEXT,
    date_found DATE
);