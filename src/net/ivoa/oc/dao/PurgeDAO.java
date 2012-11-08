package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ivoa.pdr.commons.DateFinder;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class PurgeDAO {
	private static final PurgeDAO instance = new PurgeDAO();

	public static PurgeDAO getInstance() {
		return instance;
	}

	private PurgeDAO() {
	}

	private Map<String, String> getInfMap(Connection conn) throws SQLException {

		Map<String, String> toReturn = new HashMap<String, String>();

		String query = "select ParamName , InfLimit from ParamLimits where InfLimit is not null";

		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn.put(rs.getString("ParamName"), rs.getString("InfLimit"));
		}
		return toReturn;
	}

	private Map<String, String> getMaxMap(Connection conn) throws SQLException {
		Map<String, String> toReturn = new HashMap<String, String>();

		String query = "select ParamName , SupLimit from ParamLimits where SupLimit is not null";

		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn.put(rs.getString("ParamName"), rs.getString("SupLimit"));
		}
		return toReturn;
	}

	public void purge() throws SQLException, ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Map<String, String> nameTypesMap = RawParameterDao.getInstance()
				.getParentParamTypes();

		Map<String, String> infMap = PurgeDAO.getInstance().getInfMap(conn);
		Map<String, String> supMap = PurgeDAO.getInstance().getMaxMap(conn);

		List<String> queries = new ArrayList<String>();

		for (Map.Entry<String, String> entry : infMap.entrySet()) {

			String paramName = entry.getKey();
			String paramValue = entry.getValue();

			String castTpe = PurgeDAO.getInstance().getCastTypeFromType(
					nameTypesMap.get(paramName));

			String query = "delete from ConfigurationsDetails where ParamName ='"
					+ paramName
					+ "' and cast(ParamValue as "
					+ castTpe
					+ ") < " + paramValue;

			queries.add(query);
		}

		for (Map.Entry<String, String> entry : supMap.entrySet()) {

			String paramName = entry.getKey();
			String paramValue = entry.getValue();

			String castTpe = PurgeDAO.getInstance().getCastTypeFromType(
					nameTypesMap.get(paramName));

			String query = "delete from ConfigurationsDetails where ParamName ='"
					+ paramName
					+ "' and cast(ParamValue as "
					+ castTpe
					+ ") > " + paramValue;

			queries.add(query);
		}

		queries.add("delete from Job where IdConfig not in (select distinct IdConfig from ConfigurationsDetails)");
		queries.add("delete from Notifications where IdConfig not in (select distinct IdConfig from ConfigurationsDetails)");
		queries.add("delete from Results where IdConfig not in (select distinct IdConfig from ConfigurationsDetails)");

		Statement st = conn.createStatement();
		for (String query : queries) {
			st.addBatch(query);
		}
		@SuppressWarnings("unused")
		int[] updatedCol = st.executeBatch();
		conn.close();

	}

	public void removeJobsFromDB(List<Integer> IdJobs)
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		List<String> queries = new ArrayList<String>();
		for (Integer currentId : IdJobs) {
			queries.add("delete from Job where IdConfig=" + currentId);
			queries.add("delete from Notifications where IdConfig=" + currentId);
			queries.add("delete from Results where IdConfig=" + currentId);
			queries.add("delete from ConfigurationsDetails where IdConfig="
					+ currentId);
		}
		Statement st = conn.createStatement();
		for (String query : queries) {
			st.addBatch(query);
		}
		@SuppressWarnings("unused")
		int[] updatedCol = st.executeBatch();
		conn.close();
	}

	public List<Integer> getIdJobsToOldAndDeleteThem() throws SQLException,
			ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Integer maxDurationForResults = GlobalTechConfigDAO.getInstance()
				.getMaxDurationForResults();

		if (null != maxDurationForResults || maxDurationForResults > 0) {
			List<Integer> toReturn = new ArrayList<Integer>();

			String query = "select IdConfig from Notifications group by IdConfig having max(NotificationDate)<?";

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(
					1,
					DateFinder.getInstance().getDateOfNDaysAgo(
							maxDurationForResults));

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				toReturn.add(rs.getInt("IdConfig"));

			}
			conn.close();
			return toReturn;
		} else {
			conn.close();
			return null;
		}
	}

	private String getCastTypeFromType(String Type) {
		if (Type.equalsIgnoreCase("real")) {
			return "decimal";
		}
		if (Type.equalsIgnoreCase("integer")) {
			return "signed";
		}
		return Type;
	}
}
