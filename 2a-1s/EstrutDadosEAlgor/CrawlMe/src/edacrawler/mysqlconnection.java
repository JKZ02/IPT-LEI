package edacrawler;

import java.sql.*;

public class mysqlconnection {
	
	public static Connection conexao = null;
	
	public static Connection bdconnector() {
		
		try {
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/crawlerdb", "root", "rootroot");
			return conexao;
		}catch(Exception e1) {return null;}
	}

}
