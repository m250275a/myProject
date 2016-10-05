package com.memsche.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class MemScheDAO implements MemScheDAO_interface {
	
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1,memScheVO.getMemno());
			pstmt.setInt(2,memScheVO.getFree());
			pstmt.setDate(3,memScheVO.getMdate());
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
	public void update(MemScheVO memScheVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setInt(1,memScheVO.getMemno());
			pstmt.setInt(2,memScheVO.getFree());
			pstmt.setDate(3,memScheVO.getMdate());
			pstmt.setInt(4,memScheVO.getMemsche());
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
	public void delete(Integer memsche) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memsche);

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
	public MemScheVO findByPrimaryKey(Integer memsche) {
		MemScheVO memScheVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
	
	@Override
	public List<MemScheVO> getByMemno(Integer memno) {
		List<MemScheVO> list = new ArrayList<MemScheVO>();
		MemScheVO memScheVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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