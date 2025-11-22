<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Người Dùng</title>
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
    <nav>
        <div class="LTM Project">Logo</div>
        <div>
            <a href="#home">Trang Chủ</a>
            <a href="#problems">Bài Toán</a>
            <a href="#leaderboard">Bảng Xếp Hạng</a>
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
    <form action="Home" method="get" id="filterForm" style="display: flex; gap: 15px; width: 100%;">

        <select name="difficulty" onchange="document.getElementById('filterForm').submit()">
            <option value="">Độ khó (Tất cả)</option>
            <option value="Easy" ${paramDifficulty == 'Easy' ? 'selected' : ''}>Dễ</option>
            <option value="Medium" ${paramDifficulty == 'Medium' ? 'selected' : ''}>Trung Bình</option>
            <option value="Hard" ${paramDifficulty == 'Hard' ? 'selected' : ''}>Khó</option>
        </select>

        <select name="tags" onchange="document.getElementById('filterForm').submit()">
              <option value="">Thẻ (Tất cả)</option>
              <c:forEach items="${availableTags}" var="t">
                  <option value="${t}" ${paramTags == t ? 'selected' : ''}>
                      ${t}
                  </option>
              </c:forEach>
          </select>
        <select name="status" onchange="document.getElementById('filterForm').submit()">
            <option value="">Trạng thái (Tất cả)</option>
            <option value="solved" ${paramStatus == 'solved' ? 'selected' : ''}>Đã Giải (Accepted)</option>
            <option value="unsolved" ${paramStatus == 'unsolved' ? 'selected' : ''}>Chưa Giải</option>
        </select>

        <button type="submit" class="btn" style="padding: 5px 15px;">Lọc</button>
    </form>
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
