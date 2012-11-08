package net.ivoa.oc.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class DBConnectionBuilder {
//private static final String configuration="jdbc:mysql://172.16.93.162/PDR_Calc?user=root&password=root";
	
	
	private static final String configuration="jdbc:mysql://localhost:3306/Broadening";	
	private static final String dbUser ="root";
	private static final String dbPassword ="";

//	private static final String configuration="jdbc:mysql://172.16.93.162/PDR_Calc";	
//	private static final String dbUser ="root";
//	private static final String dbPassword ="root";

	
	private static final DBConnectionBuilder instance = new DBConnectionBuilder();

	private DBConnectionBuilder() {

	}

	public static DBConnectionBuilder getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn =(Connection) DriverManager.getConnection(configuration,dbUser,dbPassword);
		conn.setReadOnly(false);
		conn.setAutoCommit(false);
		return conn;
	}
}
