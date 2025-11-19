package com.ltmproject.model;

public class Problems {
    private int problem_id;
    private String description;
    Problems(int problem_id, String description) {
        this.problem_id = problem_id;
        this.description = description;
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
