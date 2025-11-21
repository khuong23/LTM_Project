package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import judge.JudgeManager;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JudgeManager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JudgeManager.stop();
    }
}
