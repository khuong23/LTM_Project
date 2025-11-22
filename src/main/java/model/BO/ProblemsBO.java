package model.BO;

import model.Bean.Problems;
import model.DAO.ProblemsDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemsBO {
    private ProblemsDAO problemsDAO = new ProblemsDAO();

    public List<Problems> getAllProblems() {
        try {
            return problemsDAO.getAllProblems();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Problems getProblemById(int problemId) {
        try {
            return problemsDAO.getProblemById(problemId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countProblems() {
        return ProblemsDAO.countProblems();
    }

    public void updateProblem(Problems p) {
        ProblemsDAO.updateProblem(p);
    }

    public int addProblem(Problems p) {
        return ProblemsDAO.addProblem(p);
    }
}