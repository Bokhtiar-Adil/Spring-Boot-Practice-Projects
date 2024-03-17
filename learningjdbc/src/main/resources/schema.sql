--DROP DATABASE IF EXISTS `Learning_JDBC`;
--CREATE DATABASE `Learning_JDBC`;
--USE `Learning_JDBC`;

--DROP TABLE IF EXISTS 'book'
--DROP TABLE IF EXISTS 'author'

CREATE TABLE author (
    id INT NOT NULL,
    name VARCHAR(100),
    age INT,
    PRIMARY KEY (id)
);

CREATE TABLE book (
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(100),
    author_id INT,
    PRIMARY KEY (isbn),
    FOREIGN KEY (author_id) REFERENCES author(id)
);