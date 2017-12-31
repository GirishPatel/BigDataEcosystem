package db.bigdata.webserver.app;

import db.bigdata.webserver.servlets.AuthenticateServlet;
import db.bigdata.webserver.servlets.CRUDServlet;
import db.bigdata.webserver.servlets.KafkaServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JettyServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private Server server;

    public void start() {

        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {

                    int APP_PORT = 8080;
                    server = new Server(APP_PORT);

                    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
                    contextHandler.setMaxFormContentSize(60000000);
                    contextHandler.setContextPath("/");
                    contextHandler.addFilter(CrossOriginFilter.class, "/*", java.util.EnumSet.of(DispatcherType.REQUEST));
                    contextHandler.addServlet(AuthenticateServlet.class, "/auth/*");
                    contextHandler.addServlet(CRUDServlet.class, "/crud/*");
                    contextHandler.addServlet(KafkaServlet.class, "/kafka/*");
                    contextHandler.addServlet(DefaultServlet.class, "/");

                    HandlerCollection contexts = new HandlerCollection();
                    ServletContextHandler[] servletContextHandlers = {contextHandler};
                    contexts.setHandlers(servletContextHandlers);
                    server.setHandler(contexts);

                    logger.debug("HANDLERS ATTACHED");

                    server.start();
                    logger.info("HTTP SERVER STARTED ON : " + APP_PORT);

                    server.join();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    public void stop() {
        try {
            server.stop();
            executorService.shutdown();
        } catch (Exception e) {
            logger.error("JETTY_SHUTDOWN_FAILED:" + e.getMessage(), e);
        }
    }

}
