package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.ivoa.pdr.commons.DateFinder;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class NotificationsDAO {
	private static final NotificationsDAO instance = new NotificationsDAO();

	public static NotificationsDAO getInstance() {
		return instance;
	}

	private NotificationsDAO() {
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

	public void updateNotifications(Integer idUser, Integer idConfig)
			throws SQLException, ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "insert into Notifications (IdUser, IdConfig, Notified, DemandDate) values (?,?,0,?)";

		String demandDate = DateFinder.getInstance().getCurrentDate();
		PreparedStatement ps = conn.prepareStatement(query);

		ps.setInt(1, idUser);
		ps.setInt(2, idConfig);
		ps.setString(3, demandDate);

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
