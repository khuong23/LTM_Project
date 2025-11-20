package model.DAO;

import model.Bean.Problem;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemsDAO {
    public List<Problem> getAllProblems() throws SQLException {
        List<Problem> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Problems";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Problem(
                        rs.getInt("problem_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("difficulty"),
                        rs.getDouble("ac_rate")
                ));
            }
        }
        return list;
    }
}