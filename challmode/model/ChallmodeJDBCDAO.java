package com.challmode.model;

import java.util.*;
import java.sql.*;

public class ChallmodeJDBCDAO implements ChallmodeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "AA103Kevin";
	String passwd = "m18680768";

		private static final String INSERT_STMT = 
			"INSERT INTO Challmode (challmode, challcontent) VALUES (Challmode_SEQ.NEXTVAL, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT challmode, challcontent FROM Challmode order by challmode";
		private static final String GET_ONE_STMT = 
			"SELECT challmode, challcontent FROM Challmode where challmode = ?";
		private static final String DELETE = 
			"DELETE FROM Challmode where challmode = ?";
		private static final String UPDATE = 
			"UPDATE Challmode set challcontent=? where challmode = ?";


	@Override
	public void insert(ChallmodeVO challmodeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, challmodeVO.getChallcontent());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ChallmodeVO challmodeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, challmodeVO.getChallcontent());
			pstmt.setInt(2, challmodeVO.getChallmode());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer challmode) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, challmode);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public ChallmodeVO findByPrimaryKey(Integer challmode) {

		ChallmodeVO challmodeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, challmode);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				challmodeVO = new ChallmodeVO();
				challmodeVO.setChallmode(rs.getInt("challmode"));
				challmodeVO.setChallcontent(rs.getString("challcontent"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return challmodeVO;
	}

	@Override
	public List<ChallmodeVO> getAll() {
		List<ChallmodeVO> list = new ArrayList<ChallmodeVO>();
		ChallmodeVO challmodeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				challmodeVO = new ChallmodeVO();
				challmodeVO.setChallmode(rs.getInt("challmode"));
				challmodeVO.setChallcontent(rs.getString("challcontent"));
				list.add(challmodeVO); // Store the row in the list
			}
//			System.out.println("已查詢到"+list.size()+"筆記錄");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		ChallmodeJDBCDAO dao = new ChallmodeJDBCDAO();

		 // 新增
		 ChallmodeVO challmodeVO1 = new ChallmodeVO();
		 challmodeVO1.setChallcontent("隨便打");
		 dao.insert(challmodeVO1);
		//
//		 // 修改
//		 ChallmodeVO challmodeVO2 = new ChallmodeVO();
//		 challmodeVO2.setChallmode(7);
//		 challmodeVO2.setChallcontent("不要隨便打");
//		 dao.update(challmodeVO2);
		//
		// 刪除
//		dao.delete(7);
//
//		 // 查詢
//		 ChallmodeVO challmodeVO3 = dao.findByPrimaryKey(3);
//		 System.out.print(challmodeVO3.getChallmode() + ",");
//		 System.out.println(challmodeVO3.getChallcontent() + ",");
//		 System.out.println("---------------------");
		//
		 // 查詢
//		 List<ChallmodeVO> list = dao.getAll();
//		 for (ChallmodeVO achallmode : list) {
//		 System.out.print(achallmode.getChallmode() + ",");
//		 System.out.print(achallmode.getChallcontent() + ",");
//		
//		 System.out.println();
//		 }
	}

}
