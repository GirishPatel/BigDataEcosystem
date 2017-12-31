package db.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlPoolableObjectFactory extends BasePoolableObjectFactory<Connection> {
    private String host;
    private int port;
    private String dbName;
    private String user;
    private String password;

    MySqlPoolableObjectFactory(String host, int port, String dbName, String user, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection makeObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?autoReconnectForPools=true";
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void destroyObject(Connection obj) throws Exception {
        obj.close();
    }

    @Override
    public boolean validateObject(Connection obj) {
        try {
            return !obj.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
