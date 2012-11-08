package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import visitors.GeneralParameterVisitor;
import CommonsObjects.GeneralParameter;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class RawParameterDao {

	private static final String QUERY9 = "select RawParamName, RawParamType, RawParamValue, ParentParamName from DefaultRawParameter";

	private static final RawParameterDao instance = new RawParameterDao();

	public static RawParameterDao getInstance() {
		return instance;
	}

	private RawParameterDao() {
	}

	public Map<String, GeneralParameter> getRawParameters()
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Map<String, GeneralParameter> toReturn = new HashMap<String, GeneralParameter>();

		PreparedStatement ps = conn.prepareStatement(QUERY9);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String paramName = rs.getString("RawParamName");
			String paramType = rs.getString("RawParamType");
			String paramValue = rs.getString("RawParamValue");
			String parentName = rs.getString("ParentParamName");

			GeneralParameter temp = new GeneralParameter(paramValue, paramType,
					parentName, new GeneralParameterVisitor());
			toReturn.put(paramName, temp);

		}
		conn.close();
		return toReturn;

	}

	public Map<String, String> getParentParamTypes() throws SQLException,
			ClassNotFoundException {
		Map<String, String> toReturn = new HashMap<String, String>();

		String query = "SELECT ParentParamName, ParentType FROM DefaultRawParameter GROUP BY ParentParamName";

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String paramName = rs.getString("ParentParamName");
			String paramTYpe = rs.getString("ParentType");
			toReturn.put(paramName, paramTYpe);
		}
		conn.close();

		return toReturn;
	}

}
