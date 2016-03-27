import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CustomRaiseSal {
	
	private static Connection conn;

		
	public static int customRaise(double lowerLimit, double upperLimit, double percentage) {
		int numRaised = 0;
		try {
			if (conn == null) {
				conn = DriverManager.getConnection("jdbc:default:connection:");
			}
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
			ResultSet rs  = stmt.executeQuery("Select sal from emp");
			while(rs.next()) {
				double sal = rs.getDouble(1);
				System.out.println(sal);
				if ( sal >= lowerLimit && sal <= upperLimit) {
				    System.out.println("updating " + sal);	
				    rs.updateDouble(1, sal * (1+percentage/100));
					numRaised++;
					rs.updateRow();
				}
				
			}
			conn.commit();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return numRaised;
	}
	
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@cs440.systems.wvu.edu:2222:cs440", "scott", "tiger");
			System.out.println("we raised "+ customRaise(600,2500,12)+" salaries");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
