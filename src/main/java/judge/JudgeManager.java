package judge;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JudgeManager {

    private static final BlockingQueue<Integer> QUEUE = new LinkedBlockingQueue<>();
    private static volatile boolean running = false;

    public static void start() {
        if (running) return;
        running = true;

        // Tạo 1–2 worker thread, tuỳ config
        int workers = 2;
        for (int i = 0; i < workers; i++) {
            Thread t = new Thread(new JudgeWorker(QUEUE), "Judge-Worker-" + i);
            t.setDaemon(true);
            t.start();
        }
    }

    public static void stop() {
        running = false;
    }

    public static void enqueue(int submitId) {
        try {
            QUEUE.put(submitId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
