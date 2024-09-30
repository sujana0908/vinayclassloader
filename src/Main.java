import util.ConnectionPool;
import util.BeanContainer;

import java.util.logging.Logger;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        // Explicitly load the H2 driver class
        Class.forName("org.h2.Driver");

        Logger logger = Logger.getLogger(Main.class.getName());

        // Initialize BeanContainer
        BeanContainer beanContainer = new BeanContainer();

        // Initialize ConnectionPool
        ConnectionPool connectionPool = new ConnectionPool(2);
        beanContainer.registerBean("ConnectionPool", connectionPool);

        // Load classes using EncryptedClassLoader
        String classesDir = "classes";
        EncryptedClassLoader classLoader = new EncryptedClassLoader(classesDir);

        // Load and instantiate LoggingService
        Class<?> loggingServiceClass = classLoader.loadClass("services.LoggingService");
        Object loggingService = loggingServiceClass.getDeclaredConstructor().newInstance();
        beanContainer.registerBean("LoggingService", loggingService);

        // Load and instantiate DatabaseService
        Class<?> databaseServiceClass = classLoader.loadClass("services.DatabaseService");
        Object databaseService = databaseServiceClass
                .getDeclaredConstructor(ConnectionPool.class)
                .newInstance(connectionPool);
        beanContainer.registerBean("DatabaseService", databaseService);

        // Use the services via reflection
        Method logMethod = loggingServiceClass.getMethod("log", String.class);
        logMethod.invoke(loggingService, "Application started");

        Method executeQueryMethod = databaseServiceClass.getMethod("executeQuery", String.class);
        executeQueryMethod.invoke(databaseService, "CREATE TABLE Test(id INT PRIMARY KEY, name VARCHAR(255))");
        executeQueryMethod.invoke(databaseService, "INSERT INTO Test(id, name) VALUES(1, 'Test Name')");

        logMethod.invoke(loggingService, "Database operations completed");
        logMethod.invoke(loggingService, "Application finished");
    }
}
