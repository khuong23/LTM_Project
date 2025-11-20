package model.Bean;

public class Submissions{
    private int submit_id;
    private int user_id;
    private int problem_id;
    private String filename;
    private String code;
    private String status;
    private int score;
    private String output;
    private String error;

    public Submissions(int submit_id, int user_id, int problem_id, String filename, String code, String status, int score, String ouput, String error) {
        this.submit_id = submit_id;
        this.user_id = user_id;
        this.problem_id = problem_id;
        this.filename = filename;
        this.code = code;
        this.status = status;
        this.score = score;
        this.output = ouput;
        this.error = error;
    }

    public int getSubmit_id() {
        return submit_id;
    }
    public void setSubmit_id(int submit_id) {
        this.submit_id = submit_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getProblem_id() {
        return problem_id;
    }
    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
