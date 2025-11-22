package model.Bean;

public class Problems {
    private int problem_id;
    private String title;
    private String description;
    private String difficulty;
    private Double ac_rate;
    private String tags;
    
    public Problems(int problem_id, String title, String description, String difficulty, double ac_rate, String tags) {
        this.problem_id = problem_id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ac_rate = ac_rate;
        this.tags = tags;
    }
    public Problems() {}
    public int getProblem_id() {
        return problem_id;
    }
    
    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public double getAcRate() {
        return ac_rate;
    }
    
    public void setAcRate(double ac_rate) {
        this.ac_rate = ac_rate;
    }
    
    public int getId() {
        return problem_id;
    }
    
    public void setId(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getTags() { return tags;}

    public void setTags(String tags) {
        this.tags = tags;
    }
}
