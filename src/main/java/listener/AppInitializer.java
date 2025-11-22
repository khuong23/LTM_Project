package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import judge.JudgeManager;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[AppInitializer] Starting JudgeManager...");
        JudgeManager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppInitializer] Stopping JudgeManager...");
        JudgeManager.stop();
    }
}
