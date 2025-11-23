package model.Bean;

public class LeaderboardEntry {
    private int rank;
    private String username;
    private int totalScore;
    private int solvedCount;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(int rank, String username, int totalScore, int solvedCount) {
        this.rank = rank;
        this.username = username;
        this.totalScore = totalScore;
        this.solvedCount = solvedCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(int solvedCount) {
        this.solvedCount = solvedCount;
    }
}
