package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import judge.JudgeManager;
import model.BO.SubmissionsBO;
import model.Bean.Submissions;
import model.Bean.Users;

import java.io.IOException;

@WebServlet(name = "SubmitCode", value = "/SubmitCode")
public class SubmitCodeController extends HttpServlet {

    private final SubmissionsBO submissionsBO = new SubmissionsBO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Users currentUser = (Users) session.getAttribute("currentUser");

        String problemIdStr = request.getParameter("problemId");
        String code = request.getParameter("code");

        int problemId = Integer.parseInt(problemIdStr);

        Submissions s = new Submissions();
        s.setUser_id(currentUser.getUser_id());
        s.setProblem_id(problemId);
        s.setFilename("Main.java");
        s.setCode(code);
        s.setStatus("PENDING");
        s.setScore(0);

        int submitId = submissionsBO.createSubmission(s);
        if(submitId > 0){
            JudgeManager.enqueue(submitId);
        }
        response.sendRedirect(
                request.getContextPath() + "/ProblemDetail?id=" + problemId + "&submitted=1"
        );
    }
}
