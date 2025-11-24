DROP database LTM_Project;
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

ALTER TABLE Problems
ADD COLUMN title VARCHAR(255) NOT NULL,
ADD COLUMN difficulty ENUM('Easy', 'Medium', 'Hard') DEFAULT 'Easy',
ADD COLUMN ac_rate DECIMAL(5,2) DEFAULT 0.00;

-- Thêm dữ liệu mẫu phù hợp
INSERT INTO Problems (title, description, difficulty, ac_rate)
VALUES ('Kiểm tra số nguyên tố', 'Viết chương trình Java kiểm tra một số nguyên dương n có phải là số nguyên tố hay không. Chương trình đọc một số n từ input và in "YES" nếu n là số nguyên tố, "NO" nếu không phải.', 'Easy', 75.5);

INSERT INTO TestCases (problem_id, input, expected_output)
VALUES  (1, '2', 'YES'),
		(1, '4', 'NO'),
        (1, '17', 'YES');

ALTER TABLE Problems ADD COLUMN tags VARCHAR(100) DEFAULT 'Basic';

-- Tăng giới hạn gói tin lên 64MB (đủ cho bài toán này)
SET GLOBAL max_allowed_packet = 67108864;

USE LTM_Project;
ALTER TABLE TestCases MODIFY input LONGTEXT;
ALTER TABLE TestCases MODIFY expected_output LONGTEXT;

-- Tạo đề bài
INSERT INTO Problems (title, description, difficulty, ac_rate, tags)
VALUES (
    'DDoS Detection: Phân tích Log hệ thống',
    'Hệ thống nhận được một file log chứa hàng trăm ngàn dòng request. Mỗi dòng có định dạng: [TIMESTAMP] [IP_ADDRESS] [URL].\nNhiệm vụ: Tìm IP nào đã gửi nhiều request nhất (nghi vấn DDoS) và in ra số lượng request của IP đó.',
    'Hard',
    0.0,
    'BigData, String Processing'
);

USE LTM_Project;

-- Mở rộng cột output để chứa được log kết quả chấm bài lớn
ALTER TABLE Submissions MODIFY output LONGTEXT;

-- Mở rộng luôn cột error để tránh lỗi tương tự nếu thông báo lỗi quá dài
ALTER TABLE Submissions MODIFY error LONGTEXT;