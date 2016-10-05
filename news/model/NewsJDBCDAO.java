package com.news.model;

import java.util.*;
import java.io.*;
import java.sql.*;

public class NewsJDBCDAO implements NewsDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "AA103g05";
	String passwd = "1234";

	private static final String INSERT_STMT = 
			"INSERT INTO News (newsno, newsdate, newscon, newsfullcon, newsimg) VALUES (News_SEQ.NEXTVAL, default, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT newsno, newsdate, newscon, newsfullcon, newsimg FROM News order by newsno";
		private static final String GET_ONE_STMT = 
			"SELECT newsno, newsdate, newscon, newsfullcon, newsimg FROM News where newsno = ?";
		private static final String DELETE = 
			"DELETE FROM News where news_no = ?";
		private static final String UPDATE = 
			"UPDATE News set newsdate=?, newscon=?, newsfullcon=?, newsimg=? where newsno = ?";

	@Override
	public void insert(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			//pstmt.setDate(1, newsVO.getNewsdate());
			pstmt.setString(1, newsVO.getNewscon());
			pstmt.setString(2, newsVO.getNewsfullcon());
			pstmt.setBytes(3, newsVO.getNewsimg());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void update(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setDate(1, newsVO.getNewsdate());
			pstmt.setString(2, newsVO.getNewscon());
			pstmt.setString(3, newsVO.getNewsfullcon());
			pstmt.setBytes(4, newsVO.getNewsimg());
			pstmt.setInt(5, newsVO.getNewsno());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void delete(Integer newsno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, newsno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public NewsVO findByPrimaryKey(Integer newsno) {

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("newsno"));
				newsVO.setNewsdate(rs.getDate("newsdate"));
				newsVO.setNewscon(rs.getString("newscon"));
				newsVO.setNewsfullcon(rs.getString("newsfullcon"));
				newsVO.setNewsimg(rs.getBytes("newsimg"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("newsno"));
				newsVO.setNewsdate(rs.getDate("newsdate"));
				newsVO.setNewscon(rs.getString("newscon"));
				newsVO.setNewsfullcon(rs.getString("newsfullcon"));
				newsVO.setNewsimg(rs.getBytes("newsimg"));
				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {

		NewsJDBCDAO dao = new NewsJDBCDAO();

		try {
			
			String filePath = "WebContent/img/456.png";
			File pic = new File(filePath);
			byte[] bFile = new byte[(int) pic.length()];
			FileInputStream fin = new FileInputStream(pic);

			fin.read(bFile);
			fin.close();

			// 穝糤
//			NewsVO newsVO1 = new NewsVO();			
//			
//			//newsVO1.setNewsdate("儡"));
//			newsVO1.setNewscon("儡");
//			newsVO1.setNewsfullcon("儡猭畍次次!");
//			newsVO1.setNewsimg(bFile);
//			dao.insert(newsVO1);

			 // э
			 NewsVO newsVO2 = new NewsVO();
			 newsVO2.setNewsno(6);
			//newsVO2.setNewsdate();
				newsVO2.setNewscon("儡");
				newsVO2.setNewsfullcon("儡猭畍次次456!");
				newsVO2.setNewsimg(bFile);
			 dao.update(newsVO2);
			
			// //埃
			// dao.delete(13);
			//
			 //琩高
//			 NewsVO newsVO3 = dao.findByPrimaryKey(6);
//			 System.out.print(newsVO3.getNews_no() + ",");
//			 System.out.print(newsVO3.getNews_date() + ",");
//			 System.out.print(newsVO3.getNews_content() + ",");
//			 System.out.print(newsVO3.getNews_fullcontent() + ",");
//			 System.out.println((newsVO3.getNews_img() + ","));
//			 System.out.println("---------------------");
			
//			 // 琩高
//			 List<NewsVO> list = dao.getAll();
//			 for (NewsVO anewsVO : list) {
//			 System.out.print(anewsVO.getNews_no() + ",");
//			 System.out.print(anewsVO.getNews_date() + ",");
//			 System.out.print(anewsVO.getNews_content() + ",");
//			 System.out.print(anewsVO.getNews_fullcontent() + ",");
//			 System.out.println(anewsVO.getNews_img() + ",");
//			 System.out.println();
//			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
