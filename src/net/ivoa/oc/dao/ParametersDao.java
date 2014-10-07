package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ivoa.pdr.commons.ParamConfiguration;

/**
 * @author Carlo Maria Zwolf Observatoire de Paris LERMA
 */

public class ParametersDao {
	private static final ParametersDao instance = new ParametersDao();

	public static ParametersDao getInstance() {
		return instance;
	}

	private ParametersDao() {
	}

	public Integer persistConfigurationAndGetId(
			ParamConfiguration configuration, Integer userId, String gridID,
			String jobNickName, Boolean mailRequested) throws SQLException,
			ClassNotFoundException {

		Integer IdConfig = ParametersDao.getInstance()
				.getIdExistingConfiguration(configuration);

		if (null == IdConfig) {
			IdConfig = JobDAO.getInstance().createNewJobAndGetIdConfig();
			ParametersDao.getInstance().insertConfigurationValues(
					configuration, IdConfig);
		}
		NotificationsDAO.getInstance().updateNotifications(userId, IdConfig,gridID,jobNickName,mailRequested);

		return IdConfig;
	}

	private void insertConfigurationValues(ParamConfiguration configuration,
			Integer idConfig) throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		for (Map.Entry<String, String> entry : configuration.getParamMap()
				.entrySet()) {
			ParametersDao.getInstance().insertParam(entry.getKey(),
					entry.getValue(), idConfig, conn);
		}
		conn.close();
	}

	private void insertParam(String paramName, String paramValue,
			Integer idConfig, Connection conn) throws SQLException {
		String query = "insert into ConfigurationsDetails (IdConfig, ParamName, ParamValue) values (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);

		ps.setInt(1, idConfig);
		ps.setString(2, paramName);
		ps.setString(3, paramValue);

		ps.execute();
		ps.close();
	}

	public Integer getIdExistingConfiguration(ParamConfiguration configuration)
			throws SQLException, ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		Integer toReturn = null;
		List<String> queries = new ArrayList<String>();
		int i = 0;

		for (Map.Entry<String, String> entry : configuration.getParamMap()
				.entrySet()) {
			String tableName01 = "config" + i;
			String tableName02 = "job" + i;
			String query = "select " + tableName01
					+ ".IdConfig from ConfigurationsDetails " + tableName01
					+ ", Job " + tableName02 + " where " + tableName01
					+ ".IdConfig =" + tableName02 + ".IdConfig and "
					+ tableName01 + ".ParamName='" + entry.getKey() + "' and "
					+ tableName01 + ".ParamValue ='" + entry.getValue()
					+ "' and " + tableName02
					+ ".IdService in (select max(IdService) from Service)";
			queries.add(query);
			i++;
		}

		String assembledQuery = "";

		for (int k = 0; k < queries.size(); k++) {
			assembledQuery = assembledQuery + queries.get(k);
			if (k < queries.size() - 1) {
				assembledQuery = assembledQuery + " and " + "config" + k
						+ ".IdConfig in (";
			}
		}
		for (int k = 1; k < queries.size(); k++) {
			assembledQuery = assembledQuery + ")";
		}

		PreparedStatement ps = conn.prepareStatement(assembledQuery);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn = rs.getInt(1);
		}
		conn.close();
		return toReturn;

	}

	public boolean doesConfigAlreadyExist(ParamConfiguration configuration)
			throws SQLException, ClassNotFoundException {

		return null != ParametersDao.getInstance().getIdExistingConfiguration(
				configuration);
	}

	public Map<String, String> getConfigurationMap(Integer idConfiguration)
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Map<String, String> toReturn = new HashMap<String, String>();

		String query = "select ParamName, ParamValue from ConfigurationsDetails where IdConfig=? order by ParamName";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idConfiguration);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn.put(rs.getString("ParamName"), rs.getString("ParamValue"));
		}
		conn.close();
		return toReturn;
	}

}
