// model/BO/SubmissionsBO.java
package model.BO;

import model.Bean.Submissions;
import model.DAO.SubmissionsDAO;

public class SubmissionsBO {
    private final SubmissionsDAO submissionsDAO = new SubmissionsDAO();

    public Submissions getLatestSubmission(int userId, int problemId) {
        return submissionsDAO.findLatestByUserAndProblem(userId, problemId);
    }

    public void createSubmission(Submissions submission) {
        submissionsDAO.insert(submission);
    }
}
