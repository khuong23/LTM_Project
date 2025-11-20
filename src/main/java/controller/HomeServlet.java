package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Bean.Problems;
import model.DAO.ProblemsDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/Home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProblemsDAO problemsDAO = new ProblemsDAO();
        try {
            List<Problems> list = problemsDAO.getAllProblems();
            request.setAttribute("problemList", list);
            request.getRequestDispatcher("user_page.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
