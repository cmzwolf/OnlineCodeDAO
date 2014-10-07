package net.ivoa.oc.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

/**
 * @author Carlo Maria Zwolf Observatoire de Paris LERMA
 */

public class DBConnectionBuilder {

	private static String configuration = "jdbc:mysql://localhost:3306/ParisDurham";
	private static String dbUser = "";
	private static String dbPassword = "";

	// private static final String
	// private static final String dbUser ="root";
	// private static final String dbPassword ="root";

	private static final DBConnectionBuilder instance = new DBConnectionBuilder();

	private DBConnectionBuilder() {

	}

	public static DBConnectionBuilder getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException,
			ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = (Connection) DriverManager.getConnection(
				configuration, dbUser, dbPassword);
		conn.setReadOnly(false);
		conn.setAutoCommit(false);
		return conn;
	}
}
