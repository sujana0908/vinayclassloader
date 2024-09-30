package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {
    private final List<Connection> availableConnections = new ArrayList<>();
    private final String url = "jdbc:h2:mem:testdb"; // Using H2 in-memory database
    private final Logger logger = Logger.getLogger(ConnectionPool.class.getName());

    public ConnectionPool(int initialSize) throws SQLException {
        for(int i = 0; i < initialSize; i++) {
            availableConnections.add(createNewConnection());
        }
    }

    private Connection createNewConnection() throws SQLException {
        logger.info("Creating new database connection");
        return DriverManager.getConnection(url);
    }

    public synchronized Connection getConnection() throws SQLException {
        if(availableConnections.isEmpty()) {
            logger.info("No available connections, creating a new one");
            return createNewConnection();
        } else {
            logger.info("Reusing existing database connection");
            return availableConnections.remove(availableConnections.size() - 1);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        availableConnections.add(connection);
        logger.info("Connection returned to pool");
    }
}
