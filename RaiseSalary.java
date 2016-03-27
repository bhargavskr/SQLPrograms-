import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RaiseSalary {
	
	private static Connection conn = null;

	public static void raise() {
		try {
			if(conn == null) {
				conn = DriverManager.getConnection("jdbc:default:connection");
			}
			PreparedStatement ps = conn.prepareStatement("Select sal from emp", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ) {
				double salary = rs.getDouble(1);	
				if (salary < 1000) {
					rs.updateDouble(1, salary * 1.1);
				} else if (salary < 3000) {
					rs.updateDouble(1, salary * 1.2);
				} else {
					rs.updateDouble(1, salary * 1.3);
				}
				rs.updateRow();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		try {
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@cs440.systems.wvu.edu:2222:cs440", "scott", "tiger");
			raise();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
