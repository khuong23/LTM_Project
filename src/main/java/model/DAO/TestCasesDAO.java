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
}
