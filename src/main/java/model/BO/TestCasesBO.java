package model.BO;

import model.Bean.TestCase;
import model.DAO.TestCasesDAO;

import java.util.List;

public class TestCasesBO {

    private final TestCasesDAO testCasesDAO = new TestCasesDAO();

    public List<TestCase> getByProblemId(int problemId) {
        return testCasesDAO.getByProblemId(problemId);
    }
}
