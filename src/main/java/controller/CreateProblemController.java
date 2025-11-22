package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import model.Bean.Problems;
import model.BO.ProblemsBO;

@WebServlet(name = "CreateProblemController", value = "/CreateProblem")
public class CreateProblemController extends HttpServlet {

    private ProblemsBO problemsBO;

    @Override
    public void init() throws ServletException {
        problemsBO = new ProblemsBO();
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null
                && session.getAttribute("currentUser") != null
                && session.getAttribute("isAdmin") != null
                && (Boolean) session.getAttribute("isAdmin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("problem_add.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String difficulty = request.getParameter("difficulty");
        String description = request.getParameter("description");
        String acRateStr = request.getParameter("acRate");
        String tags = request.getParameter("tags");

        double acRate = 0;
        try {
            if (acRateStr != null && !acRateStr.isEmpty()) {
                acRate = Double.parseDouble(acRateStr);
            }
        } catch (NumberFormatException e) {
            acRate = 0;
        }

        Problems p = new Problems();
        p.setTitle(title);
        p.setDescription(description);
        p.setDifficulty(difficulty);
        p.setAcRate(acRate);
        p.setTags(tags);

        int newProblemId = problemsBO.addProblem(p);

        if (newProblemId > 0) {
            response.sendRedirect("ProblemEdit?problem_id=" + newProblemId);
        } else {
            response.sendRedirect("admin");
        }
    }
}
