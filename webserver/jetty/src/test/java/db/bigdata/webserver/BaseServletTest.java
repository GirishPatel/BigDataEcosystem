package db.bigdata.webserver;

import db.bigdata.webserver.app.JettyServer;
import junit.framework.TestCase;

public abstract class BaseServletTest extends TestCase {

    private JettyServer server;

    abstract String username();

    protected void setUp() {
        server = new JettyServer();
        server.start();
    }

    protected void tearDown() {
        server.stop();
    }

}
