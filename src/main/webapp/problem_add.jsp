<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm bài tập mới - Admin</title>
    <link rel="stylesheet" href="styles/addProblemStyles.css">
</head>
<body>

<div class="navbar">
    <div>
        <strong>LTM Project – Admin</strong>
    </div>
    <div>
        Xin chào,
        <strong><c:out value="${sessionScope.username}" /></strong>
        |
        <a href="admin">Quay lại Dashboard</a>
        |
        <a href="${pageContext.request.contextPath}/Logout">Đăng xuất</a>
    </div>
</div>

<div class="container">
    <h1>Thêm bài tập mới</h1>

    <div class="section">
        <div class="section-title">Thông tin bài tập</div>

        <form method="post" action="CreateProblem">
            <div class="form-group">
                <label>Tiêu đề:</label>
                <input type="text" name="title" required placeholder="VD: Kiểm tra số nguyên tố">
            </div>

            <div class="form-group">
                <label>Độ khó:</label>
                <select name="difficulty">
                    <option value="Easy">Easy</option>
                    <option value="Medium">Medium</option>
                    <option value="Hard">Hard</option>
                </select>
            </div>

            <div class="form-group">
                <label>AC Rate (%):</label>
                <input type="number" step="0.01" name="acRate" placeholder="VD: 75.50">
                <div class="text-muted mt-1">Có thể để trống, mặc định = 0</div>
            </div>

            <div class="form-group">
                <label>Mô tả:</label>
                <textarea name="description" rows="6"
                          placeholder="Mô tả bài toán, yêu cầu input/output, ví dụ..."></textarea>
            </div>

            <button type="submit" class="btn">Tạo bài tập</button>
        </form>
    </div>
</div>

</body>
</html>
