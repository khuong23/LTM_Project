package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.BO.ProblemsBO;
import java.io.IOException;

@WebServlet(name = "DeleteProblem", value = "/DeleteProblem")
public class DeleteProblemsController extends HttpServlet {
    private ProblemsBO problemsBO;

    @Override
    public void init() {
        problemsBO = new ProblemsBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isAdmin") == null ||
                !(Boolean) session.getAttribute("isAdmin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int problemId = Integer.parseInt(idStr);
                problemsBO.deleteProblem(problemId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("admin");
    }
}