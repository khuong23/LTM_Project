package controller;

import model.Bean.LeaderboardEntry;
import model.BO.LeaderboardBO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LeaderboardController", value = "/Leaderboard")
public class LeaderboardController extends HttpServlet {
    private LeaderboardBO leaderboardBO = new LeaderboardBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LeaderboardEntry> leaderboard = leaderboardBO.getLeaderboard();
        request.setAttribute("leaderboard", leaderboard);
        request.getRequestDispatcher("leaderboard.jsp").forward(request, response);
    }
}
