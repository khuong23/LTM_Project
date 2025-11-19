package com.ltmproject.controller;

import com.ltmproject.dao.UsersDAO;
import com.ltmproject.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckLogin", value = "/CheckLogin")
public class CheckLogin extends HttpServlet {
    public CheckLogin() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        Integer password = Integer.valueOf(request.getParameter("password"));
        try {
            Users users = UsersDAO.getUser(username);
            if(users != null && users.getPassword().equals(password.toString())) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                if(users.getRole().equals("admin")) {
                    session.setAttribute("isAdmin", true);
                    response.sendRedirect("admin_page.jsp");
                }
                else {
                    session.setAttribute("isAdmin", false);
                    response.sendRedirect("user_page.jsp");
                }
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}