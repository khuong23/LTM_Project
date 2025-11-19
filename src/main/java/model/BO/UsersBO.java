package model.BO;

import model.Bean.Users;
import model.DAO.UsersDAO;
import java.sql.SQLException;

public class UsersBO {
    private UsersDAO usersDAO = new UsersDAO();

    /**
     * Kiểm tra thông tin đăng nhập
     * @param username - Tên đăng nhập
     * @param password - Mật khẩu
     * @return Users object nếu đăng nhập thành công, null nếu thất bại
     */
    public Users checkLogin(String username, String password) {
        try {
            // Kiểm tra input rỗng
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return null;
            }
            
            // Lấy thông tin user từ DAO
            Users user = usersDAO.getUser(username.trim());
            
            // Kiểm tra mật khẩu
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Kiểm tra user có phải là admin không
     * @param user - User object
     * @return true nếu là admin, false nếu không
     */
    public boolean isAdmin(Users user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
    
    /**
     * Tạo user mới
     * @param username - Tên đăng nhập
     * @param password - Mật khẩu
     * @param role - Vai trò (admin/user)
     * @return Users object nếu tạo thành công, null nếu thất bại
     */
    public Users createUser(String username, String password, String role) {
        try {
            // Kiểm tra input
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty() ||
                role == null || role.trim().isEmpty()) {
                return null;
            }
            
            // Kiểm tra user đã tồn tại chưa
            Users existingUser = usersDAO.getUser(username.trim());
            if (existingUser != null) {
                return null; // User đã tồn tại
            }
            
            // Tạo user mới
            return usersDAO.setUser(username.trim(), password, role.trim());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
