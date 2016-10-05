//package com.friend.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.member.model.MemberDAO_interface;
//
//import com.member.model.MemberVO;
//
//public class FriendJDBCDAO implements FriendDAO_interface {
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	String userid = "aa103";
//	String passwd = "zzzz1111";
//	private static final String INSERT_STMT = "INSERT INTO Friend  VALUES (?,?)";
//	
//	//刪除單個好友
//	private static final String DELETE = "DELETE FROM Friend where memno =? and frino=? ";
//
//	// 查詢全部好友
//	private static final String GET_ALL_STMT ="select * from friend where memno=?";
//	@Override
//	public FriendVO insert(FriendVO friendVO) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//			
//			pstmt.setInt(1, friendVO.getMemno());
//			pstmt.setInt(2, friendVO.getFrino());
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
//		return friendVO;
//	}
//
//	@Override
//	   public void delete(Integer memno,Integer frino) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(DELETE);
//			
//			pstmt.setInt(1,memno);
//			pstmt.setInt(2,frino);
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
//	public List<FriendVO> getAll(Integer memno) {
//		List<FriendVO> list = new ArrayList<FriendVO>();
//		FriendVO friendVO = null;
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
//				friendVO = new FriendVO();
//				friendVO.setMemno(rs.getInt("memno"));
//				friendVO.setFrino(rs.getInt("frino"));
//				list.add(friendVO);
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
//		FriendJDBCDAO dao = new FriendJDBCDAO();
//		
////		 新增
////		//////////////////////////////////////////////////////////////
////		FriendVO friendVO1 = new FriendVO();
////		friendVO1.setFrino(7003);
////		friendVO1.setFriedno(7004);
////		int updateCount_insert=dao.insert(friendVO1);
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
//		List<FriendVO> list = dao.getAll(7001);
//		for (FriendVO aFriend : list) 
//		{
//		   System.out.print(aFriend.getMemno() + ",");
//		   System.out.print(aFriend.getFrino() + ",");
//		  
//		   System.out.println();
//		}
//	////////////////////////////////////////////////////////////////
//		
//		
//	}//main
//}//class
