package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/mercadobanco?useTimezone=true&serverTimezone=UTC", "root", "tulio123");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
