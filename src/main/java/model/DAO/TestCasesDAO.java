package model.DAO;

import utils.DBConnection;
import model.Bean.TestCase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestCasesDAO {

    public List<TestCase> getByProblemId(int problemId) {
        List<TestCase> list = new ArrayList<>();
        String sql = "SELECT test_case_id, problem_id, input, expected_output FROM TestCases WHERE problem_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, problemId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TestCase tc = new TestCase();
                    tc.setTestCaseId(rs.getInt("test_case_id"));
                    tc.setProblemId(rs.getInt("problem_id"));
                    tc.setInput(rs.getString("input"));
                    tc.setExpectedOutput(rs.getString("expected_output"));
                    list.add(tc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public void addTestCase(TestCase tc) {
        String sql = "INSERT INTO TestCases (problem_id, input, expected_output) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tc.getProblemId());
            ps.setString(2, tc.getInput());
            ps.setString(3, tc.getExpectedOutput());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateTestCase(TestCase tc) {
        String sql = "UPDATE TestCases SET input = ?, expected_output = ? WHERE test_case_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tc.getInput());
            ps.setString(2, tc.getExpectedOutput());
            ps.setInt(3, tc.getTestCaseId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteTestCase(int testCaseId) {
        String sql = "DELETE FROM TestCases WHERE test_case_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, testCaseId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
