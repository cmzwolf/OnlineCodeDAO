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

public class InputFileDao {
	private static final InputFileDao instance = new InputFileDao();

	public static InputFileDao getInstance() {
		return instance;
	}

	private InputFileDao() {
	}

	public List<IOFile> getInputFilesList() throws SQLException, ClassNotFoundException {

		Connection conn = DBConnectionBuilder.getInstance().getConnection();

		List<IOFile> toReturn = new ArrayList<IOFile>();

		String query = "select fileExtension, filePattern, InputDir from Inputs";

		PreparedStatement ps2 = conn.prepareStatement(query);
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
			toReturn.add(new IOFile(rs.getString("filePattern"), rs.getString("fileExtension"), rs.getString("InputDir")));
		}
		conn.close();
		return toReturn;
	}

}
