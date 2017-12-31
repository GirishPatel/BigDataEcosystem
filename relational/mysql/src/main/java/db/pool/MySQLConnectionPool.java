package db.pool;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;

import java.sql.Connection;

public class MySQLConnectionPool {

    public static ObjectPool<Connection> pool = getConnectionPool();

    public static ObjectPool<Connection> getConnectionPool() {

        String hostName = "localhost";
        int port = 3306;
        String dbName = "search_rating";
        String user = "root";
        String password = "";

        PoolableObjectFactory<Connection> mySqlPoolableObjectFactory = new MySqlPoolableObjectFactory(hostName, port, dbName, user, password);
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxActive = 10;
        config.testOnBorrow = true;
        config.testWhileIdle = true;
        config.timeBetweenEvictionRunsMillis = 10000;
        config.minEvictableIdleTimeMillis = 60000;

        GenericObjectPoolFactory<Connection> genericObjectPoolFactory = new GenericObjectPoolFactory<Connection>(mySqlPoolableObjectFactory, config);
        return genericObjectPoolFactory.createPool();
    }

}
