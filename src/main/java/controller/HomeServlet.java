package controller;

import model.BO.ProblemsBO;
import model.Bean.Problems;
import model.Bean.Users;
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

        HttpSession session = request.getSession(false);
        int userId = 0;
        if (session != null && session.getAttribute("currentUser") != null) {
            Users user = (Users) session.getAttribute("currentUser");
            userId = user.getUser_id();
        }

        String difficulty = request.getParameter("difficulty");
        String tags = request.getParameter("tags");
        String status = request.getParameter("status");

        List<Problems> list = problemsBO.getProblemsByFilter(difficulty, tags, status, userId);
        List<String> tagList = problemsBO.getAllUniqueTags();

        request.setAttribute("problemList", list);
        request.setAttribute("paramDifficulty", difficulty);
        request.setAttribute("paramTags", tags);
        request.setAttribute("paramStatus", status);
        request.setAttribute("availableTags", tagList);


        request.getRequestDispatcher("user_page.jsp").forward(request, response);
    }
}