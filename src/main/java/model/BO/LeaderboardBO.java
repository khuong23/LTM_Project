package model.BO;

import model.Bean.LeaderboardEntry;
import model.DAO.LeaderboardDAO;
import java.util.List;

public class LeaderboardBO {
    private LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboardDAO.getLeaderboard();
    }
}
