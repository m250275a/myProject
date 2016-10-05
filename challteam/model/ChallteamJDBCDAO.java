//package com.challteam.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import com.challteam.model.ChallteamVO;
//
//public class ChallteamJDBCDAO implements ChallteamDAO_interface{
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	String userid = "aa103";
//	String passwd = "zzzz1111";
//	private static final String INSERT_STMT = "INSERT INTO challteam  VALUES (?,?)";
//	
//	//刪除單個好友
//	private static final String DELETE = "DELETE FROM challteam where teamno =? and challmode=? ";
//
//	// 查詢全部好友
//	private static final String GET_ALL_STMT ="select * from challteam where teamno=?";
//	@Override
//	public int insert(ChallteamVO challteamVO) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//			
//			pstmt.setInt(1, challteamVO.getTeamno());
//			pstmt.setInt(2, challteamVO.getChallmode());
//			
//
//			updateCount = pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//		return updateCount;
//	}
//
//	@Override
//	   public int delete(Integer teamno,Integer challmode) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(DELETE);
//			
//			pstmt.setInt(1,teamno);
//			pstmt.setInt(2,challmode);
//			
//
//			updateCount = pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//		return updateCount;
//	}
//
//	
//
//	
//	
//	@Override
//	public List<ChallteamVO> getAll(Integer memno) {
//		List<ChallteamVO> list = new ArrayList<ChallteamVO>();
//		ChallteamVO challteamVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			pstmt.setInt(1,memno);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				
//				// memberVO 也稱為 Domain objects
//				challteamVO = new ChallteamVO();
//				challteamVO.setTeamno(rs.getInt("teamno"));
//				challteamVO.setChallmode(rs.getInt("challmode"));
//				list.add(challteamVO);
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}	
//	
///////////////////////////////////////////////////////////////////////////////	
//	public static void main(String[] args) {
//		ChallteamJDBCDAO dao = new ChallteamJDBCDAO();
//		
////		 新增
////		//////////////////////////////////////////////////////////////
////		ChallteamVO challteamVO1 = new ChallteamVO();
////		challteamVO1.setTeamno(7003);
////		challteamVO1.setChallmode(7004);
////		int updateCount_insert=dao.insert(challteamVO1);
////		System.out.println(updateCount_insert+"個new ok");
//		/////////////////////////////////////////////////////////////
//		
////		 刪除
//////		/////////////////////////////////////////////////////////////
////		int updateCount_delete = dao.delete(7003,7004);
////		 System.out.println(updateCount_delete+"個del OK");
//		////////////////////////////////////////////////////////////////////
//		
//		//查全部7001好友
//		///////////////////////////////////////////////////////
//		List<ChallteamVO> list = dao.getAll(7001);
//		for (ChallteamVO aChallmode : list) 
//		{
//		   System.out.print(aChallmode.getChallmode() + ",");
//		  
//		   System.out.println();
//		}
//	////////////////////////////////////////////////////////////////
//		
//		
//	}//main
//}//class