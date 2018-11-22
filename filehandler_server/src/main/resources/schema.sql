CREATE DATABASE IF NOT EXISTS filehandler;
USE filehandler;

CREATE TABLE IF NOT EXISTS `user` (
    name VARCHAR(30) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `file` (
    name VARCHAR(50) PRIMARY KEY,
    size INTEGER NOT NULL,
    writeable BOOLEAN NOT NULL,
    owner VARCHAR(30) NOT NULL,

    FOREIGN KEY (owner) REFERENCES `user`(name)
    );

--CREATE USER IF NOT EXISTS 'filehandler'@'%' IDENTIFIED BY 'filehandler';
--GRANT ALL PRIVILEGES ON * . * TO 'filehandler'@'%';
