package db.pool;

import java.sql.Connection;
import java.sql.ResultSet;

public class MySQLUtility {

    public static ResultSet get(String query) throws Exception {
        Connection connection = MySQLConnectionPool.pool.borrowObject();
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        MySQLConnectionPool.pool.returnObject(connection);
        return resultSet;
    }

    public static int update(String query) {
        try {
            Connection connection = MySQLConnectionPool.pool.borrowObject();
            int count = connection.prepareStatement(query).executeUpdate();
            MySQLConnectionPool.pool.returnObject(connection);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
