package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class GlobalTechConfigDAO {
	private static final GlobalTechConfigDAO instance = new GlobalTechConfigDAO();

	public static GlobalTechConfigDAO getInstance() {
		return instance;
	}

	private GlobalTechConfigDAO() {
	}

	public Integer getMaxDurationForResults() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Integer toReturn = null;

		String query = "select MaxResultDuration from GlobalTechConfig";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn = rs.getInt(1);
		}
		conn.close();

		return toReturn;
	}

	public String getServletContainer() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String toReturn = null;

		String query = "select ServeletContainerAdress from GlobalTechConfig";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn = rs.getString(1);
		}
		conn.close();

		return toReturn;
	}

}
