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
        
        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Gọi BO để kiểm tra login
        Users user = usersBO.checkLogin(username, password);
        
        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setAttribute("username", username);
            
            // Phân quyền dựa vào role
            if (usersBO.isAdmin(user)) {
                session.setAttribute("isAdmin", true);
                response.sendRedirect("admin_page.jsp");
            } else {
                session.setAttribute("isAdmin", false);
                response.sendRedirect("user_page.jsp");
            }
        } else {
            // Đăng nhập thất bại
            response.sendRedirect("login.jsp?error=1");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect về trang login nếu truy cập qua GET
        response.sendRedirect("login.jsp");
    }
}
