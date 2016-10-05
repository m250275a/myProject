package com.personalmsg.model;

import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;

public class PersonalMsgDAO implements PersonalMsgDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO personalmsg (msgno,memno,memedno,msg,msgdate) VALUES (personalmsg_seq.NEXTVAL, ?, ?, ?,?)";
	private static final String INSERT_STMT_NEW = "INSERT INTO personalmsg (msgno,memno,memedno,msg,msgdate) VALUES (personalmsg_seq.NEXTVAL, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT msgno,memno,memedno,msg,to_char(msgdate,'yyyy-mm-dd hh:mi:ss')msgdate FROM personalmsg order by msgno";
	private static final String GET_ONE_STMT = "SELECT msgno,memno,memedno,msg,to_char(msgdate,'yyyy-mm-dd hh:mi:ss')msgdate FROM personalmsg where msgno = ?";
	private static final String Get_Msgs_ByMemno_STMT = "SELECT msgno,memno,memedno,msg,to_char(msgdate,'yyyy-mm-dd hh:mi:ss')msgdate FROM (select * from personalmsg where memedno =? order by msgdate desc)where ROWNUM <= 5";
	private static final String DELETE = "DELETE FROM personalmsg where msgno = ?";
	private static final String UPDATE = "UPDATE personalmsg set memno=?, memedno=?, msg=?, msgdate=? where msgno=?";
	private static final String GET_MEMNOS_STMT = "SELECT memedno FROM Personalmsg GROUP BY memedno";

	@Override
	public void insert(PersonalMsgVO personalMsgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, personalMsgVO.getMemno());
			pstmt.setInt(2, personalMsgVO.getMemedno());
			pstmt.setString(3, personalMsgVO.getMsg());
			pstmt.setTimestamp(4, personalMsgVO.getMsgdate());

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
	public void update(PersonalMsgVO personalMsgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, personalMsgVO.getMemno());
			pstmt.setInt(2, personalMsgVO.getMemedno());
			pstmt.setString(3, personalMsgVO.getMsg());
			pstmt.setTimestamp(4, personalMsgVO.getMsgdate());
			pstmt.setInt(5, personalMsgVO.getMsgno());
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
	public void delete(Integer msgno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, msgno);

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
	public PersonalMsgVO findByPrimaryKey(Integer msgno) {

		PersonalMsgVO personalMsgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, msgno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMsgno(rs.getInt("msgno"));
				personalMsgVO.setMemno(rs.getInt("memno"));
				personalMsgVO.setMemedno(rs.getInt("memedno"));
				personalMsgVO.setMsg(rs.getString("msg"));
				personalMsgVO.setMsgdate(rs.getTimestamp("msgdate"));

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
		return personalMsgVO;
	}

	@Override
	public List<PersonalMsgVO> getAll() {
		List<PersonalMsgVO> list = new ArrayList<PersonalMsgVO>();
		PersonalMsgVO personalMsgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMsgno(rs.getInt("msgno"));
				personalMsgVO.setMemno(rs.getInt("memno"));
				personalMsgVO.setMemedno(rs.getInt("memedno"));
				personalMsgVO.setMsg(rs.getString("msg"));
				personalMsgVO.setMsgdate(rs.getTimestamp("msgdate"));
				list.add(personalMsgVO); // Store the row in the list
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

	@Override
	public List<PersonalMsgVO> getMsgsByMemedno(Integer memedno) {
		List<PersonalMsgVO> list = new ArrayList<PersonalMsgVO>();
		PersonalMsgVO personalMsgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Get_Msgs_ByMemno_STMT);
			pstmt.setInt(1, memedno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMsgno(rs.getInt("msgno"));
				personalMsgVO.setMemno(rs.getInt("memno"));
				personalMsgVO.setMemedno(rs.getInt("memedno"));
				personalMsgVO.setMsg(rs.getString("msg"));
				personalMsgVO.setMsgdate(rs.getTimestamp("msgdate"));
				list.add(personalMsgVO); // store the row in the vector

			} // Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<PersonalMsgVO> getMemednos() {

		List<PersonalMsgVO> list = new ArrayList<PersonalMsgVO>();
		PersonalMsgVO personalMsgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMNOS_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				personalMsgVO = new PersonalMsgVO();

				personalMsgVO.setMemedno(rs.getInt("memedno"));

				list.add(personalMsgVO); // Store the row in the list
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

	@Override
	public void insertByMsg(PersonalMsgVO personalMsgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT_NEW);

			pstmt.setInt(1, personalMsgVO.getMemno());
			pstmt.setInt(2, personalMsgVO.getMemedno());
			pstmt.setString(3, personalMsgVO.getMsg());
			pstmt.setTimestamp(4, personalMsgVO.getMsgdate());

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
}
