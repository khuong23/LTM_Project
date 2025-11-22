package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Bean.Users;
import model.Bean.Problems;
import model.Bean.Submissions;
import model.BO.UsersBO;
import model.BO.ProblemsBO;
import model.BO.SubmissionsBO;

@WebServlet(name = "AdminDashboardC", value = "/admin")
public class AdminDashboardController extends HttpServlet {

    private UsersBO usersBO;
    private ProblemsBO problemsBO;
    private SubmissionsBO submissionsBO;

    @Override
    public void init() throws ServletException {
        usersBO = new UsersBO();
        problemsBO = new ProblemsBO();
        submissionsBO = new SubmissionsBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null ||
                session.getAttribute("isAdmin") == null ||
                !(Boolean) session.getAttribute("isAdmin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int totalUsers = usersBO.countUsers();
        int totalProblems = problemsBO.countProblems();
        int totalSubmissions = submissionsBO.countSubmissions();
        int acceptedSubmissions = submissionsBO.countAcceptedSubmissions();

        List<Problems> problemList = problemsBO.getAllProblems();
        List<Submissions> recentSubmissions = submissionsBO.getRecentSubmissions(10);

        Map<Integer, String> submissionUsernames = new HashMap<>();
        Map<Integer, String> submissionProblemTitles = new HashMap<>();

        for (Submissions s : recentSubmissions) {
            int submitId = s.getSubmit_id();
            String username = submissionsBO.getUsernameBySubmissionId(submitId);
            String problemTitle = submissionsBO.getProblemTitleBySubmissionId(submitId);

            submissionUsernames.put(submitId, username);
            submissionProblemTitles.put(submitId, problemTitle);
        }

        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalProblems", totalProblems);
        request.setAttribute("totalSubmissions", totalSubmissions);
        request.setAttribute("acceptedSubmissions", acceptedSubmissions);

        request.setAttribute("problemList", problemList);
        request.setAttribute("recentSubmissions", recentSubmissions);
        request.setAttribute("submissionUsernames", submissionUsernames);
        request.setAttribute("submissionProblemTitles", submissionProblemTitles);

        RequestDispatcher rd = request.getRequestDispatcher("admin_page.jsp");
        rd.forward(request, response);
    }
}
