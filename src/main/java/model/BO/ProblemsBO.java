package model.BO;

import model.Bean.Problems;
import model.DAO.ProblemsDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

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

    public List<String> getAllUniqueTags() {
        List<String> rawTags = problemsDAO.getAllTagsRaw();

        Set<String> uniqueTags = new TreeSet<>();

        for (String row : rawTags) {
            if (row == null) continue;

            String[] parts = row.split(",");

            for (String part : parts) {
                String cleanTag = part.trim();
                if (!cleanTag.isEmpty()) {
                    uniqueTags.add(cleanTag);
                }
            }
        }

        return new ArrayList<>(uniqueTags);
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

    public void deleteProblem(int problemId) {
        problemsDAO.deleteProblems(problemId);
    }

    public List<Problems> getProblemsByFilter(String difficulty, String tag, String status, int userId) {
        return problemsDAO.getProblemsByFilter(difficulty, tag, status, userId);
    }
}