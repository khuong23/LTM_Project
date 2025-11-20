package controller;

import model.BO.ProblemsBO;
import model.Bean.Problems;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/Home")
public class HomeServlet extends HttpServlet {
    private ProblemsBO problemsBO = new ProblemsBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Problems> list = problemsBO.getAllProblems();

        request.setAttribute("problemList", list);

        request.getRequestDispatcher("user_page.jsp").forward(request, response);
    }
}