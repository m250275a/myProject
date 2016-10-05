package com.admin.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

import com.admin.model.AdminDAO_interface;

public class AdminDAO implements AdminDAO_interface {

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


	private static final String INSERT_STMT = "INSERT INTO Admin (adminno,adminname,adminvarname,adminid,adminphone,adminaddr,adminpsw,adminemail,adminlevel)"
			+ "VALUES (admin_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT adminno,adminname,adminvarname,adminid,adminphone,adminaddr,adminpsw,adminemail,adminlevel FROM Admin order by adminno";
	private static final String GET_ONE_STMT = "SELECT adminno,adminname,adminvarname,adminid,adminphone,adminaddr,adminpsw,adminemail,adminlevel FROM Admin where adminno = ?";
	private static final String DELETE = "DELETE FROM Admin where adminno = ?";
	private static final String UPDATE = "UPDATE Admin set adminname=?, adminvarname=?, adminid=?, adminphone=?, adminaddr=?, adminpsw=?, adminemail=?, adminlevel=?"
			+ "where adminno =?";

	@Override
	public void insert(AdminVO adminVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminVO.getAdminname());
			pstmt.setString(2, adminVO.getAdminvarname());
			pstmt.setString(3, adminVO.getAdminid());
			pstmt.setString(4, adminVO.getAdminphone());
			pstmt.setString(5, adminVO.getAdminaddr());
			pstmt.setString(6, adminVO.getAdminpsw());
			pstmt.setString(7, adminVO.getAdminemail());
			pstmt.setString(8, adminVO.getAdminlevel());

			pstmt.executeUpdate();

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
	public void update(AdminVO adminVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminVO.getAdminname());
			pstmt.setString(2, adminVO.getAdminvarname());
			pstmt.setString(3, adminVO.getAdminid());
			pstmt.setString(4, adminVO.getAdminphone());
			pstmt.setString(5, adminVO.getAdminaddr());
			pstmt.setString(6, adminVO.getAdminpsw());
			pstmt.setString(7, adminVO.getAdminemail());
			pstmt.setString(8, adminVO.getAdminlevel());
			pstmt.setInt(9, adminVO.getAdminno());

			pstmt.executeUpdate();

			
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
	public void delete(Integer adminno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, adminno);

			pstmt.executeUpdate();

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
	public AdminVO findByPrimaryKey(Integer adminno) {

		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, adminno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				adminVO = new AdminVO();
				adminVO.setAdminno(rs.getInt("adminno"));
				adminVO.setAdminname(rs.getString("adminname"));
				adminVO.setAdminvarname(rs.getString("adminvarname"));
				adminVO.setAdminid(rs.getString("adminid"));
				adminVO.setAdminphone(rs.getString("adminphone"));
				adminVO.setAdminaddr(rs.getString("adminaddr"));
				adminVO.setAdminpsw(rs.getString("adminpsw"));
				adminVO.setAdminemail(rs.getString("adminemail"));
				adminVO.setAdminlevel(rs.getString("adminlevel"));
				
			}
			
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
		return adminVO;
	}

	@Override
	public List<AdminVO> getAll() {
		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO adminVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				adminVO = new AdminVO();
				adminVO.setAdminno(rs.getInt("adminno"));
				adminVO.setAdminname(rs.getString("adminname"));
				adminVO.setAdminvarname(rs.getString("adminvarname"));
				adminVO.setAdminid(rs.getString("adminid"));
				adminVO.setAdminphone(rs.getString("adminphone"));
				adminVO.setAdminaddr(rs.getString("adminaddr"));
				adminVO.setAdminpsw(rs.getString("adminpsw"));
				adminVO.setAdminemail(rs.getString("adminemail"));
				adminVO.setAdminlevel(rs.getString("adminlevel"));
				list.add(adminVO); // Store the row in the list
			}

		
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
}