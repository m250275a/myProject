package adr.court;

import adr.tool.util.Common;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourtDaoMySqlImpl implements CourtDao {

	public CourtDaoMySqlImpl() {
		super();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(CourtVO court, byte[] courtimg) {
		int count = 0;
		String sql = "INSERT INTO Court"
				+ "(courtno, courtlat, courtlng, courtloc, courtname, courtdesc, courtimg) "
				+ "VALUES(Court_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setDouble(1, court.getCourtlat());
			ps.setDouble(2, court.getCourtlng());
			ps.setString(3, court.getCourtloc());
			ps.setString(4, court.getCourtname());
			ps.setString(5, court.getCourtdesc());
			ps.setBytes(6, courtimg);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int update(CourtVO court, byte[] courtimg) {
		int count = 0;
		String sql = "UPDATE Court SET courtlat = ?, courtlng = ?, courtloc = ?, courtname = ?, courtdesc = ?, courtimg = ? WHERE courtno = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setDouble(1, court.getCourtlat());
			ps.setDouble(2, court.getCourtlng());
			ps.setString(3, court.getCourtloc());
			ps.setString(4, court.getCourtname());
			ps.setString(5, court.getCourtdesc());
			ps.setBytes(6, courtimg);
			ps.setInt(7, court.getCourtno());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public int delete(Integer courtno) {
		int count = 0;
		String sql = "DELETE FROM Court WHERE courtno = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, courtno);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public byte[] getImage(Integer courtno) {
		String sql = "SELECT courtimg FROM Court WHERE courtno = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] courtimg = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setInt(1, courtno);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				courtimg = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return courtimg;
	}

	@Override
	public CourtVO findByCourtno(Integer courtno) {
		String sql = "SELECT courtlat, courtlng, courtloc, courtname, courtdesc FROM Court WHERE courtno = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		CourtVO court = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, courtno);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Double courtlat = rs.getDouble(1);
				Double courtlng = rs.getDouble(2);
				String courtloc = rs.getString(3);
				String courtname = rs.getString(4);
				String courtdesc = rs.getString(5);
				court = new CourtVO(courtno, courtlat, courtlng, courtloc, courtname, courtdesc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return court;
	}

	@Override
	public List<CourtVO> getAll() {
		String sql = "SELECT courtno, courtlat, courtlng, courtloc, courtname, courtdesc "
				+ "FROM Court ORDER BY courtno ASC ";
		Connection connection = null;
		PreparedStatement ps = null;
		List<CourtVO> courtList = new ArrayList<CourtVO>();
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer courtno = rs.getInt(1);
				Double courtlat = rs.getDouble(2);
				Double courtlng = rs.getDouble(3);
				String courtloc = rs.getString(4);
				String courtname = rs.getString(5);
				String courtdesc = rs.getString(6);
				CourtVO court = new CourtVO(courtno, courtlat, courtlng, courtloc, courtname,courtdesc);
				courtList.add(court);
			}
			return courtList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return courtList;
	}
}
