package controller;

import model.BO.UsersBO;
import model.Bean.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CheckLoginServlet", value = "/CheckLogin")
public class CheckLoginServlet extends HttpServlet {
    private UsersBO usersBO = new UsersBO();
    
    public CheckLoginServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Users user = usersBO.checkLogin(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setAttribute("username", username);
            
            if (usersBO.isAdmin(user)) {
                session.setAttribute("isAdmin", true);
                response.sendRedirect("admin_page.jsp");
            } else {
                session.setAttribute("isAdmin", false);
                response.sendRedirect("Home");
            }
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
