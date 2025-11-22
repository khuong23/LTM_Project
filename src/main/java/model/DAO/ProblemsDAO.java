package model.DAO;

import model.Bean.Problems;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemsDAO {
    public static void updateProblem(Problems p) {
        String sql = "UPDATE Problems SET title = ?, description = ?, difficulty = ?, ac_rate = ?, tags = ? WHERE problem_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getDifficulty());
            ps.setDouble(4, p.getAcRate());
            ps.setString(5, p.getTags());
            ps.setInt(5, p.getProblem_id());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static int addProblem(Problems p) {
        String sql = "INSERT INTO Problems (title, description, difficulty, ac_rate, tags) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getDifficulty());
            ps.setDouble(4, p.getAcRate());
            ps.setString(5, p.getTags() != null ? p.getTags() : "Basic");

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // trả về problem_id vừa tạo
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


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
                        rs.getDouble("ac_rate"),
                        rs.getString("tags")
                ));
            }
        }
        return list;
    }
    public Problems getProblemById(int problemId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Problems WHERE problem_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, problemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Problems(
                            rs.getInt("problem_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("difficulty"),
                            rs.getDouble("ac_rate"),
                            rs.getString("tags")
                    );
                }
            }
        }
        return null;
    }
    public static int countProblems() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM Problems";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void deleteProblems(int problemId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlDeleteTC = "DELETE FROM TestCases WHERE problem_id = ?";
            ps = conn.prepareStatement(sqlDeleteTC);
            ps.setInt(1, problemId);
            ps.executeUpdate();
            ps.close();

            String sqlDeleteProb = "DELETE FROM Problems WHERE problem_id = ?";
            ps = conn.prepareStatement(sqlDeleteProb);
            ps.setInt(1, problemId);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();

            } catch (SQLException ex) {
                 ex.printStackTrace();
            } finally {
                try {
                    if (ps != null) ps.close();
                    if(conn != null) conn.close();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
        }
    }
    public List<Problems> getProblemsByFilter(String difficulty, String tag, String status, int userId) {
        List<Problems> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT p.* FROM Problems p WHERE 1=1 ");

        if (difficulty != null && !difficulty.isEmpty()) {
            sql.append(" AND p.difficulty = ? ");
        }
        if (tag != null && !tag.isEmpty()) {
            sql.append(" AND p.tags LIKE ? ");
        }

        if ("solved".equals(status)) {
            sql.append(" AND p.problem_id IN (SELECT problem_id FROM Submissions WHERE user_id = ? AND status = 'ACCEPTED') ");
        } else if ("unsolved".equals(status)) {
            sql.append(" AND p.problem_id NOT IN (SELECT problem_id FROM Submissions WHERE user_id = ? AND status = 'ACCEPTED') ");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (difficulty != null && !difficulty.isEmpty()) {
                ps.setString(index++, difficulty);
            }
            if (tag != null && !tag.isEmpty()) {
                ps.setString(index++, "%" + tag + "%");
            }
            if ("solved".equals(status) || "unsolved".equals(status)) {
                ps.setInt(index++, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Problems p = new Problems();
                    p.setProblem_id(rs.getInt("problem_id"));
                    p.setTitle(rs.getString("title"));
                    p.setDescription(rs.getString("description"));
                    p.setDifficulty(rs.getString("difficulty"));
                    p.setAcRate(rs.getDouble("ac_rate"));
                    p.setTags(rs.getString("tags")); // Lấy thêm tags
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getAllTagsRaw() {
        List<String> rawTags = new ArrayList<>();
        String sql = "SELECT DISTINCT tags FROM Problems WHERE tags IS NOT NULL AND tags <> ''";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rawTags.add(rs.getString("tags"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawTags;
    }
}