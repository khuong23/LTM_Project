package model.DAO;

import model.Bean.Problems;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemsDAO {
    public List<Problems> getAllProblems() throws SQLException {
        List<Problems> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Problems";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Problems(
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