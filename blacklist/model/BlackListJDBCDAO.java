package com.blacklist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlackListJDBCDAO implements BlackListDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "basketball";
	String passwd = "1234";
	
	private static final String INSERT_STMT = 
			"INSERT INTO blacklist (blackno,memno,memedno) VALUES (BlackList_SEQ.nextval,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT blackno,memno,memedno FROM blacklist order by blackno";
		private static final String GET_ONE_STMT = 
			"SELECT blackno,memno,memedno FROM blacklist where blackno = ?";
		private static final String DELETE = 
			"DELETE FROM blacklist where blackno = ?";
		private static final String UPDATE = 
			"UPDATE blacklist set memno=?, memedno=? where blackno = ? ";
		private static final String GET_BY_MEMNO = 
			"SELECT blackno,memno,memedno FROM blacklist where memno = ?";
		
	@Override
	public void insert(BlackListVO blackListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			int cols[] = {1}; //或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			
			pstmt.setInt(1,blackListVO.getMemno());
			pstmt.setInt(2,blackListVO.getMemedno());

			pstmt.executeUpdate();
			ResultSet rsKeys = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rsKeys.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rsKeys.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rsKeys.getString(i);
						System.out.println("自增主鍵值(i=" + i + ") = " + key +"(剛新增成功的員工編號)");
					}
				} while (rsKeys.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(BlackListVO blackListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1,blackListVO.getMemno());
			pstmt.setInt(2,blackListVO.getMemedno());
			pstmt.setInt(3,blackListVO.getBlackno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(Integer blackno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, blackno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public BlackListVO findByPrimaryKey(Integer blackno) {
		BlackListVO blackListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, blackno);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				blackListVO = new BlackListVO();
				blackListVO.setBlackno(rs.getInt("blackno"));
				blackListVO.setMemno(rs.getInt("memno"));
				blackListVO.setMemedno(rs.getInt("memedno"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return blackListVO;
	}
	@Override
	public List<BlackListVO> getAll() {
		List<BlackListVO> list = new ArrayList<BlackListVO>();
		BlackListVO blackListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				blackListVO = new BlackListVO();
				blackListVO.setBlackno(rs.getInt("blackno"));
				blackListVO.setMemno(rs.getInt("memno"));
				blackListVO.setMemedno(rs.getInt("memedno"));
				list.add(blackListVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<BlackListVO> getByMemno(Integer memno){
		BlackListVO blackListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BlackListVO> list = new ArrayList<BlackListVO>();
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MEMNO);

			pstmt.setInt(1, memno);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				blackListVO = new BlackListVO();
				blackListVO.setBlackno(rs.getInt("blackno"));
				blackListVO.setMemno(rs.getInt("memno"));
				blackListVO.setMemedno(rs.getInt("memedno"));
				list.add(blackListVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

		BlackListJDBCDAO dao = new BlackListJDBCDAO();

		// 新增
		BlackListVO blackListVO1 = new BlackListVO();
		blackListVO1.setMemno(7010);
		blackListVO1.setMemedno(7014);
		dao.insert(blackListVO1);

		// 修改
//		BlackListVO blackListVO2 = new BlackListVO();
//		blackListVO2.setBlackno(10);
//		blackListVO2.setMemno(7010);
//		blackListVO2.setMemedno(7013);
//		dao.update(blackListVO2);

		

		// 查詢
//		BlackListVO blackListVO3 = dao.findByPrimaryKey(10);
//		System.out.print(blackListVO3.getBlackno()+",");
//		System.out.print(blackListVO3.getMemno()+",");
//		System.out.println(blackListVO3.getMemedno());
//		System.out.println("---------------------");

		// 查詢
//		List<BlackListVO> list = dao.getAll();
//		for (BlackListVO aBlackList : list) {
//			System.out.print(aBlackList.getBlackno() + ",");
//			System.out.print(aBlackList.getMemno() + ",");
//			System.out.print(aBlackList.getMemedno());
//			System.out.println();
//		}
//		List<BlackListVO> list = dao.getByMemno(7001);
//		for (BlackListVO aBlackList : list) {
//			System.out.print(aBlackList.getBlackno() + ",");
//			System.out.print(aBlackList.getMemno() + ",");
//			System.out.print(aBlackList.getMemedno());
//			System.out.println();
//		}
		// 刪除
			//	dao.delete(0011);
	}
}
