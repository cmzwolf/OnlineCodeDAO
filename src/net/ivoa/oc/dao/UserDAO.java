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

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class UserDAO {

	private static final UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	private UserDAO() {
	}

	public Integer getIdUserByMail(String mail) throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnectionBuilder.getInstance().getConnection();
		Integer idUser = UserDAO.getInstance().getIdExistingUserByMail(mail,
				conn);
		if (null == idUser) {
			UserDAO.getInstance().insertUser(mail, conn);
			idUser = UserDAO.getInstance().getIdExistingUserByMail(mail, conn);
		}

		conn.close();
		return idUser;
	}
	
	public String getMailFromUserId(Integer userId) throws SQLException, ClassNotFoundException{
		Connection conn= DBConnectionBuilder.getInstance().getConnection();
		String query = "select Email from User where IdUser =?";
		String toReturn = null;
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			toReturn = rs.getString(1);
		}
		conn.close();
		return toReturn;
	}
	

	private void insertUser(String mail, Connection conn) throws SQLException {
		String query = "insert into User (Email) values (?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mail);
		ps.execute();
	}

	private Integer getIdExistingUserByMail(String mail, Connection conn)
			throws SQLException {
		String query = "select IdUser from User where Email=?";
		Integer toReturn = null;
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mail);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			toReturn = rs.getInt(1);
		}
		return toReturn;
	}

	public Map<Integer, String> getMailToNotifyForGivenJob(Integer idConfig)
			throws SQLException, ClassNotFoundException {
		Map<Integer, String> toReturn = new HashMap<Integer, String>();
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select U.IdUser, U.Email from User U, Notifications N where N.IdUser = U.idUser and IdConfig=? and Notified=0";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idConfig);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			toReturn.put(rs.getInt(1), rs.getString(2));
		}
		conn.close();

		return toReturn;
	}
	
	public List<List<String>> getUserThatAskedForAJob(Integer idConfig) throws SQLException, ClassNotFoundException{
		List<List<String>> toReturn = new ArrayList<List<String>>();
		
		List<String> userMail = new ArrayList<String>();
		List<String> dateDemande =  new ArrayList<String>();
		
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		String query = "select U.Email, N.DemandDate from User U, Notifications N where N.IdUser = U.idUser and IdConfig=?";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idConfig);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			userMail.add(rs.getString(1));
			dateDemande.add(rs.getString(2));
		}
		conn.close();
		toReturn.add(userMail);
		toReturn.add(dateDemande);
		
		return toReturn;
	}

	public void markUserAsNotifiedForGivenJob(Integer idUser,
			Integer idConfiguration) throws SQLException,
			ClassNotFoundException {
		String query = "update Notifications set Notified=1, NotificationDate=? where IdConfig=? and IdUser=?";
		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, DateFinder.getInstance().getCurrentDate());
		ps.setInt(2, idConfiguration);
		ps.setInt(3, idUser);
		ps.execute();

		conn.close();
	}

}
