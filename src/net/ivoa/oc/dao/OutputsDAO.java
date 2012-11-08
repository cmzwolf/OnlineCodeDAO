package net.ivoa.oc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.ivoa.pdr.commons.IOFile;

/**
 * @author Carlo Maria Zwolf
 * Observatoire de Paris
 * LERMA
 */

public class OutputsDAO {
	private static final OutputsDAO instance = new OutputsDAO();

	public static OutputsDAO getInstance() {
		return instance;
	}

	private OutputsDAO() {
	}
	
	public List<IOFile> getOutputsFilesList() throws SQLException, ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		List<IOFile> toReturn = new ArrayList<IOFile>();

		String query = "select fileExtension, OutputDir from Outputs";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(new IOFile(null, rs.getString("fileExtension"), rs.getString("OutputDir")));
		}
		conn.close();
		return toReturn;
	}
}
