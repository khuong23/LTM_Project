package judge;

import model.BO.SubmissionsBO;
import model.BO.TestCasesBO;
import model.Bean.Submissions;
import model.Bean.TestCase;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JudgeService {

    private final SubmissionsBO submissionsBO = new SubmissionsBO();
    private final TestCasesBO testCasesBO = new TestCasesBO();

    public void judgeSubmission(int submitId) {
        Submissions sub = submissionsBO.getById(submitId);
        if (sub == null)
            return;

        // cập nhật trạng thái
        submissionsBO.updateStatus(submitId, "PROCESSING");

        try {
            Path workDir = Files.createTempDirectory("submission_" + submitId + "_");
            Path sourceFile = workDir.resolve("Main.java");

            // Ghi code ra file
            Files.writeString(sourceFile, sub.getCode());

            // 1. COMPILE
            ProcessBuilder javacPb = new ProcessBuilder("javac", "Main.java");
            javacPb.directory(workDir.toFile());
            Process javacProc = javacPb.start();

            String compileErr = readStream(javacProc.getErrorStream());
            int compileExit = javacProc.waitFor();

            if (compileExit != 0) {
                submissionsBO.updateResult(
                        submitId,
                        "COMPILE_ERROR",
                        0,
                        null,
                        compileErr);
                return;
            }

            // 2. RUN TESTCASES
            List<TestCase> testCases = testCasesBO.getByProblemId(sub.getProblem_id());

            int totalWeight = 0;
            int passedWeight = 0;
            StringBuilder allOutput = new StringBuilder();

            for (TestCase tc : testCases) {
                totalWeight += tc.getWeight();

                ProcessBuilder javaPb = new ProcessBuilder(
                        "java", "-cp", ".", "Main" // 🔥 QUAN TRỌNG
                );
                javaPb.directory(workDir.toFile()); // 🔥 CŨNG QUAN TRỌNG
                Process javaProc = javaPb.start();

                // Ghi input
                try (BufferedWriter w = new BufferedWriter(
                        new OutputStreamWriter(javaProc.getOutputStream()))) {
                    w.write(tc.getInput());
                    w.flush();
                }

                boolean finished = javaProc.waitFor(30, TimeUnit.SECONDS);
                if (!finished) {
                    javaProc.destroyForcibly();
                    submissionsBO.updateResult(
                            submitId,
                            "TIME_LIMIT",
                            0,
                            allOutput.toString(),
                            "Time limit exceeded");
                    return;
                }

                int exitCode = javaProc.exitValue();
                String stdout = readStream(javaProc.getInputStream()).trim();
                String stderr = readStream(javaProc.getErrorStream()).trim();
                String expected = tc.getExpectedOutput().trim();

                allOutput.append("Test ").append(tc.getTestCaseId()).append(":\n");
                allOutput.append("Input: ").append(tc.getInput()).append("\n");
                if (!stdout.isEmpty()) {
                    allOutput.append("Output: ").append(stdout).append("\n");
                }
                allOutput.append("Expected: ").append(expected).append("\n");
                if (!stderr.isEmpty()) {
                    allOutput.append("Error:\n").append(stderr).append("\n");
                }
                allOutput.append("\n");

                // Nếu process lỗi runtime (exitCode != 0) → có thể coi là RUNTIME_ERROR
                if (exitCode != 0) {
                    submissionsBO.updateResult(
                            submitId,
                            "RUNTIME_ERROR",
                            0,
                            allOutput.toString(),
                            stderr);
                    return;
                }

                if (stdout.equals(expected)) {
                    passedWeight += tc.getWeight();
                }
            }

            int score = (totalWeight == 0) ? 0 : (passedWeight * 100 / totalWeight);
            String finalStatus = (passedWeight == totalWeight) ? "ACCEPTED" : "WRONG_ANSWER";

            submissionsBO.updateResult(
                    submitId,
                    finalStatus,
                    score,
                    allOutput.toString(),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            submissionsBO.updateResult(submitId, "RUNTIME_ERROR", 0, null, e.getMessage());
        }
    }

    private String readStream(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        }
    }
}
