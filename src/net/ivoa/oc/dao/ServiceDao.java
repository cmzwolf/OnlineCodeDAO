package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.ivoa.pdr.commons.Service;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class ServiceDao {

	private static final String QUERY9 = "select IdService, description, MaxSimAutorized from Service order by IdService desc limit 0,1";

	private static final ServiceDao instance = new ServiceDao();

	public static ServiceDao getInstance() {
		return instance;
	}

	private ServiceDao() {
	}

	public Service getCurrentService() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Integer idService = null;
		String description = null;
		Integer maxAutorizedSims = null;

		PreparedStatement ps = conn.prepareStatement(QUERY9);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			idService = rs.getInt("IdService");
			description = rs.getString("description");
			maxAutorizedSims = rs.getInt("MaxSimAutorized");
		}
		conn.close();
		return new Service(idService, description, maxAutorizedSims);

	}

}
