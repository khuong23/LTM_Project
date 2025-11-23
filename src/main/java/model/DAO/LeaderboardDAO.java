package model.DAO;

import model.Bean.LeaderboardEntry;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {

    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String query = "SELECT u.username, " +
                "COALESCE(SUM(s_max.max_score), 0) AS total_score, " +
                "COUNT(DISTINCT CASE WHEN s_accepted.status = 'ACCEPTED' THEN s_accepted.problem_id END) AS solved_count "
                +
                "FROM Users u " +
                "LEFT JOIN ( " +
                "    SELECT user_id, problem_id, MAX(score) as max_score " +
                "    FROM Submissions " +
                "    GROUP BY user_id, problem_id " +
                ") s_max ON u.user_id = s_max.user_id " +
                "LEFT JOIN Submissions s_accepted ON u.user_id = s_accepted.user_id AND s_accepted.status = 'ACCEPTED' "
                +
                "WHERE u.role = 'user' " +
                "GROUP BY u.user_id, u.username " +
                "ORDER BY total_score DESC, solved_count DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                String username = rs.getString("username");
                int totalScore = rs.getInt("total_score");
                int solvedCount = rs.getInt("solved_count");

                leaderboard.add(new LeaderboardEntry(rank++, username, totalScore, solvedCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
}
