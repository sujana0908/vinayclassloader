package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConnectionPool;

public class DatabaseService {
    private final ConnectionPool connectionPool;

    public DatabaseService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void executeQuery(String query) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
