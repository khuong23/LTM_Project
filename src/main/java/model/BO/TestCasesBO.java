package model.BO;

import model.Bean.TestCase;
import model.DAO.TestCasesDAO;

import java.util.List;

public class TestCasesBO {

    private final TestCasesDAO testCasesDAO = new TestCasesDAO();

    public List<TestCase> getByProblemId(int problemId) {
        return testCasesDAO.getByProblemId(problemId);
    }

    public List<TestCase> getTestCasesByProblemId(int problemId) {
        return testCasesDAO.getByProblemId(problemId);
    }

    public void addTestCase(TestCase tc) {
        testCasesDAO.addTestCase(tc);
    }

    public void updateTestCase(TestCase tc) {
        testCasesDAO.updateTestCase(tc);
    }

    public void deleteTestCase(int testCaseId) {
        testCasesDAO.deleteTestCase(testCaseId);
    }
}
