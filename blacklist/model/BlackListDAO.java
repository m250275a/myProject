package com.blacklist.model;


import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BlackListDAO implements BlackListDAO_interface{
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
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
				"SELECT blackno,memedno FROM blacklist where memno = ?";
		
	@Override
	public void insert(BlackListVO blackListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1,blackListVO.getMemno());
			pstmt.setInt(2,blackListVO.getMemedno());
			pstmt.setInt(3,blackListVO.getBlackno());

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, blackno);

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMNO);

			pstmt.setInt(1, memno);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				blackListVO = new BlackListVO();
				
				blackListVO.setBlackno(rs.getInt("blackno"));
				blackListVO.setMemedno(rs.getInt("memedno"));
				list.add(blackListVO); // Store the row in the list
			}

			// Handle any driver errors
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