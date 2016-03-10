package netcracker.edu.ishop.utils.oracledatabase;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    //oracle http port is 8088 now

    private static ConnectionPool INSTANCE = new ConnectionPool();

    private DataSource datasource;

    private ConnectionPool() {
        datasource = new DataSource();
        PoolProperties poolProps = new PoolProperties();
        poolProps.setUrl("jdbc:oracle:thin:@//localhost:1521/xe");
        poolProps.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        poolProps.setUsername("verlorener");
        poolProps.setPassword("verlorener");
        poolProps.setJmxEnabled(true);
        poolProps.setTestWhileIdle(false);
        poolProps.setTestOnBorrow(true);
        poolProps.setValidationQuery("SELECT 1 FROM DUAL");
        poolProps.setTestOnReturn(false);
        poolProps.setValidationInterval(30000);
        poolProps.setTimeBetweenEvictionRunsMillis(30000);
        poolProps.setMaxActive(20);
        poolProps.setMaxIdle(20);
        poolProps.setInitialSize(10);
        poolProps.setMaxWait(10000);
        poolProps.setRemoveAbandonedTimeout(60);
        poolProps.setMinEvictableIdleTimeMillis(30000);
        poolProps.setMinIdle(10);
        poolProps.setLogAbandoned(true);
        poolProps.setRemoveAbandoned(true);
        poolProps.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        datasource.setPoolProperties(poolProps);


    }
    public static ConnectionPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool();
        }
        return INSTANCE;
    }


    public Connection getPooledConnection() {

        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            // don't forget to close connections in order to return them to pool;
        }
    }
}


