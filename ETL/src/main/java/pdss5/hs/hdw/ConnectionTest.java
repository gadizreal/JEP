package pdss5.hs.hdw;
import java.sql.*;
public class ConnectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
	
		             Connection conn = null;
		             Statement stmt = null;
		             try {
		                    System.out.println("Class UP");
		                    Class.forName("com.ibm.db2.jcc.DB2Driver");
		                    System.out.println("Class ok");
		                    conn = DriverManager.getConnection("jdbc:db2://192.168.0.200:50000/SAMPLE", "db2inst1", "passw0rd");
		                    System.out.println("connect ok");
		                    stmt = conn.createStatement();
		                    ResultSet rs = stmt.executeQuery("select * from DEPARTMENT");
		                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		                    while(rs.next())
		                    {
		                           System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5));
		                    }
		             }
		             catch(ClassNotFoundException ee) {
		                    System.out.println("ClassNotFoundException,//   "+ ee );
		             }
		             catch(SQLException ee) {
		                    System.out.println(ee.toString());
		             }
		             finally {
		                    try {
		                           stmt.close();
		                    }
		                    catch(Exception ee) {
		                    }
		                    try{
		                           conn.close();
		                    }
		                    catch(Exception e1) {
		                    }
		             }	
		
		
		/////////////
	}

}
