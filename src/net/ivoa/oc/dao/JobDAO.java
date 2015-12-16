package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ivoa.pdr.commons.DateFinder;
import net.ivoa.pdr.commons.JobBean;

/**
 * @author Carlo Maria Zwolf Observatoire de Paris LERMA
 */

public class JobDAO {
	private static final JobDAO instance = new JobDAO();
	private static final String QUERY1 = "insert into Job (IdService,IdConfig,Processed,Finished,hasError,DemandDate) values( (select max(IdService) from Service), (select ifnull( (select 1+max(IdConfig) from ConfigurationsDetails) ,1)), 0 ,0 ,0 , ?)";
	private static final String QUERY2 = "select max(IdConfig) from Job";

	public static JobDAO getInstance() {
		return instance;
	}

	private JobDAO() {
	}

	public List<Integer> getListOfJobsAskedByUser(Integer idUser)
			throws SQLException, ClassNotFoundException {
		List<Integer> toReturn = new ArrayList<Integer>();

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select distinct IdConfig from Notifications where IdUser=? order by IdConfig desc";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idUser);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getInt(1));
		}
		conn.close();
		return toReturn;
	}

	public List<Integer> getListOfJobsAskedByUserAndGridId(Integer idUser,
			String gridId) throws SQLException, ClassNotFoundException {
		List<Integer> toReturn = new ArrayList<Integer>();

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select distinct IdConfig from Notifications where IdUser=? and IdGrid=? order by IdConfig desc";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idUser);
		ps.setString(2, gridId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getInt(1));
		}
		conn.close();
		return toReturn;
	}

	public Integer createNewJobAndGetIdConfig() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		Integer toReturn = null;

		PreparedStatement ps = conn.prepareStatement(QUERY1);
		ps.setString(1, DateFinder.getInstance().getCurrentDate());
		ps.execute();
		ps.close();

		PreparedStatement ps2 = conn.prepareStatement(QUERY2);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn = rs.getInt(1);
		}
		conn.commit();
		conn.close();
		return toReturn;
	}

	public List<Integer> getNotProcessedJobs() throws SQLException,
			ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		List<Integer> toReturn = new ArrayList<Integer>();

		String query = "select IdConfig from Job where Processed=0";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getInt(1));
		}
		conn.close();
		return toReturn;
	}

	public void markJobAsProcessed(Integer idConfiguration)
			throws SQLException, ClassNotFoundException {
		String query = "update Job set Processed=1, ProcessingDate=? where IdConfig=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, DateFinder.getInstance().getCurrentDate());
		ps.setInt(2, idConfiguration);
		ps.execute();
		conn.commit();
		conn.close();

	}

	public void markJobAsFinished(Integer idConfiguration) throws SQLException,
			ClassNotFoundException {
		String query = "update Job set Finished=1, FinishingDate=? where IdConfig=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, DateFinder.getInstance().getCurrentDate());
		ps.setInt(2, idConfiguration);
		ps.execute();

		conn.close();
	}

	public List<Integer> getProcessedJobs() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		List<Integer> toReturn = new ArrayList<Integer>();

		String query = "select IdConfig from Job where Processed=1 and Finished=0";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getInt(1));
		}
		conn.close();
		return toReturn;
	}

	public List<Integer> getFinishedJobsToBeNotified() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		List<Integer> toReturn = new ArrayList<Integer>();

		String query = "select distinct J.IdConfig from Job J , Notifications N where J.Processed=1 and J.Finished=1 and N.Notified=0 and N.IdConfig=J.IdConfig";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getInt(1));
		}
		conn.close();
		return toReturn;
	}

	public void insertResults(Integer idConfiguration, String urlResult,
			String resultName) throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "insert into Results (IdConfig, URLResults, ResultName) values (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idConfiguration);
		ps.setString(2, urlResult);
		ps.setString(3, resultName);
		ps.execute();
		conn.commit();
		conn.close();
	}

	public Map<String, String> getResultsFromIdJob(Integer idConfiguration)
			throws SQLException, ClassNotFoundException {
		Map<String, String> toReturn = new HashMap<String, String>();
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		String query = "select URLResults, ResultName from Results where IdConfig=?";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, idConfiguration);

		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			String key = rs.getString("ResultName");
			String value = rs.getString("URLResults");
			toReturn.put(key, value);
		}
		conn.commit();
		conn.close();

		return toReturn;
	}

	public List<Map<String, String>> getFinishedResultsByGridAndUser(
			String gridId, Integer idUser) throws ClassNotFoundException, SQLException {
		
		List<Map<String,String>> toReturn = new ArrayList<Map<String,String>>();
		List<Integer> idJobFinishedIntoGrid=new ArrayList<Integer>();
		
		String query = "select distinct N.IdConfig from Notifications N, Job J where J.IdConfig = N.IdConfig and J.Finished=1 and N.IdUser=? and N.IdGrid=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idUser);
		ps.setString(2, gridId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Integer currentId = rs.getInt(1);
			idJobFinishedIntoGrid.add(currentId);
		}
		conn.commit();
		conn.close();

		for(Integer currentJobId : idJobFinishedIntoGrid){
			toReturn.add(JobDAO.getInstance().getResultsFromIdJob(currentJobId));
		}
		return toReturn;
	}

	public String getDateWhereUserAskedTheJob(Integer idUser, Integer idJob)
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		String toReturn = null;

		String query = "select max(DemandDate) from Notifications where IdUser=? and IdConfig=?";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, idUser);
		ps2.setInt(2, idJob);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn = rs.getString(1);
		}
		conn.close();
		return toReturn;
	}

	public String getDateWhereUserReceiveNotificationForJob(Integer idUser,
			Integer idJob) throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		String toReturn = null;

		String query = "select max(NotificationDate) from Notifications where IdUser=? and IdConfig=?";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, idUser);
		ps2.setInt(2, idJob);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn = rs.getString(1);
		}
		conn.close();
		return toReturn;
	}

	public JobBean getJobBeanFromIdJob(Integer idJob) throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		JobBean toReturn = new JobBean();

		String query = "select IdConfig, IdService, Processed, Finished, hasError, DemandDate, ProcessingDate, FinishingDate from Job where IdConfig=?";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, idJob);

		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			Integer idConfigInDB = rs.getInt("IdConfig");
			Integer idService = rs.getInt("IdService");
			Boolean processed = rs.getBoolean("Processed");
			Boolean finished = rs.getBoolean("Finished");
			Boolean hasError = rs.getBoolean("hasError");
			String dateDemand = rs.getString("Processed");
			String processingDate = rs.getString("ProcessingDate");
			String finishingDate = rs.getString("FinishingDate");

			toReturn.setJobValid(idJob.equals(idConfigInDB));
			toReturn.setIdJob(idJob);
			toReturn.setIdService(idService);
			toReturn.setJobProcessed(processed);
			toReturn.setJobfinished(finished);
			toReturn.setDemandDate(dateDemand);
			toReturn.setProcessingDate(processingDate);
			toReturn.setFinishingDate(finishingDate);
			toReturn.setHasError(hasError);

			if (toReturn.isJobValid()) {
				toReturn.setJobResults(JobDAO.getInstance()
						.getResultsFromIdJob(idJob));
				toReturn.setJobConfiguration(ParametersDao.getInstance()
						.getConfigurationMap(idJob));
				toReturn.setUserToNotify(UserDAO.getInstance()
						.getMailToNotifyForGivenJob(idJob));
				toReturn.setUserAskedThisJob(UserDAO.getInstance()
						.getUserThatAskedForAJob(idJob));

			} else {
				System.out.println("Warning!!! The Job " + idJob
						+ "is marked as invalid (idJob=" + idJob
						+ " and IdCOnfig= " + idConfigInDB + ")");
			}

		}
		conn.close();
		return toReturn;
	}

	public JobBean getJobBeanFromIdJobLight(Integer idJob) throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		JobBean toReturn = new JobBean();

		String query = "select IdConfig, IdService, Processed, Finished, DemandDate, ProcessingDate, FinishingDate from Job where IdConfig=?";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, idJob);

		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			Integer idConfigInDB = rs.getInt("IdConfig");
			Integer idService = rs.getInt("IdService");
			Boolean processed = rs.getBoolean("Processed");
			Boolean finished = rs.getBoolean("Finished");
			String dateDemand = rs.getString("Processed");
			String processingDate = rs.getString("ProcessingDate");
			String finishingDate = rs.getString("FinishingDate");

			toReturn.setJobValid(idJob.equals(idConfigInDB));
			toReturn.setIdJob(idJob);
			toReturn.setIdService(idService);
			toReturn.setJobProcessed(processed);
			toReturn.setJobfinished(finished);
			toReturn.setDemandDate(dateDemand);
			toReturn.setProcessingDate(processingDate);
			toReturn.setFinishingDate(finishingDate);

		}
		conn.close();
		return toReturn;
	}

	public void markJobAsHavingErrors(Integer idConfiguration)
			throws ClassNotFoundException, SQLException {
		String query = "update Job set hasError=1 where IdConfig=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idConfiguration);
		ps.execute();
		conn.close();
	}

}
