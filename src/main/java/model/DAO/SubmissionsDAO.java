package model.DAO;

import model.Bean.Submissions;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionsDAO {

    public Submissions findLatestByUserAndProblem(int userId, int problemId) {
        String sql = """
                    SELECT * FROM Submissions
                    WHERE user_id = ? AND problem_id = ?
                    ORDER BY submit_id DESC
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
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getUser_id());
            ps.setInt(2, s.getProblem_id());
            ps.setString(3, s.getFilename());
            ps.setString(4, s.getCode());
            ps.setString(5, s.getStatus());
            ps.setInt(6, s.getScore());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return -1;
            }

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

    public int countSubmissions() {
        String sql = "SELECT COUNT(*) FROM Submissions";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int countAcceptedSubmissions() {
        String sql = "SELECT COUNT(*) FROM Submissions WHERE status = 'ACCEPTED'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Submissions> getRecentSubmissions(int numOfSubmissions) {
        List<Submissions> list = new ArrayList<>();
        String sql = "SELECT s.submit_id, s.user_id, s.problem_id, s.status, s.score, " +
                "u.username, p.title AS problem_title " +
                "FROM Submissions s " +
                "JOIN Users u ON s.user_id = u.user_id " +
                "JOIN Problems p ON s.problem_id = p.problem_id " +
                "ORDER BY s.submit_id DESC " +
                "LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numOfSubmissions);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Submissions s = new Submissions();
                    s.setSubmit_id(rs.getInt("submit_id"));
                    s.setUser_id(rs.getInt("user_id"));
                    s.setProblem_id(rs.getInt("problem_id"));
                    s.setStatus(rs.getString("status"));
                    s.setScore(rs.getInt("score"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getUsernameBySubmissionId(int submitId) {
        String sql = """
                    SELECT u.username
                    FROM Submissions s
                    JOIN Users u ON s.user_id = u.user_id
                    WHERE s.submit_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, submitId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getProblemTitleBySubmissionId(int submitId) {
        String sql = """
                    SELECT p.title
                    FROM Submissions s
                    JOIN Problems p ON s.problem_id = p.problem_id
                    WHERE s.submit_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, submitId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("title");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<Submissions> getHistory(int userId, int problemId) {
        List<Submissions> list = new ArrayList<>();
        String sql = "SELECT * FROM Submissions WHERE user_id = ? AND problem_id = ? ORDER BY submit_id DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, problemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
