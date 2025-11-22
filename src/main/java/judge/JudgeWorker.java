package judge;

import java.util.concurrent.BlockingQueue;

public class JudgeWorker implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final JudgeService judgeService = new JudgeService();

    public JudgeWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int submitId = queue.take(); // chờ có job
                judgeService.judgeSubmission(submitId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
