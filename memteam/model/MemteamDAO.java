package com.memteam.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.game.model.GameVO;
import com.team.model.TeamVO;

public class MemteamDAO implements MemteamDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String INSERT_STMT = "INSERT INTO memteam(memno,teamno)  VALUES (?,?)";

	// �R����Ӧn��(�ڵ��J���ӽ�)
	private static final String DELETE = "DELETE FROM memteam where memno =? and teamno=? ";

	// �d�ߥ����n��
	private static final String GET_ALL_STMT = "select * from memteam ";

	// �η|����y��
	private static final String GET_TEAM_BY_MEM = "SELECT teamno FROM Memteam WHERE memno=?";
	//�ư��y������
	private static final String GET_ALL_TEAMNAME="SELECT DISTINCT teamno FROM Memteam ORDER BY teamno";
	//�ư��ӷ|���w�[�J���y��
	private static final String GET_TEAM_BESIDE_MEM =
		"SELECT DISTINCT teamno FROM Memteam WHERE teamno not in (SELECT teamno From memteam WHERE memno=?)";
	//�|���ӽХ[�J�y��
	private static final String GET_JOIN_TEAM="INSERT INTO Memteam(memno,teamno) VALUES(?,?)";
 


	@Override
	public int insert(MemteamVO memteamVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, memteamVO.getMemno());
			pstmt.setInt(2, memteamVO.getTeamno());
			

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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

		return updateCount;
	}

	@Override
	public int delete(Integer memno, Integer teamno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memno);
			pstmt.setInt(2, teamno);

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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

		return updateCount;
	}

	@Override
	public List<MemteamVO> getAll() {
		List<MemteamVO> list = new ArrayList<MemteamVO>();
		MemteamVO memteamVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				// memberVO �]�٬� Domain objects
				memteamVO = new MemteamVO();
				memteamVO.setMemno(rs.getInt("memno"));
				memteamVO.setTeamno(rs.getInt("teamno"));
				list.add(memteamVO);
			}
			// Handle any driver errors
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

	// �η|����y��
	@Override
	public List<MemteamVO> getTeamBYMemno(Integer memno) {
		
		List<MemteamVO> list = new ArrayList<MemteamVO>();
		MemteamVO memteamVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TEAM_BY_MEM);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				memteamVO = new MemteamVO();
				memteamVO.setTeamno(rs.getInt("teamno"));
				list.add(memteamVO);
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

	//�ư��y������
	@Override
	public List<MemteamVO> getAllTeamName() {
		List<MemteamVO> list = new ArrayList<MemteamVO>();
		MemteamVO memteamVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_TEAMNAME);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				// memberVO �]�٬� Domain objects
				memteamVO = new MemteamVO();
				memteamVO.setTeamno(rs.getInt("teamno"));
				list.add(memteamVO);
			}
			// Handle any driver errors
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

	//�ư��ӷ|���w�[�J���y��
	@Override
	public List<MemteamVO> getTeamBesideMem(Integer memno) {
		
		List<MemteamVO> list = new ArrayList<MemteamVO>();
		MemteamVO memteamVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TEAM_BESIDE_MEM);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				memteamVO = new MemteamVO();
				memteamVO.setTeamno(rs.getInt("teamno"));
				list.add(memteamVO);
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
    //�ӽФJ��
	@Override
	public void joinTeam(MemteamVO memteamVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_JOIN_TEAM);

			pstmt.setInt(1, memteamVO.getMemno());
			pstmt.setInt(2, memteamVO.getTeamno());
    		pstmt.executeUpdate();

			// Handle any driver errors
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

	
}// class
