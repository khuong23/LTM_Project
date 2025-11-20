<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Người Dùng</title>
    <style>
        /* Cấu hình chung cho trang */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #0e0e1d;
            color: white;
        }

        /* Thanh điều hướng */
        nav {
            background-color: #1e2a3a;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav .logo {
            color: #00aaff;
            font-size: 1.5em;
        }

        nav a {
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            font-size: 1.1em;
            transition: background-color 0.3s;
        }

        nav a:hover {
            background-color: #00aaff;
        }

        /* Phần chào mừng và tổng quan tiến độ */
        .welcome {
            padding: 20px;
            background-color: #1e2a3a;
            text-align: center;
        }

        .welcome h2 {
            margin: 0;
            font-size: 1.8em;
        }

        .progress-overview {
            margin-top: 10px;
            font-size: 1.2em;
        }

        .progress-overview span {
            color: #00aaff;
        }

        /* Phần các bài toán đang làm dở */
        .in-progress {
            padding: 20px;
            background-color: #2a3a54;
        }

        .in-progress h3 {
            margin-top: 0;
            font-size: 1.5em;
            color: #00aaff;
        }

        .in-progress ul {
            list-style: none;
            padding: 0;
        }

        .in-progress ul li {
            background-color: #1e2a3a;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
        }

        .in-progress ul li a {
            color: #00aaff;
            text-decoration: none;
        }

        .in-progress ul li a:hover {
            text-decoration: underline;
        }

        /* Phần danh sách bài toán */
        .problem-list {
            padding: 20px;
            background-color: #2a3a54;
        }

        .problem-list h3 {
            font-size: 1.5em;
            color: #00aaff;
        }

        .filter {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .filter select {
            padding: 10px;
            font-size: 1em;
            color: #333;
            border: none;
            border-radius: 5px;
        }

        .problem-table {
            width: 100%;
            border-collapse: collapse;
        }

        .problem-table th, .problem-table td {
            padding: 12px;
            text-align: left;
            border: 1px solid #333;
        }

        .problem-table th {
            background-color: #1e2a3a;
        }

        .problem-table tr:nth-child(even) {
            background-color: #2a3a54;
        }

        .problem-table a {
            color: #00aaff;
            text-decoration: none;
        }

        .problem-table a:hover {
            text-decoration: underline;
        }

        /* Footer */
        footer {
            background-color: #1e2a3a;
            padding: 10px;
            text-align: center;
            color: #d1d1d1;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <!-- Thanh điều hướng -->
    <nav>
        <div class="logo">Logo</div>
        <div>
            <a href="#home">Trang Chủ</a>
            <a href="#problems">Bài Toán</a>
            <a href="#leaderboard">Bảng Xếp Hạng</a>
            <a href="#about">Giới Thiệu</a>
            <a href="#profile">${username}</a>
            <a href="#logout">Đăng Xuất</a>
        </div>
    </nav>

    <!-- Chào mừng và tổng quan tiến độ -->
    <div class="welcome">
        <h2>Chào mừng, ${currentUser.username}!</h2>
        <div class="progress-overview">
            <p>[Tổng quan tiến độ: <span>X bài đã giải</span>, <span>Y điểm</span>, <span>Rank Z</span>]</p>
        </div>
    </div>

    <!-- Các bài toán đang làm dở -->
    <div class="in-progress">
        <h3>Các Bài Toán Đang Làm Dở / Gần Đây</h3>
        <ul>
            <li><a href="#problem1">Bài A</a></li>
            <li><a href="#problem2">Bài B</a></li>
        </ul>
    </div>

    <!-- Danh sách các bài toán -->
    <div class="problem-list">
        <h3>Danh Sách Các Bài Toán</h3>

        <!-- Bộ lọc -->
        <div class="filter">
            <select>
                <option value="difficulty">Độ khó</option>
                <option value="easy">Dễ</option>
                <option value="medium">Trung Bình</option>
                <option value="hard">Khó</option>
            </select>
            <select>
                <option value="tags">Thẻ</option>
                <option value="math">Toán Học</option>
                <option value="dp">Lập Trình Động</option>
            </select>
            <select>
                <option value="status">Trạng thái</option>
                <option value="solved">Đã Giải</option>
                <option value="unsolved">Chưa Giải</option>
            </select>
        </div>

        <!-- Bảng danh sách bài toán -->
        <table class="problem-table">
            <thead>
                <tr>
                    <th>Tên Bài Toán</th>
                    <th>Độ Khó</th>
                    <th>Tỷ Lệ AC</th>
                    <th>Giải Quyết</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${problemList}" var="p">
                    <tr>
                        <td>${p.title}</td>
                        <td>${p.difficulty}</td>
                        <td>${p.acRate}%</td>
                        <td><a href="ProblemDetail?id=${p.id}">Giải Quyết</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2025 | Chính Sách Bảo Mật | Điều Khoản Sử Dụng</p>
    </footer>

</body>
</html>
