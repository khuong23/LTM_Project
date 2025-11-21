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
        if (sub == null) return;

        // cập nhật trạng thái
        submissionsBO.updateStatus(submitId, "PROCESSING");

        try {
            Path workDir = Files.createTempDirectory("submission_" + submitId + "_");
            Path sourceFile = workDir.resolve("Main.java");

            // Ghi code ra file
            Files.writeString(sourceFile, sub.getCode());

            // 1. Compile
            ProcessBuilder javacPb = new ProcessBuilder(
                    "javac", sourceFile.getFileName().toString()
            );
            javacPb.directory(workDir.toFile());
            Process javacProc = javacPb.start();

            String compileErr = readStream(javacProc.getErrorStream());
            int compileExit = javacProc.waitFor();

            if (compileExit != 0) {
                // Lỗi compile
                submissionsBO.updateResult(
                        submitId,
                        "COMPILE_ERROR",
                        0,
                        null,
                        compileErr
                );
                return;
            }

            List<TestCase> testCases = testCasesBO.getByProblemId(sub.getProblem_id());

            int totalWeight = 0;
            int passedWeight = 0;
            StringBuilder allOutput = new StringBuilder();

            for (TestCase tc : testCases) {
                totalWeight += tc.getWeight();

                ProcessBuilder javaPb = new ProcessBuilder(
                        "java", "Main"
                );
                javaPb.directory(workDir.toFile());
                Process javaProc = javaPb.start();

                try (BufferedWriter w = new BufferedWriter(
                        new OutputStreamWriter(javaProc.getOutputStream()))) {
                    w.write(tc.getInput());
                    w.flush();
                }

                boolean finished = javaProc.waitFor(2, TimeUnit.SECONDS);
                if (!finished) {
                    javaProc.destroyForcibly();
                    submissionsBO.updateResult(
                            submitId,
                            "TIME_LIMIT",
                            0,
                            allOutput.toString(),
                            "Time limit exceeded"
                    );
                    return;
                }

                String stdout = readStream(javaProc.getInputStream()).trim();
                String stderr = readStream(javaProc.getErrorStream());

                allOutput.append("Test ").append(tc.getTestCaseId()).append(":\n")
                        .append(stdout).append("\n\n");

                String expected = tc.getExpectedOutput().trim();

                if (stdout.equals(expected)) {
                    passedWeight += tc.getWeight();
                } else {
                    // có thể log lệch để debug
                }
            }

            int score = (totalWeight == 0) ? 0 : (passedWeight * 100 / totalWeight);
            String finalStatus = (passedWeight == totalWeight) ? "ACCEPTED" : "WRONG_ANSWER";

            submissionsBO.updateResult(
                    submitId,
                    finalStatus,
                    score,
                    allOutput.toString(),
                    null
            );

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
