CREATE DATABASE IF NOT EXISTS LTM_Project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE LTM_Project;

CREATE TABLE Users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user'
);

CREATE TABLE Problems(
    problem_id INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT
);

CREATE TABLE Submissions(
    submit_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    problem_id INT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    code TEXT,
    status ENUM('PENDING','PROCESSING','ACCEPTED','WRONG_ANSWER','COMPILE_ERROR','RUNTIME_ERROR','TIME_LIMIT') DEFAULT 'PENDING',
    score INT DEFAULT 0,
    output TEXT,
    error TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (problem_id) REFERENCES Problems(problem_id)
);

CREATE TABLE TestCases(
    test_case_id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT NOT NULL,
    input TEXT,
    expected_output TEXT,
    FOREIGN KEY (problem_id) REFERENCES Problems(problem_id)
);

INSERT INTO Users (username, password, role) 
VALUES	('admin', '123', 'admin'),
		('baokhuong', '123', 'user'),
		('nguyenlong', '123', 'user'),
		('baominh', '123', 'user');
INSERT INTO Problems (description)
VALUES	('Viết chương trình Java kiểm tra một số nguyên dương n có phải là số nguyên tố hay không. Chương trình đọc một số n từ input và in "YES" nếu n là số nguyên tố, "NO" nếu không phải.');

INSERT INTO TestCases (problem_id, input, expected_output)
VALUES  (1, '2', 'YES'),
		(1, '4', 'NO'),
        (1, '17', 'YES');



