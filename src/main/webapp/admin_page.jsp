<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - LTM Project</title>
    <link rel="stylesheet" href="styles/adminStyles.css">
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
        <a href="Home">Trang người dùng</a>
        |
        <a href="${pageContext.request.contextPath}/Logout">Đăng xuất</a>
    </div>
</div>

<div class="container">
    <h1>Admin Dashboard</h1>

    <!-- Thống kê -->
    <div class="stats">
        <div class="card">
            <div class="card-title">Tổng số người dùng</div>
            <div class="card-number">${totalUsers}</div>
        </div>
        <div class="card">
            <div class="card-title">Tổng số bài tập</div>
            <div class="card-number">${totalProblems}</div>
        </div>
        <div class="card">
            <div class="card-title">Tổng số submissions</div>
            <div class="card-number">${totalSubmissions}</div>
        </div>
        <div class="card">
            <div class="card-title">Số submission ACCEPTED</div>
            <div class="card-number">${acceptedSubmissions}</div>
        </div>
    </div>

    <!-- Danh sách Problems -->
    <div class="section">
        <div class="section-title">Danh sách bài tập</div>

        <div class="top-actions">
            <a href="CreateProblem" class="btn">+ Add Problems</a>
        </div>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Tiêu đề</th>
                <th>Độ khó</th>
                <th>AC Rate (%)</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${problemList}">
                <tr>
                    <td>${p.problem_id}</td>
                    <td>${p.title}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.difficulty == 'Easy'}">
                                <span class="badge badge-easy">Easy</span>
                            </c:when>
                            <c:when test="${p.difficulty == 'Medium'}">
                                <span class="badge badge-medium">Medium</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-hard">Hard</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${p.acRate}</td>
                    <td>
                        <a class="btn btn-secondary" href="ProblemEdit?problem_id=${p.problem_id}">
                            Edit
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty problemList}">
                <tr>
                    <td colspan="5">Chưa có bài nào.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

    <!-- Submissions gần nhất -->
    <div class="section">
        <div class="section-title">Submissions gần đây</div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Người dùng</th>
                <th>Bài</th>
                <th>Trạng thái</th>
                <th>Điểm</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${recentSubmissions}">
                <tr>
                    <td>${s.submit_id}</td>
                    <td>${submissionUsernames[s.submit_id]}</td>
                    <td>${submissionProblemTitles[s.submit_id]}</td>
                    <td class="status-${s.status}">
                            ${s.status}
                    </td>
                    <td>${s.score}</td>
                </tr>
            </c:forEach>

            <c:if test="${empty recentSubmissions}">
                <tr>
                    <td colspan="5">Chưa có submission nào.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

</div>

</body>
</html>
