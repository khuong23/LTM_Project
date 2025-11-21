package model.DAO;

import model.Bean.Submissions;
import utils.DBConnection;

import java.sql.*;

public class SubmissionsDAO {

    public Submissions findLatestByUserAndProblem(int userId, int problemId) {
        String sql = """
            SELECT * FROM Submissions
            WHERE user_id = ? AND problem_id = ?
            ORDER BY created_at DESC
            LIMIT 1
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, problemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Submissions s = new Submissions();
                    s.setSubmit_id(rs.getInt("submit_id"));
                    s.setUser_id(rs.getInt("user_id"));
                    s.setProblem_id(rs.getInt("problem_id"));
                    s.setFilename(rs.getString("filename"));
                    s.setCode(rs.getString("code"));
                    s.setStatus(rs.getString("status"));
                    s.setScore(rs.getInt("score"));
                    s.setOutput(rs.getString("output"));
                    s.setError(rs.getString("error"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Submissions s) {
        String sql = """
            INSERT INTO Submissions (user_id, problem_id, filename, code, status, score)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getUser_id());
            ps.setInt(2, s.getProblem_id());
            ps.setString(3, s.getFilename());
            ps.setString(4, s.getCode());
            ps.setString(5, s.getStatus());
            ps.setInt(6, s.getScore());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // submit_id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public Submissions findById(int id) {
        String sql = "SELECT * FROM Submissions WHERE submit_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Submissions s = new Submissions();
                    s.setSubmit_id(rs.getInt("submit_id"));
                    s.setUser_id(rs.getInt("user_id"));
                    s.setProblem_id(rs.getInt("problem_id"));
                    s.setFilename(rs.getString("filename"));
                    s.setCode(rs.getString("code"));
                    s.setStatus(rs.getString("status"));
                    s.setScore(rs.getInt("score"));
                    s.setOutput(rs.getString("output"));
                    s.setError(rs.getString("error"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateStatus(int submitId, String status) {
        String sql = "UPDATE Submissions SET status = ? WHERE submit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, submitId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateResult(int submitId, String status, int score, String output, String error) {
        String sql = """
        UPDATE Submissions
        SET status = ?, score = ?, output = ?, error = ?
        WHERE submit_id = ?
    """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, score);
            ps.setString(3, output);
            ps.setString(4, error);
            ps.setInt(5, submitId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
