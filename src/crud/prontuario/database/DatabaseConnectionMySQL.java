package crud.prontuario.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import crud.prontuario.utils.EnvLoader;

public class DatabaseConnectionMySQL implements IConnection {

	private final String USERNAME = EnvLoader.get("DB_USERNAME");
	private final String PASSWORD = EnvLoader.get("DB_PASSWORD");
	private final String ADDRESS = EnvLoader.get("DB_ADDRESS");
	private final String PORT = EnvLoader.get("DB_PORT");
	private final String DATABASE = EnvLoader.get("DB_NAME");

    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            String url = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/" + DATABASE;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco MySQL");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão MySQL");
            e.printStackTrace();
        }
    }
}
