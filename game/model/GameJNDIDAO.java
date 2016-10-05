package com.game.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GameJNDIDAO implements GameDAO_interface {
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
				"INSERT INTO Game (gameno, gamedate, memno, teamno, courtno, gametype) VALUES (Game_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
			private static final String GET_ALL_STMT = 
				"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where gameresult is null order by gameno desc";
			//還沒有對手的比賽
			private static final String GET_GameLIST = "select gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where teamno2 is null";
			
			private static final String GET_ONE_STMT = 
				"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where gameno = ?";
			private static final String GET_Games_ByCourtno_forchange= 
					"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where courtno = ? order by gameno desc";
			
			private static final String GET_Games_ByCourtno_STMT = 
					"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where courtno = ? and gameresult is null order by gameno desc";
			private static final String GET_Games_ByGametype_STMT = 
					"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where gametype = ? and teamno2 is null order by gameno desc";
			private static final String GET_Games_ByTeamno_STMT = 
					"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where teamno = ? and teamno2 is null order by gameno desc";
			private static final String GET_ONEBYMEMNO_STMT = 
					"SELECT gameno, gamedate, memno, teamno, teamno2, courtno, gametype, gameresult FROM Game where memno = ?";
			private static final String DELETE = 
				"DELETE FROM Game where gameno = ?";
			private static final String UPDATE = 
				"UPDATE Game set teamno2=? where gameno = ?";
			// 修改比賽結果
			public static final String updateGameresult = "UPDATE Game set  gameresult=? where gameno=?";

			@Override
			public void insert(GameVO gameVO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(INSERT_STMT);

					pstmt.setDate(1, gameVO.getGamedate());
					pstmt.setInt(2, gameVO.getMemno());
					pstmt.setInt(3, gameVO.getTeamno());
					pstmt.setInt(4, gameVO.getCourtno());
					pstmt.setInt(5, gameVO.getGametype());
					
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
			public void update(GameVO gameVO) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE);

					//pstmt.setDate(1, gameVO.getGamedate());
					//pstmt.setInt(2, gameVO.getMemno());
					//pstmt.setInt(3, gameVO.getTeamno());
					pstmt.setInt(1, gameVO.getTeamno2());
//					pstmt.setInt(2, gameVO.getCourtno());
					//pstmt.setInt(6, gameVO.getGametype());
//					pstmt.setString(2, gameVO.getGameresult());
					pstmt.setInt(2, gameVO.getGameno());

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
			public void delete(Integer gameno) {

				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(DELETE);

					pstmt.setInt(1, gameno);

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
			public Set<GameVO> getGamesByMemno(Integer memno) {
				Set<GameVO> set = new LinkedHashSet<GameVO>();
				GameVO gameVO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONEBYMEMNO_STMT);

					pstmt.setInt(1, memno);

					rs = pstmt.executeQuery();

					while (rs.next()) {
						// gameVO 也稱為 Domain objects
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
				return set;
			}
			
			@Override
			public GameVO findByPrimaryKey(Integer gameno) {

				GameVO gameVO = null;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONE_STMT);

					pstmt.setInt(1, gameno);

					rs = pstmt.executeQuery();

					while (rs.next()) {
						// gameVO 也稱為 Domain objects
						gameVO = new GameVO();
						gameVO.setGameno(rs.getInt("gameno"));
						gameVO.setGamedate(rs.getDate("gamedate"));
						gameVO.setMemno(rs.getInt("memno"));
						gameVO.setTeamno(rs.getInt("teamno"));
						gameVO.setTeamno2(rs.getInt("teamno2"));
						gameVO.setCourtno(rs.getInt("courtno"));
						gameVO.setGametype(rs.getInt("gametype"));
						gameVO.setGameresult(rs.getString("gameresult"));
						
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
				return gameVO;
			}
			
			@Override
			public List<GameVO> getGamesByCourtno(Integer courtno) {
				List<GameVO> list = new ArrayList<GameVO>();
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
						list.add(gameVO); // Store the row in the vector
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
			public List<GameVO> getGamesByCourtnoToChange(Integer courtno) {
				List<GameVO> list = new ArrayList<GameVO>();
				GameVO gameVO = null;
			
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
			
				try {
			
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_Games_ByCourtno_forchange);
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
						list.add(gameVO); // Store the row in the vector
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
			public List<GameVO> getGamesByGametype(Integer gametype) {
				List<GameVO> list = new ArrayList<GameVO>();
				GameVO gameVO = null;
			
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
			
				try {
			
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_Games_ByGametype_STMT);
					pstmt.setInt(1, gametype);
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
						list.add(gameVO); // Store the row in the vector
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
			public List<GameVO> getGamesByTeam(Integer teamno) {
				List<GameVO> list = new ArrayList<GameVO>();
				GameVO gameVO = null;
			
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
			
				try {
			
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_Games_ByTeamno_STMT);
					pstmt.setInt(1, teamno);
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
						list.add(gameVO); // Store the row in the vector
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
			public List<GameVO> getAllGameList() {
				List<GameVO> list = new ArrayList<GameVO>();
				GameVO gameVO = null;

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_GameLIST);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						// gameVO 也稱為 Domain objects
						gameVO = new GameVO();
						gameVO.setGameno(rs.getInt("gameno"));
						gameVO.setGamedate(rs.getDate("gamedate"));
						gameVO.setMemno(rs.getInt("memno"));
						gameVO.setTeamno(rs.getInt("teamno"));
						gameVO.setTeamno2(rs.getInt("teamno2"));
						gameVO.setCourtno(rs.getInt("courtno"));
						gameVO.setGametype(rs.getInt("gametype"));
						gameVO.setGameresult(rs.getString("gameresult"));
						list.add(gameVO); // Store the row in the list
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
			public List<GameVO> getAll() {
				List<GameVO> list = new ArrayList<GameVO>();
				GameVO gameVO = null;

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ALL_STMT);
					rs = pstmt.executeQuery();

					while (rs.next()) {
						// gameVO 也稱為 Domain objects
						gameVO = new GameVO();
						gameVO.setGameno(rs.getInt("gameno"));
						gameVO.setGamedate(rs.getDate("gamedate"));
						gameVO.setMemno(rs.getInt("memno"));
						gameVO.setTeamno(rs.getInt("teamno"));
						gameVO.setTeamno2(rs.getInt("teamno2"));
						gameVO.setCourtno(rs.getInt("courtno"));
						gameVO.setGametype(rs.getInt("gametype"));
						gameVO.setGameresult(rs.getString("gameresult"));
						list.add(gameVO); // Store the row in the list
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
			
			// 修改比賽結果
			@Override
			public void updateGameresult(GameVO gameVO) {
				Connection con = null;
				PreparedStatement pstmt = null;

				try {

					con = ds.getConnection();
					pstmt = con.prepareStatement(updateGameresult);

					pstmt.setString(1, gameVO.getGameresult());
					pstmt.setInt(2, gameVO.getGameno());
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
}
