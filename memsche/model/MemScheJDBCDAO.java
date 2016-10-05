package com.memsche.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MemScheJDBCDAO implements MemScheDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "basketball";
	String passwd = "1234";
	
	private static final String INSERT_STMT = 
			"INSERT INTO MemSche (memsche,memno,free,mdate) VALUES (MemSche_SEQ.nextval,?,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT memsche,memno,free,mdate FROM MemSche order by memsche";
		private static final String GET_ONE_STMT = 
			"SELECT memsche,memno,free,mdate FROM MemSche where memsche = ?";
		private static final String DELETE = 
			"DELETE FROM MemSche where memsche = ?";
		private static final String UPDATE = 
			"UPDATE MemSche set memno=?,free=?,mdate=? where memsche = ?";
		private static final String GET_BY_MEMNO = 
				"SELECT memsche,memno,free,mdate FROM MemSche where memno = ?";
	@Override
	public void insert(MemScheVO memScheVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,memScheVO.getMemno());
			pstmt.setInt(2,memScheVO.getFree());
			pstmt.setDate(3,memScheVO.getMdate());
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
	public void update(MemScheVO memScheVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setInt(1,memScheVO.getMemno());
			pstmt.setInt(2,memScheVO.getFree());
			pstmt.setDate(3,memScheVO.getMdate());
			pstmt.setInt(4,memScheVO.getMemsche());
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
	public void delete(Integer memsche) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memsche);

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
	public MemScheVO findByPrimaryKey(Integer memsche) {
		MemScheVO memScheVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memsche);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memScheVO = new MemScheVO();
				memScheVO.setMemsche(rs.getInt("memsche"));
				memScheVO.setMemno(rs.getInt("memno"));
				memScheVO.setFree(rs.getInt("free"));
				memScheVO.setMdate(rs.getDate("mdate"));
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
		return memScheVO;
	}

	@Override
	public List<MemScheVO> getAll() {
		List<MemScheVO> list = new ArrayList<MemScheVO>();
		MemScheVO memScheVO = null;

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
				memScheVO = new MemScheVO();
				memScheVO.setMemsche(rs.getInt("memsche"));
				memScheVO.setMemno(rs.getInt("memno"));
				memScheVO.setFree(rs.getInt("free"));
				memScheVO.setMdate(rs.getDate("mdate"));
				list.add(memScheVO); // Store the row in the list
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

		MemScheJDBCDAO dao = new MemScheJDBCDAO();

		// 新增
//		MemScheVO memScheVO1 = new MemScheVO();
//		memScheVO1.setMemno(7012);
//		memScheVO1.setFree(3);
//		memScheVO1.setMdate(java.sql.Date.valueOf("2016-08-28"));
//		dao.insert(memScheVO1);

		// 修改
//		MemScheVO memScheVO2 = new MemScheVO();
//		memScheVO2.setMemsche(11);
//		memScheVO2.setMemno(7012);
//		memScheVO2.setFree(3);
//		memScheVO2.setMdate(java.sql.Date.valueOf("2016-08-28"));
//		dao.update(memScheVO2);

		

		// 查詢
//		MemScheVO memScheVO3 = dao.findByPrimaryKey(10);
//		System.out.print(memScheVO3.getMemsche()+",");
//		System.out.print(memScheVO3.getMemno()+",");
//		System.out.println(memScheVO3.getFree());
//		System.out.println(memScheVO3.getMdate());
//		System.out.println("---------------------");

		// 查詢
//		List<MemScheVO> list = dao.getAll();
//		for (MemScheVO aBlackList : list) {
//			System.out.print(aBlackList.getMemsche() + ",");
//			System.out.print(aBlackList.getMemno()+ ",");
//			System.out.print(aBlackList.getFree()+ ",");
//			System.out.print(aBlackList.getMdate());
//			System.out.println();
//		}
		
		List<MemScheVO> list = dao.getByMemno(7001);
		for (MemScheVO aBlackList : list) {
			System.out.print(aBlackList.getMemsche() + ",");
			System.out.print(aBlackList.getMemno()+ ",");
			System.out.print(aBlackList.getFree()+ ",");
			System.out.print(aBlackList.getMdate());
			System.out.println();
		}
		
		// 刪除
//		dao.delete(11);
	}

	@Override
	public List<MemScheVO> getByMemno(Integer memno) {
		List<MemScheVO> list = new ArrayList<MemScheVO>();
		MemScheVO memScheVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MEMNO);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				memScheVO = new MemScheVO();
				memScheVO.setMemsche(rs.getInt("memsche"));
				memScheVO.setMemno(rs.getInt("memno"));
				memScheVO.setFree(rs.getInt("free"));
				memScheVO.setMdate(rs.getDate("mdate"));
				list.add(memScheVO); // Store the row in the list
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
}
