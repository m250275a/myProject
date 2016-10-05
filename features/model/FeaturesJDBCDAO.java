//package com.features.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.features.model.FeaturesJDBCDAO;
//import com.features.model.FeaturesVO;
//import com.features.model.FeaturesVO;
//
//public class FeaturesJDBCDAO implements FeaturesDAO_interface{
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	String userid = "aa103";
//	String passwd = "zzzz1111";
//private static final String INSERT_STMT = "INSERT INTO Features  VALUES (Features_SEQ.NEXTVAL,?)";
//	
//	//刪除單個
//	private static final String DELETE = "DELETE FROM Features where feano =?";
//	private static final String GET_ONE_STMT = "SELECT * from features where feano=?";
//	// 查詢全部
//	private static final String GET_ALL_STMT ="select * from features order by feano";
//	@Override
//	public int insert(String feapower) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//			pstmt.setString(1,feapower);
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
//	@Override
//	public int delete(Integer feano) {
//		int updateCount = 0;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(DELETE);
//			pstmt.setInt(1,feano);
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
//	public List<FeaturesVO> getAll() {
//		List<FeaturesVO> list = new ArrayList<FeaturesVO>();
//		FeaturesVO featuresVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// memberVO 也稱為 Domain objects
//				featuresVO = new FeaturesVO();
//				featuresVO.setFeano(rs.getInt("feano"));
//				featuresVO.setFeapower(rs.getString("feapower"));
//							list.add(featuresVO);
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
//	public FeaturesVO  getOne(Integer feano) {
////		FeaturesVO featuresVO=null;
////		featuresVO=new FeaturesVO();
//		FeaturesVO featuresVO=new FeaturesVO();
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		ResultSet rs = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//			pstmt.setInt(1,feano);
//			rs = pstmt.executeQuery();
//			while(rs.next()){
//				
//				featuresVO.setFeano(rs.getInt("feano"));
//			featuresVO.setFeapower(rs.getString("feapower"));
//			}
//			
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
//		return featuresVO;
//	
//	}
//	public static void main(String[] args) {
//		FeaturesJDBCDAO dao = new FeaturesJDBCDAO();
////	//	 新增
////			//////////////////////////////////////////////////////////////
//
////			int updateCount_insert = dao.insert("中樂透");
////			System.out.println(updateCount_insert+"個new ok");
//			/////////////////////////////////////////////////////////////
//			
////			 刪除
//////			/////////////////////////////////////////////////////////////
////			 int updateCount_delete = dao.delete(5);
////			 System.out.println(updateCount_delete+"個del OK");
//			////////////////////////////////////////////////////////////////////
////			//查一個
//////			/////////////////////////////////////////////////////////////
//			
////				FeaturesVO featuresVO = dao.getOne(2);
////				   System.out.print(featuresVO.getFeano() + ",");
////				   System.out.print(featuresVO.getFeapower() + ",");
////				  
////				
////			/////////
//		////////////////////////////////////////////////////////////////////
//			
//			//查全部
//			///////////////////////////////////////////////////////
//			List<FeaturesVO> list = dao.getAll();
////			for (FeaturesVO aFeatures : list) 
////			{
//		
//			   System.out.print(list.get(0) + ",");
////			   System.out.print(aFeatures.getFeapower() + ",");
//			  
////			   System.out.println();
////			}
//		////////////////////////////////////////////////////////////////
//					
//	}//main
//}//class