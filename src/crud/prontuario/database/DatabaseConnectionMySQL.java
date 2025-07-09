package crud.prontuario.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionMySQL implements IConnection {
	
	private final String USERNAME = "root";
	private final String PASSWORD = "root";
	private final String ADDRESS = "localhost";
	private final String PORT = "3306";
	private final String DATABASE = "prontuario";

	 private Connection connection;
	 
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			 String url = "jdbc:postgresql://" + ADDRESS + ":" + PORT + "/" + DATABASE;
			return DriverManager.getConnection(url, USERNAME, PASSWORD);
		}catch(SQLException e) {
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
            e.printStackTrace();
        }
    }

}
