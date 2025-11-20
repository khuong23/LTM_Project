package model.Bean;

public class Problems {
    private int problem_id;
    private String description;
    public Problems(int problem_id, String description) {
        this.problem_id = problem_id;
        this.description = description;
    }

    public Problems(int problemId, String title, String description, String difficulty, double acRate) {
    }

    public int getProblem_id() {
        return problem_id;
    }
    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
