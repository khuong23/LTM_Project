<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Người Dùng</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <nav>
        <div class="logo">Logo</div>
        <div>
            <a href="#home">Trang Chủ</a>
            <a href="#problems">Bài Toán</a>
            <a href="#leaderboard">Bảng Xếp Hạng</a>
            <a href="#about">Giới Thiệu</a>
            <a href="#profile">${username}</a>
            <a href="${pageContext.request.contextPath}/Logout">Đăng Xuất</a>
        </div>
    </nav>

    <div class="welcome">
        <h2>Chào mừng, ${currentUser.username}!</h2>
    </div>

    <div class="problem-list">
        <h3>Danh Sách Các Bài Toán</h3>

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
                        <td><a href="${pageContext.request.contextPath}/ProblemDetail?id=${p.id}">
                            Chi Tiết
                        </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <footer>
        <p>&copy; 2025 | Chính Sách Bảo Mật | Điều Khoản Sử Dụng</p>
    </footer>

</body>
</html>
