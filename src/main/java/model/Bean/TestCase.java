package model.Bean;

public class TestCase {
    private int test_case_id;
    private int problem_id;
    private String input;
    private String expected_output;

    // Nếu sau này muốn dùng weight thì có sẵn, mặc định = 1
    private int weight = 1;

    public int getTestCaseId() {
        return test_case_id;
    }

    public void setTestCaseId(int testCaseId) {
        this.test_case_id = testCaseId;
    }

    public int getProblemId() {
        return problem_id;
    }

    public void setProblemId(int problemId) {
        this.problem_id = problemId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expected_output;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expected_output = expectedOutput;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
