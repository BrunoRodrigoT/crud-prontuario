package crud.prontuario.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import crud.prontuario.utils.EnvLoader;

public class DatabaseConnectionPgSQL implements IConnection {

    private final String USERNAME = EnvLoader.get("DB_USERNAME");
    private final String PASSWORD = EnvLoader.get("DB_PASSWORD");
    private final String ADDRESS = EnvLoader.get("DB_ADDRESS");
    private final String PORT = EnvLoader.get("DB_PORT");
    private final String DATABASE = EnvLoader.get("DB_NAME");

    private Connection connection;

    public Connection getConnection() {
        try {
            String url = "jdbc:postgresql://" + ADDRESS + ":" + PORT + "/" + DATABASE;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
