package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.BO.ProblemsBO;
import model.BO.SubmissionsBO;
import model.Bean.Problems;
import model.Bean.Submissions;
import model.Bean.Users;

import java.io.IOException;

@WebServlet(name = "ProblemDetail", value = "/ProblemDetail")
public class ProblemDetailController extends HttpServlet {

    private final ProblemsBO problemsBO = new ProblemsBO();
    private final SubmissionsBO submissionsBO = new SubmissionsBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Users currentUser = (Users) session.getAttribute("currentUser");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        int problemId = Integer.parseInt(idParam);

        Problems problem = problemsBO.getProblemById(problemId);
        if (problem == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy bài toán");
            return;
        }

        Submissions lastSubmission =
                submissionsBO.getLatestSubmission(currentUser.getUser_id(), problemId);

        request.setAttribute("problem", problem);
        request.setAttribute("lastSubmission", lastSubmission);

        request.getRequestDispatcher("problem_detail.jsp").forward(request, response);
    }
}
