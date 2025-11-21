// model/BO/SubmissionsBO.java
package model.BO;

import model.Bean.Submissions;
import model.DAO.SubmissionsDAO;

public class SubmissionsBO {
    private final SubmissionsDAO submissionsDAO = new SubmissionsDAO();

    public Submissions getLatestSubmission(int userId, int problemId) {
        return submissionsDAO.findLatestByUserAndProblem(userId, problemId);
    }

    public int createSubmission(Submissions submission) {
        return submissionsDAO.insert(submission);
    }
    public Submissions getById(int submitId) {
        return submissionsDAO.findById(submitId);
    }
    public void updateStatus(int submitId, String status) {
        submissionsDAO.updateStatus(submitId, status);
    }

    public void updateResult(int submitId, String status, int score, String output, String error) {
        submissionsDAO.updateResult(submitId, status, score, output, error);
    }

}
