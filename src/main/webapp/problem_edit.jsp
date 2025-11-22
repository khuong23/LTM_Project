<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài tập - Admin</title>
    <link rel="stylesheet" href="styles/problematicStyles.css">
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
    <h1>Chi tiết bài tập #${problem.problem_id}</h1>

    <!-- 1 section lớn chia 2 cột -->
    <div class="section problem-layout">

        <!-- Cột trái: Form cập nhật Problem -->
        <div>
            <div class="section-title">Thông tin bài tập</div>

            <form method="post" action="ProblemEdit">
                <input type="hidden" name="action" value="updateProblem">
                <!-- ĐÚNG: problem_id -->
                <input type="hidden" name="problem_id" value="${problem.problem_id}">

                <div class="form-group">
                    <label>Tiêu đề:</label>
                    <input type="text" name="title" value="${problem.title}" required>
                </div>

                <div class="form-group">
                    <label>Độ khó:</label>
                    <select name="difficulty">
                        <option value="Easy"   ${problem.difficulty == 'Easy'   ? 'selected' : ''}>Easy</option>
                        <option value="Medium" ${problem.difficulty == 'Medium' ? 'selected' : ''}>Medium</option>
                        <option value="Hard"   ${problem.difficulty == 'Hard'   ? 'selected' : ''}>Hard</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>AC Rate (%):</label>
                    <input type="number" step="0.01" name="acRate" value="${problem.acRate}">
                </div>

                <div class="form-group">
                    <label>Mô tả:</label>
                    <textarea name="description" rows="6">${problem.description}</textarea>
                </div>

                <button type="submit" class="btn">Lưu thay đổi bài tập</button>
            </form>
        </div>

        <!-- Cột phải: Quản lý Test Cases -->
        <div>
            <div class="section-title">Test Cases</div>

            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Input</th>
                    <th>Expected Output</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="tc" items="${testCases}">
                    <tr>
                        <!-- 1 form / row, 2 loại action: update / delete -->
                        <form method="post" action="ProblemEdit">
                            <input type="hidden" name="problem_id" value="${problem.problem_id}">
                            <input type="hidden" name="test_case_id" value="${tc.testCaseId}">

                            <td>${tc.testCaseId}</td>
                            <td>
                                <textarea name="input" rows="2">${tc.input}</textarea>
                            </td>
                            <td>
                                <textarea name="expected_output" rows="2">${tc.expectedOutput}</textarea>
                            </td>
                            <td class="testcase-row-actions">
                                <button type="submit" name="action" value="updateTestCase"
                                        class="btn btn-secondary">Cập nhật</button>
                                <button type="submit" name="action" value="deleteTestCase"
                                        class="btn btn-danger"
                                        onclick="return confirm('Xóa test case này?');">Xóa</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>

                <c:if test="${empty testCases}">
                    <tr>
                        <td colspan="4">Chưa có test case nào.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            <!-- Thêm test case mới -->
            <div class="add-testcase-box">
                <h3>Thêm test case mới</h3>
                <form method="post" action="ProblemEdit">
                    <input type="hidden" name="action" value="addTestCase">
                    <input type="hidden" name="problem_id" value="${problem.problem_id}">

                    <div class="form-group">
                        <label>Input:</label>
                        <textarea name="input" rows="3" required></textarea>
                    </div>

                    <div class="form-group">
                        <label>Expected Output:</label>
                        <textarea name="expected_output" rows="3" required></textarea>
                    </div>

                    <button type="submit" class="btn">Thêm test case</button>
                </form>
            </div>
        </div>

    </div>

</div>

</body>
</html>
