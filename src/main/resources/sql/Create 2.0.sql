DROP DATABASE timestar;

CREATE DATABASE timestar;

USE timestar;

CREATE TABLE teacher ( 
id INT NOT NULL AUTO_INCREMENT, 
name VARCHAR(30) NOT NULL, 
surname VARCHAR(30) NOT NULL, 
phone VARCHAR(30) NOT NULL, 
comment_about VARCHAR(500) NOT NULL, 
PRIMARY KEY(id));

CREATE TABLE languages ( 
teacher_id INT NOT NULL, 
code VARCHAR(3) NOT NULL, 
FOREIGN KEY(teacher_id) REFERENCES teacher(id), 
UNIQUE KEY (teacher_id, code));

CREATE TABLE customer ( 
id INT NOT NULL AUTO_INCREMENT, 
name VARCHAR(30) NOT NULL, 
phone VARCHAR(30) NOT NULL, 
comment_about VARCHAR(500) NOT NULL, 
PRIMARY KEY(id));

CREATE TABLE student_group ( 
id INT NOT NULL AUTO_INCREMENT, 
customer_id INT NOT NULL, 
name VARCHAR(30) NOT NULL, 
PRIMARY KEY(id), 
FOREIGN KEY(customer_id) REFERENCES customer(id));

CREATE TABLE student ( 
id INT NOT NULL AUTO_INCREMENT, 
group_id INT NOT NULL, 
name VARCHAR(60) NOT NULL, 
PRIMARY KEY(id), 
FOREIGN KEY(group_id) REFERENCES student_group(id));

CREATE TABLE contract ( 
id INT NOT NULL AUTO_INCREMENT, 
group_id INT NOT NULL, 
payment_day TINYINT NOT NULL, 
start_date DATE NOT NULL, 
language_level VARCHAR(20) NOT NULL, 
payment_value DECIMAL(19, 4) NOT NULL, 
PRIMARY KEY(id), 
FOREIGN KEY(group_id) REFERENCES student_group(id));

CREATE TABLE lesson ( 
id BIGINT NOT NULL AUTO_INCREMENT, 
teacher_id INT NOT NULL, 
customer_id INT NOT NULL, 
group_id INT NOT NULL, 
date_of_lesson DATE NOT NULL, 
time_of_lesson SMALLINT NOT NULL, 
length_in_minutes SMALLINT NOT NULL, 
comment_about VARCHAR(500) NOT NULL, 
PRIMARY KEY(id), 
FOREIGN KEY(teacher_id) REFERENCES teacher(id), 
FOREIGN KEY(customer_id) REFERENCES customer(id), 
FOREIGN KEY(group_id) REFERENCES student_group(id));

CREATE TABLE attendance ( 
lesson_id BIGINT NOT NULL, 
student_id INT NOT NULL, 
FOREIGN KEY(lesson_id) REFERENCES lesson(id), 
FOREIGN KEY(student_id) REFERENCES student(id), 
UNIQUE KEY (lesson_id, student_id));