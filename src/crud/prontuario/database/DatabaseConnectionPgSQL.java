package crud.prontuario.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionPgSQL implements IConnection {
	
	private final String USERNAME = "brunorodrigo";
	private final String PASSWORD = "root";
	private final String ADDRESS = "localhost";
	private final String PORT = "5432";
	private final String DATABASE = "prontuario";

	 private Connection connection;
	 
	public Connection getConnection() {
		try {
			 String url = "jdbc:postgresql://" + ADDRESS + ":" + PORT + "/" + DATABASE;
			return DriverManager.getConnection(url, USERNAME, PASSWORD);
		}catch(SQLException e) {
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
