<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài toán | ${problem.title}</title>
    <link rel="stylesheet" href="styles/detailStyle.css">
</head>
<body>

<nav class="top-nav">
    <div class="logo">LTM Judge</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/Home">Trang Chủ</a>
        <a href="#">Bài Toán</a>
        <a href="#">Bảng Xếp Hạng</a>
        <a href="#">Giới Thiệu</a>
        <a href="#">${currentUser.username}</a>
        <a href="${pageContext.request.contextPath}/Logout">Đăng Xuất</a>
    </div>
</nav>

<main class="page-wrapper">
    <div class="page-header">
        <a href="${pageContext.request.contextPath}/Home" class="btn btn-secondary btn-back">
            ← Quay lại danh sách
        </a>
        <div>
            <h1 class="problem-title">${problem.title}</h1>
            <div class="problem-meta">
                <span class="badge difficulty-${problem.difficulty}">Độ khó: ${problem.difficulty}</span>
                <span class="badge ac-rate">Tỉ lệ AC: ${problem.acRate}%</span>
                <span class="badge problem-id">ID: ${problem.id}</span>
            </div>
        </div>
    </div>

    <div class="problem-layout">
        <!-- PANEL TRÁI: NỘP BÀI -->
        <section class="left-panel">
            <div class="card">
                <h3>Nộp code Java</h3>

                <c:if test="${param.submitted == '1'}">
                    <p class="alert success">Đã nhận bài nộp. Kết quả sẽ được cập nhật sau khi chấm.</p>
                </c:if>

                <form action="${pageContext.request.contextPath}/SubmitCode" method="post" class="submit-form">
                    <input type="hidden" name="problemId" value="${problem.id}"/>

                    <label for="code">Code Java của bạn:</label>
                    <textarea id="code" name="code" rows="22" required>
<c:if test="${not empty lastSubmission}">${lastSubmission.code}</c:if>
                    </textarea>

                    <button type="submit" class="btn btn-primary">Nộp bài</button>
                </form>
            </div>
        </section>

        <!-- PANEL PHẢI: MÔ TẢ + KẾT QUẢ -->
        <section class="right-panel">

            <div class="card problem-desc-card">
                <h3>Mô tả bài toán</h3>
                <pre class="problem-description"><c:out value="${problem.description}"/></pre>
            </div>

            <div class="card">
                <h3>Kết quả gần nhất của bạn</h3>
                <c:choose>
                    <c:when test="${not empty lastSubmission}">
                        <p><b>Điểm:</b> ${lastSubmission.score}</p>
                        <p><b>Trạng thái:</b> ${lastSubmission.status}</p>

                        <c:if test="${not empty lastSubmission.output}">
                            <details class="detail-block">
                                <summary>Output</summary>
                                <pre>${lastSubmission.output}</pre>
                            </details>
                        </c:if>

                        <c:if test="${not empty lastSubmission.error}">
                            <details class="detail-block error-block">
                                <summary>Lỗi</summary>
                                <pre>${lastSubmission.error}</pre>
                            </details>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <p>Bạn chưa nộp bài cho bài toán này.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </div>

</main>

<footer class="footer">
    <p>&copy; 2025 | LTM Judge | Chính sách bảo mật | Điều khoản sử dụng</p>
</footer>

</body>
</html>
