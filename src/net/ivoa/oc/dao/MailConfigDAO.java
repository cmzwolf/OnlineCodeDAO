package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.ivoa.pdr.commons.MailConfig;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class MailConfigDAO {
	private static final MailConfigDAO instance = new MailConfigDAO();

	public static MailConfigDAO getInstance() {
		return instance;
	}

	private MailConfigDAO() {
	}
	
	public MailConfig getMailConfig() throws SQLException, ClassNotFoundException{
		MailConfig toReturn = new MailConfig();
		
		Connection conn = DBConnectionBuilder.getInstance().getConnection();


		String query = "select ServerName, UserName, Password, FromAdress, FromLabel, Subject from MailConfig";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.setServerName(rs.getString("ServerName"));
			toReturn.setUserName(rs.getString("UserName"));
			toReturn.setPassword(rs.getString("Password"));
			toReturn.setFromAdress(rs.getString("FromAdress"));
			toReturn.setFromLabel(rs.getString("FromLabel"));
			toReturn.setSubject(rs.getString("Subject"));
		}
		conn.close();
		
		return toReturn;
	}
}
