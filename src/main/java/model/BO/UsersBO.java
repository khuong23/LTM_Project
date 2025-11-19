package model.BO;

import model.Bean.Users;
import model.DAO.UsersDAO;
import java.sql.SQLException;

public class UsersBO {
    private UsersDAO usersDAO = new UsersDAO();

    public Users checkLogin(String username, String password) {
        try {
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return null;
            }
            
            Users user = usersDAO.getUser(username.trim());
            
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean isAdmin(Users user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
    
    public Users createUser(String username, String password, String role) {
        try {
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty() ||
                role == null || role.trim().isEmpty()) {
                return null;
            }
            
            Users existingUser = usersDAO.getUser(username.trim());
            if (existingUser != null) {
                return null;
            }
            
            return usersDAO.setUser(username.trim(), password, role.trim());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
