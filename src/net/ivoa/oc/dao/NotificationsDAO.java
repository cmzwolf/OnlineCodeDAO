package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.ivoa.pdr.commons.DateFinder;
import net.ivoa.pdr.commons.JobBean;

/**
 * @author Carlo Maria Zwolf Observatoire de Paris LERMA
 */

public class NotificationsDAO {
	private static final NotificationsDAO instance = new NotificationsDAO();

	public static NotificationsDAO getInstance() {
		return instance;
	}

	private NotificationsDAO() {
	}

	public List<Boolean> hasToSendMailToUser(Integer IdUser, Integer jobId)
			throws SQLException, ClassNotFoundException {
		List<Boolean> toReturn = new ArrayList<Boolean>();
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select UserWantMail from Notifications where IdUser=? and IdConfig=?";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, IdUser);
		ps2.setInt(2, jobId);

		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getBoolean("UserWantMail"));
		}
		conn.close();
		return toReturn;
	}

	public List<Boolean> hasToSentMailToUserNotNotified(Integer IdUser,
			Integer jobId) throws SQLException, ClassNotFoundException {
		List<Boolean> toReturn = new ArrayList<Boolean>();
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select UserWantMail from Notifications where IdUser=? and IdConfig=? and Notified=false";
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setInt(1, IdUser);
		ps2.setInt(2, jobId);

		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(rs.getBoolean("UserWantMail"));
		}
		conn.close();
		return toReturn;

	}

	public void cutLinkUserJob(Integer idUser, Integer idJob)
			throws SQLException, ClassNotFoundException {
		String query = "delete from Notifications where IdUser =? and IdConfig=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query);

		ps.setInt(1, idUser);
		ps.setInt(2, idJob);

		ps.execute();
		ps.close();
		conn.close();
	}

	public void updateNotifications(Integer idUser, Integer idConfig,
			String gridId, String jobNickName, Boolean userWantMail)
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "insert into Notifications (IdUser, IdConfig, Notified, DemandDate, IdGrid, JobNickname, UserWantMail) values (?,?,0,?,?,?,?)";

		String demandDate = DateFinder.getInstance().getCurrentDate();
		PreparedStatement ps = conn.prepareStatement(query);

		ps.setInt(1, idUser);
		ps.setInt(2, idConfig);
		ps.setString(3, demandDate);

		ps.setString(4, gridId);
		ps.setString(5, jobNickName);

		ps.setBoolean(6, userWantMail);

		ps.execute();
		ps.close();

		conn.close();

		NotificationsDAO.getInstance().updatePermanentConfiguration(idUser,
				idConfig, demandDate);

	}

	private void updatePermanentConfiguration(Integer idUser, Integer idConfig,
			String demandDate) throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "insert into PermanentNotification (IdUser, IdConfig, DemandDate) values (?,?,?)";

		PreparedStatement ps = conn.prepareStatement(query);

		ps.setInt(1, idUser);
		ps.setInt(2, idConfig);
		ps.setString(3, demandDate);

		ps.execute();
		ps.close();
		conn.commit();

		conn.close();
	}

}
