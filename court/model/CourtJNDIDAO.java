package com.court.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.game.model.GameVO;
import com.team.model.TeamVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Court;



public class CourtJNDIDAO implements CourtDAO_interface  {
	
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
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
				"INSERT INTO Court (courtno, courtlat, courtlng, courtloc, courtname, courtnum, basketnum, courttype, opentime, courtlight, courthost, courtphone, courtdesc, courtmoney, courtimg, whoname) VALUES (Court_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
				"SELECT courtno, courtlat, courtlng, courtloc, courtname, courtnum, basketnum, courttype, opentime, courtlight, courthost, courtphone, courtdesc, courtmoney, courtimg, whoname FROM Court order by courtno";
		private static final String GET_ONE_STMT = 
				"SELECT courtno, courtlat, courtlng, courtloc, courtname, courtnum, basketnum, courttype, opentime, courtlight, courthost, courtphone, courtdesc, courtmoney, courtimg, whoname FROM Court where courtno = ?";
		private static final String GET_Games_ByCourtno_STMT = 
				"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where courtno = ? order by gameno";
		private static final String GET_Teams_ByCourtno_STMT = 
				"SELECT teamno, teamname, courtno, teamlogo, gender, teamlevel, gametype, wins, lose FROM Team where courtno=? order by wins desc";
		private static final String GET_ONE_IMG = 
				"SELECT courtimg FROM Court where courtno = ?";
		private static final String DELETE = 
				"DELETE FROM Court where courtno = ?";
		private static final String UPDATE = 
				"UPDATE Court set courtname= ?, whoname=? where courtno = ?";

		@Override
		public void insert(CourtVO courtVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				
				pstmt.setDouble(1, courtVO.getCourtlat());
				pstmt.setDouble(2, courtVO.getCourtlng());
				pstmt.setString(3, courtVO.getCourtloc());
				pstmt.setString(4, courtVO.getCourtname());
				pstmt.setInt(5, courtVO.getCourtnum());
				pstmt.setInt(6, courtVO.getBasketnum());
				pstmt.setString(7, courtVO.getCourttype());
				pstmt.setString(8, courtVO.getOpentime());
				pstmt.setString(9, courtVO.getCourtlight());
				pstmt.setString(10, courtVO.getCourthost());
				pstmt.setString(11, courtVO.getCourtphone());
				pstmt.setString(12, courtVO.getCourtdesc());
				pstmt.setInt(13, courtVO.getCourtmoney());
				pstmt.setBytes(14, courtVO.getCourtimg());
				pstmt.setInt(15, courtVO.getWhoname());
				pstmt.executeUpdate();

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
		public void update(CourtVO courtVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

//				pstmt.setDouble(1, courtVO.getCourtlat());
//				pstmt.setDouble(2, courtVO.getCourtlng());
//				pstmt.setString(3, courtVO.getCourtloc());
				pstmt.setString(1, courtVO.getCourtname());
//				pstmt.setInt(5, courtVO.getCourtnum());
//				pstmt.setInt(6, courtVO.getBasketnum());
//				pstmt.setString(7, courtVO.getCourttype());
//				pstmt.setString(8, courtVO.getOpentime());
//				pstmt.setString(9, courtVO.getCourtlight());
//				pstmt.setString(10, courtVO.getCourthost());
//				pstmt.setString(11, courtVO.getCourtphone());
//				pstmt.setString(12, courtVO.getCourtdesc());
//				pstmt.setInt(13, courtVO.getCourtmoney());
//				pstmt.setBytes(14, courtVO.getCourtimg());
				pstmt.setInt(2, courtVO.getWhoname());
				pstmt.setInt(3, courtVO.getCourtno());
				
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
		public void delete(Integer courtno) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, courtno);

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
		public CourtVO findByPrimaryKey(Integer courtno) {

			CourtVO courtVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, courtno);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// courtVO �]�٬� Domain objects
					courtVO = new CourtVO();
					
					courtVO.setCourtno(rs.getInt("courtno"));
					courtVO.setCourtlat(rs.getDouble("courtlat"));
					courtVO.setCourtlng(rs.getDouble("courtlng"));
					courtVO.setCourtloc(rs.getString("courtloc"));
					courtVO.setCourtname(rs.getString("courtname"));
					courtVO.setCourtnum(rs.getInt("courtnum"));
					courtVO.setBasketnum(rs.getInt("basketnum"));
					courtVO.setCourttype(rs.getString("courttype"));
					courtVO.setOpentime(rs.getString("opentime"));
					courtVO.setCourtlight(rs.getString("courtlight"));
					courtVO.setCourthost(rs.getString("courthost"));
					courtVO.setCourtphone(rs.getString("courtphone"));
					courtVO.setCourtdesc(rs.getString("courtdesc"));
					courtVO.setCourtmoney(rs.getInt("courtmoney"));
					courtVO.setCourtimg(rs.getBytes("courtimg"));
					courtVO.setWhoname(rs.getInt("whoname"));
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
			return courtVO;
		}

		@Override
		public List<CourtVO> getAll() {
			List<CourtVO> list = new ArrayList<CourtVO>();
			CourtVO courtVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// courtVO �]�٬� Domain objects
					
					courtVO = new CourtVO();
					courtVO.setCourtno(rs.getInt("courtno"));
					courtVO.setCourtlat(rs.getDouble("courtlat"));
					courtVO.setCourtlng(rs.getDouble("courtlng"));
					courtVO.setCourtloc(rs.getString("courtloc"));
					courtVO.setCourtname(rs.getString("courtname"));
					courtVO.setCourtnum(rs.getInt("courtnum"));
					courtVO.setBasketnum(rs.getInt("basketnum"));
					courtVO.setCourttype(rs.getString("courttype"));
					courtVO.setOpentime(rs.getString("opentime"));
					courtVO.setCourtlight(rs.getString("courtlight"));
					courtVO.setCourthost(rs.getString("courthost"));
					courtVO.setCourtphone(rs.getString("courtphone"));
					courtVO.setCourtdesc(rs.getString("courtdesc"));
					courtVO.setCourtmoney(rs.getInt("courtmoney"));
					courtVO.setCourtimg(rs.getBytes("courtimg"));
					courtVO.setWhoname(rs.getInt("whoname"));
					list.add(courtVO); // Store the row in the list
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
		public List<CourtVO> getAll(Map<String, String[]> map) {
			List<CourtVO> list = new ArrayList<CourtVO>();
			CourtVO courtVO = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			try {
				
				con = ds.getConnection();
				String finalSQL = "select * from court "
			          + jdbcUtil_CompositeQuery_Court.get_WhereCondition(map)
			          + "order by courtno";
				pstmt = con.prepareStatement(finalSQL);
				System.out.println("finalSQL(by DAO) = "+finalSQL);
				rs = pstmt.executeQuery();
		
				while (rs.next()) {
					courtVO = new CourtVO();
					courtVO.setCourtno(rs.getInt("courtno"));
					courtVO.setCourtlat(rs.getDouble("courtlat"));
					courtVO.setCourtlng(rs.getDouble("courtlng"));
					courtVO.setCourtloc(rs.getString("courtloc"));
					courtVO.setCourtname(rs.getString("courtname"));
					courtVO.setCourtnum(rs.getInt("courtnum"));
					courtVO.setBasketnum(rs.getInt("basketnum"));
					courtVO.setCourttype(rs.getString("courttype"));
					courtVO.setOpentime(rs.getString("opentime"));
					courtVO.setCourtlight(rs.getString("courtlight"));
					courtVO.setCourthost(rs.getString("courthost"));
					courtVO.setCourtphone(rs.getString("courtphone"));
					courtVO.setCourtdesc(rs.getString("courtdesc"));
					courtVO.setCourtmoney(rs.getInt("courtmoney"));
					courtVO.setCourtimg(rs.getBytes("courtimg"));
					courtVO.setWhoname(rs.getInt("whoname"));
					list.add(courtVO); // Store the row in the list
				}
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public Set<GameVO> getGamesByCourtno(Integer courtno) {
			Set<GameVO> set = new LinkedHashSet<GameVO>();
			GameVO gameVO = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			try {
		
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_Games_ByCourtno_STMT);
				pstmt.setInt(1, courtno);
				rs = pstmt.executeQuery();
		
				while (rs.next()) {
					gameVO = new GameVO();
					gameVO.setGameno(rs.getInt("gameno"));
					gameVO.setGamedate(rs.getDate("gamedate"));
					gameVO.setMemno(rs.getInt("memno"));
					gameVO.setTeamno(rs.getInt("teamno"));
					gameVO.setTeamno2(rs.getInt("teamno2"));
					gameVO.setCourtno(rs.getInt("courtno"));
					gameVO.setGametype(rs.getInt("gametype"));
					gameVO.setGameresult(rs.getString("gameresult"));
					set.add(gameVO); // Store the row in the vector
				}
		
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
			return set;
		}
		
		@Override
		public Set<TeamVO> getTeamsByCourtno(Integer courtno) {
			Set<TeamVO> set = new LinkedHashSet<TeamVO>();
			TeamVO teamVO = null;
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			try {
		
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_Teams_ByCourtno_STMT);
				pstmt.setInt(1, courtno);
				rs = pstmt.executeQuery();
		
				while (rs.next()) {
					teamVO = new TeamVO();
					teamVO.setTeamno(rs.getInt("teamno"));
					teamVO.setTeamname(rs.getString("teamname"));
					teamVO.setCourtno(rs.getInt("courtno"));
					teamVO.setTeamlogo(rs.getBytes("teamlogo"));
					teamVO.setGender(rs.getString("gender"));
					teamVO.setTeamlevel(rs.getString("teamlevel"));
					teamVO.setGametype(rs.getInt("gametype"));
					teamVO.setWins(rs.getInt("wins"));
					teamVO.setLose(rs.getInt("lose"));
					set.add(teamVO); // Store the row in the vector
				}
		
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
			return set;
		}
		
		public byte[] GetImg(Integer courtno) {

			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			byte[] img=null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_IMG);
				pstmt.setInt(1, courtno);
	            rs = pstmt.executeQuery();
	            
				while (rs.next()) {
					
					img=rs.getBytes("courtimg");
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
				return img;	
		}
}
