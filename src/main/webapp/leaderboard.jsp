<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Bảng Xếp Hạng</title>
            <link rel="stylesheet" href="styles/styles.css">
            <style>
                .leaderboard-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                .leaderboard-table th,
                .leaderboard-table td {
                    border: 1px solid #333;
                    padding: 12px;
                    text-align: center;
                }

                .leaderboard-table th {
                    background-color: #1e2a3a;
                    color: #00aaff;
                }

                .leaderboard-table tr:nth-child(even) {
                    background-color: #2a3a54;
                }

                .leaderboard-table tr:hover {
                    background-color: #1e2a3a;
                }
            </style>
        </head>

        <body>
            <nav>
                <div class="LTM Project">Logo</div>
                <div>
                    <a href="Home">Trang Chủ</a>
                    <a href="Home#problems">Bài Toán</a>
                    <a href="Leaderboard" class="active">Bảng Xếp Hạng</a>
                    <a href="Home#profile">${sessionScope.currentUser.username}</a>
                    <a href="${pageContext.request.contextPath}/Logout">Đăng Xuất</a>
                </div>
            </nav>

            <div class="welcome">
                <h2>Bảng Xếp Hạng</h2>
            </div>

            <div class="problem-list">
                <table class="leaderboard-table">
                    <thead>
                        <tr>
                            <th>Hạng</th>
                            <th>Người Dùng</th>
                            <th>Tổng Điểm</th>
                            <th>Số Bài Đã Giải</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${leaderboard}" var="entry">
                            <tr>
                                <td>${entry.rank}</td>
                                <td>${entry.username}</td>
                                <td>${entry.totalScore}</td>
                                <td>${entry.solvedCount}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty leaderboard}">
                            <tr>
                                <td colspan="4">Chưa có dữ liệu xếp hạng.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <footer>
                <p>&copy; 2025 | Chính Sách Bảo Mật | Điều Khoản Sử Dụng</p>
            </footer>

        </body>

        </html>