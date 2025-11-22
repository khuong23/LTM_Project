package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import model.Bean.Problems;
import model.Bean.TestCase;
import model.BO.ProblemsBO;
import model.BO.TestCasesBO;

@WebServlet(name = "ProblemEdit", value = "/ProblemEdit")
public class EditProblemController extends HttpServlet {

    private ProblemsBO problemsBO;
    private TestCasesBO testCasesBO;

    @Override
    public void init() throws ServletException {
        problemsBO = new ProblemsBO();
        testCasesBO = new TestCasesBO();
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null
                && session.getAttribute("currentUser") != null
                && session.getAttribute("isAdmin") != null
                && (Boolean) session.getAttribute("isAdmin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String problemIdStr = request.getParameter("problem_id");
        if (problemIdStr == null) {
            response.sendRedirect("admin");
            return;
        }

        int problemId = Integer.parseInt(problemIdStr);

        Problems problem = problemsBO.getProblemById(problemId);
        if (problem == null) {
            response.sendRedirect("admin");
            return;
        }

        List<TestCase> testCases = testCasesBO.getTestCasesByProblemId(problemId);

        request.setAttribute("problem", problem);
        request.setAttribute("testCases", testCases);

        RequestDispatcher rd = request.getRequestDispatcher("problem_edit.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        // ĐÚNG: lấy từ param problem_id
        int problemId = Integer.parseInt(request.getParameter("problem_id"));

        try {
            switch (action) {
                case "updateProblem": {
                    String title = request.getParameter("title");
                    String description = request.getParameter("description");
                    String difficulty = request.getParameter("difficulty");
                    String acRateStr = request.getParameter("acRate");
                    String tags = request.getParameter("tags");

                    double acRate = 0;
                    try {
                        acRate = Double.parseDouble(acRateStr);
                    } catch (NumberFormatException e) {
                        acRate = 0;
                    }

                    Problems p = new Problems();
                    p.setProblem_id(problemId);
                    p.setTitle(title);
                    p.setDescription(description);
                    p.setDifficulty(difficulty);
                    p.setAcRate(acRate);
                    p.setTags(tags);

                    problemsBO.updateProblem(p);
                    break;
                }

                case "addTestCase": {
                    String input = request.getParameter("input");
                    String expectedOutput = request.getParameter("expected_output");

                    TestCase tc = new TestCase();
                    tc.setProblemId(problemId);
                    tc.setInput(input);
                    tc.setExpectedOutput(expectedOutput);

                    testCasesBO.addTestCase(tc);
                    break;
                }

                case "updateTestCase": {
                    int testCaseId = Integer.parseInt(request.getParameter("test_case_id"));
                    String input = request.getParameter("input");
                    String expectedOutput = request.getParameter("expected_output");

                    TestCase tc = new TestCase();
                    tc.setTestCaseId(testCaseId);
                    tc.setProblemId(problemId);
                    tc.setInput(input);
                    tc.setExpectedOutput(expectedOutput);

                    testCasesBO.updateTestCase(tc);
                    break;
                }

                case "deleteTestCase": {
                    int testCaseId = Integer.parseInt(request.getParameter("test_case_id"));
                    testCasesBO.deleteTestCase(testCaseId);
                    break;
                }

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect lại
        response.sendRedirect("ProblemEdit?problem_id=" + problemId);
    }
}
