package com.member.model;




import javax.naming.NamingException;
import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.MemberVO;
import com.ordered.model.*;
import com.videopicture.model.VideoPictureVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Member;






public class MemberDAO implements MemberDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Member (memno, memname, memadd, memage, mempassword, "
			+ "memvarname, memphone,mememail, memsex, memcheck, memshit, "
			+ "memwow, memballage, memreb,memscore,memblock, memast,memsteal,memimg)"
			+ "VALUES (Member_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	// "INSERT INTO emp2 (empno,ename,job,hiredate,sal,comm,deptno) VALUES
	// (emp2_seq.NEXTVAL, ?, ?, to_date(?,'yyyy-mm-dd'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM Member order by Memno";

	// "SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd')
	// hiredate,sal,comm,deptno FROM emp2 order by empno";
	private static final String GET_ONE_STMT = "SELECT * FROM Member where Memno=?";

	// "SELECT empno,ename,job,to_char(hiredate,'yyyy-mm-dd')
	// hiredate,sal,comm,deptno FROM emp2 where empno = ?";
	private static final String DELETE = "DELETE FROM Member where Memno = ?";

	// "DELETE FROM emp2 where empno = ?";
	private static final String UPDATE = "UPDATE Member set memname=?, memadd=?, memage=?,"
			+ " mempassword=?, memvarname=?, memphone=?,mememail=?, memsex=?,"
			+ " memcheck=?, memshit=?, memwow=?, memballage=?, memreb=?,"
			+ "memscore=?,memblock=?, memast=?,memsteal=?,memimg=?  where memno=?";
	private static final String GET_Ordereds_ByMemno_STMT = 
			"SELECT ordedno,memno,orddate,deliverydate,sums FROM ordered where memno = ? order by ordedno";
	
	//用EMAIL查詢會員資料
		private static final String GET_MAIL_STMT =
				"SELECT * from Member where mememail = ?";
		private static final String GET_ALL_ByName =
			"SELECT * from Member where memname like ? or memvarname like ?";
		
		//修改會員申請球隊狀態
		private static final String UPDATE_JOIN_CHECK="UPDATE Member set memcheck=? WHERE memno=?";
		//找申請隊員
		private static final String GET_JOIN_MEM="SELECT memno,memname,memcheck FROM Member WHERE  memcheck=?";
		 //找球隊隊員的個人影音照片
		private static final String GET_TEAMWINER_PIC=
				"SELECT vpno,memno,vpmsg,vpfile FROM VideoPicture WHERE NOT(vptype ='mp3' OR vptype='mp4' OR vptype ='avi')"
				+ "and memno=?";
		//更新球技	
		private static final String UPDATESKILL = "UPDATE Member set memshit=?,memreb=?,memscore=?,memblock=?, memast=?,memsteal=? where memno=?";
		private static final String UPDATESKILL2 = "UPDATE Member set memreb=?,memscore=?,memblock=?, memast=?,memsteal=? where memno=?";
		//更新會員資料	
		private static final String UPDATEINFO = "UPDATE Member set memname=?, memadd=?, memage=?, "
				+ "mempassword=?, memvarname=?, memphone=?,mememail=?, memsex=?,"
				+" memcheck=?,  memballage=?,"
				+"memimg=?  where memno=?";
		
	@Override
	public int insert(MemberVO memberVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, memberVO.getMemname());
			pstmt.setString(2, memberVO.getMemadd());
			pstmt.setDate(3, memberVO.getMemage());
			pstmt.setString(4, memberVO.getMempassword());
			pstmt.setString(5, memberVO.getMemvarname());
			pstmt.setString(6, memberVO.getMemphone());
			pstmt.setString(7, memberVO.getMememail());
			pstmt.setString(8, memberVO.getMemsex());
			pstmt.setString(9, memberVO.getMemcheck());
			pstmt.setInt(10, memberVO.getMemshit());
			pstmt.setInt(11, memberVO.getMemwow());
			pstmt.setInt(12, memberVO.getMemballage());
			pstmt.setInt(13, memberVO.getMemreb());
			pstmt.setInt(14, memberVO.getMemscore());
			pstmt.setInt(15, memberVO.getMemblock());
			pstmt.setInt(16, memberVO.getMemast());
			pstmt.setInt(17, memberVO.getMemsteal());
			pstmt.setBytes(18, memberVO.getMemimg());
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
		return 0;
		
	}

//	@Override
	public int update(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, memberVO.getMemname());
			pstmt.setString(2, memberVO.getMemadd());
			pstmt.setDate(3, memberVO.getMemage());
			pstmt.setString(4, memberVO.getMempassword());
			pstmt.setString(5, memberVO.getMemvarname());
			pstmt.setString(6, memberVO.getMemphone());
			pstmt.setString(7, memberVO.getMememail());
			pstmt.setString(8, memberVO.getMemsex());
			pstmt.setString(9, memberVO.getMemcheck());
			pstmt.setInt(10, memberVO.getMemshit());
			pstmt.setInt(11, memberVO.getMemwow());
			pstmt.setInt(12, memberVO.getMemballage());
			pstmt.setInt(13, memberVO.getMemreb());
			pstmt.setInt(14, memberVO.getMemscore());
			pstmt.setInt(15, memberVO.getMemblock());
			pstmt.setInt(16, memberVO.getMemast());
			pstmt.setInt(17, memberVO.getMemsteal());
			pstmt.setBytes(18, memberVO.getMemimg());
			
			pstmt.setInt(19, memberVO.getMemno());//
			updateCount = pstmt.executeUpdate();

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
		return updateCount;
//		
	}

	@Override
	public void delete(Integer memno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, memno);
			
			updateCount = pstmt.executeUpdate();

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
	public MemberVO findByPrimaryKey(Integer memno){

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, memno);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setMemadd(rs.getString("memadd"));
				memberVO.setMemage(rs.getDate("memage"));
				memberVO.setMempassword(rs.getString("mempassword"));
				memberVO.setMemvarname(rs.getString("memvarname"));
				memberVO.setMemphone(rs.getString("memphone"));
				memberVO.setMememail(rs.getString("mememail"));
				memberVO.setMemsex(rs.getString("memsex"));
				memberVO.setMemcheck(rs.getString("memcheck"));
				memberVO.setMemshit(rs.getInt("memshit"));
				memberVO.setMemwow(rs.getInt("memwow"));
				memberVO.setMemballage(rs.getInt("memballage"));
				memberVO.setMemreb(rs.getInt("memreb"));
				memberVO.setMemscore(rs.getInt("memscore"));
				memberVO.setMemblock(rs.getInt("memblock"));
				memberVO.setMemast(rs.getInt("memast"));
				memberVO.setMemsteal(rs.getInt("memsteal"));
				memberVO.setMemimg(rs.getBytes("memimg"));
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
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setMemadd(rs.getString("memadd"));
				memberVO.setMemage(rs.getDate("memage"));
				memberVO.setMempassword(rs.getString("mempassword"));
				memberVO.setMemvarname(rs.getString("memvarname"));
				memberVO.setMemphone(rs.getString("memphone"));
				memberVO.setMememail(rs.getString("mememail"));
				memberVO.setMemsex(rs.getString("memsex"));
				memberVO.setMemcheck(rs.getString("memcheck"));
				memberVO.setMemshit(rs.getInt("memshit"));
				memberVO.setMemwow(rs.getInt("memwow"));
				memberVO.setMemballage(rs.getInt("memballage"));
				memberVO.setMemreb(rs.getInt("memreb"));
				memberVO.setMemscore(rs.getInt("memscore"));
				memberVO.setMemblock(rs.getInt("memblock"));
				memberVO.setMemast(rs.getInt("memast"));
				memberVO.setMemsteal(rs.getInt("memsteal"));
				memberVO.setMemimg(rs.getBytes("memimg"));
				list.add(memberVO);
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
	public List<MemberVO> getAll(Map<String, String[]> map) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * from member "
		          + jdbcUtil_CompositeQuery_Member.get_WhereCondition(map)
		          + "order by memno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setMemadd(rs.getString("memadd"));
				memberVO.setMemage(rs.getDate("memage"));
				memberVO.setMempassword(rs.getString("mempassword"));
				memberVO.setMemvarname(rs.getString("memvarname"));
				memberVO.setMemphone(rs.getString("memphone"));
				memberVO.setMememail(rs.getString("mememail"));
				memberVO.setMemsex(rs.getString("memsex"));
				memberVO.setMemcheck(rs.getString("memcheck"));
				memberVO.setMemshit(rs.getInt("memshit"));
				memberVO.setMemwow(rs.getInt("memwow"));
				memberVO.setMemballage(rs.getInt("memballage"));
				memberVO.setMemreb(rs.getInt("memreb"));
				memberVO.setMemscore(rs.getInt("memscore"));
				memberVO.setMemblock(rs.getInt("memblock"));
				memberVO.setMemast(rs.getInt("memast"));
				memberVO.setMemsteal(rs.getInt("memsteal"));
				memberVO.setMemimg(rs.getBytes("memimg"));
				list.add(memberVO); // Store the row in the List
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
	//註冊帳號，判斷信箱是否重複
	

	@Override
		public MemberVO GET_MAIL_STMT(String mememail) {
		
			MemberVO memberVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MAIL_STMT);
				pstmt.setString(1, mememail);
				rs = pstmt.executeQuery();
				while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setMemadd(rs.getString("memadd"));
				memberVO.setMemage(rs.getDate("memage"));
				memberVO.setMempassword(rs.getString("mempassword"));
				memberVO.setMemvarname(rs.getString("memvarname"));
				memberVO.setMemphone(rs.getString("memphone"));
				memberVO.setMememail(rs.getString("mememail"));
				memberVO.setMemsex(rs.getString("memsex"));
				memberVO.setMemcheck(rs.getString("memcheck"));
				memberVO.setMemshit(rs.getInt("memshit"));
				memberVO.setMemwow(rs.getInt("memwow"));
				memberVO.setMemballage(rs.getInt("memballage"));
				memberVO.setMemreb(rs.getInt("memreb"));
				memberVO.setMemscore(rs.getInt("memscore"));
				memberVO.setMemblock(rs.getInt("memblock"));
				memberVO.setMemast(rs.getInt("memast"));
				memberVO.setMemsteal(rs.getInt("memsteal"));
				memberVO.setMemimg(rs.getBytes("memimg"));
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
			
			
			return memberVO;
		}

	@Override
	public List<MemberVO> getAllByName(String memname){
	    
	List<MemberVO> list = new ArrayList<MemberVO>();
	MemberVO memberVO = null;
	String name =memname;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_ByName);
		pstmt.setString(1, name);
		pstmt.setString(2, name);
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
			// memberVO 也稱為 Domain objects
			memberVO = new MemberVO();
			memberVO.setMemno(rs.getInt("memno"));
			memberVO.setMemname(rs.getString("memname"));
			memberVO.setMemadd(rs.getString("memadd"));
			memberVO.setMemage(rs.getDate("memage"));
			memberVO.setMempassword(rs.getString("mempassword"));
			memberVO.setMemvarname(rs.getString("memvarname"));
			memberVO.setMemphone(rs.getString("memphone"));
			memberVO.setMememail(rs.getString("mememail"));
			memberVO.setMemsex(rs.getString("memsex"));
			memberVO.setMemcheck(rs.getString("memcheck"));
			memberVO.setMemshit(rs.getInt("memshit"));
			memberVO.setMemwow(rs.getInt("memwow"));
			memberVO.setMemballage(rs.getInt("memballage"));
			memberVO.setMemreb(rs.getInt("memreb"));
			memberVO.setMemscore(rs.getInt("memscore"));
			memberVO.setMemblock(rs.getInt("memblock"));
			memberVO.setMemast(rs.getInt("memast"));
			memberVO.setMemsteal(rs.getInt("memsteal"));
			memberVO.setMemimg(rs.getBytes("memimg"));
			list.add(memberVO);
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
	public Set<OrderedVO> getOrderedsByMemno(Integer memno) {
		Set<OrderedVO> set = new LinkedHashSet<OrderedVO>();
		OrderedVO orderedVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Ordereds_ByMemno_STMT);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				orderedVO = new OrderedVO();
				orderedVO.setOrdedno(rs.getInt("ordedno"));
				orderedVO.setMemno(rs.getInt("memno"));
				orderedVO.setOrddate(rs.getTimestamp("orddate"));
				orderedVO.setDeliverydate(rs.getTimestamp("deliverydate"));
				orderedVO.setSums(rs.getInt("sums"));
				set.add(orderedVO); // Store the row in the vector
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
	
	//修改會員申請球隊狀態
		@Override
		public void updateJoinCheck(MemberVO memberVO) {
			// TODO Auto-generated method stub
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_JOIN_CHECK);
				
				pstmt.setString(1, memberVO.getMemcheck());
				pstmt.setInt(2, memberVO.getMemno());
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

		
	    //找申請隊員
		@Override
		public List<MemberVO> getJoinMem(String memcheck) {
			// TODO Auto-generated method stub
			List<MemberVO> list = new ArrayList<MemberVO>();
			MemberVO memberVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_JOIN_MEM);
			    pstmt.setString(1,memcheck);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					memberVO = new MemberVO();
					memberVO.setMemno(rs.getInt("memno"));
					memberVO.setMemname(rs.getString("memcheck"));
					memberVO.setMemcheck(rs.getString("memcheck"));
					list.add(memberVO); // Store the row in the vector
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
		//找球隊隊員的個人影音照片
		@Override
		public List<VideoPictureVO> getTeamWinerPic(Integer memno) {
			// TODO Auto-generated method stub
			List<VideoPictureVO> list = new ArrayList<VideoPictureVO>();
			VideoPictureVO videoPictureVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_TEAMWINER_PIC);
				pstmt.setInt(1, memno);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					videoPictureVO = new VideoPictureVO();
					videoPictureVO.setVpno(rs.getInt("vpno"));
					videoPictureVO.setMemno(rs.getInt("memno"));
					videoPictureVO.setVpmsg(rs.getString("vpmsg"));
					videoPictureVO.setVpfile(rs.getBytes("vpfile"));
					list.add(videoPictureVO); // Store the row in the list
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
		public int updateSkill(MemberVO memberVO) {
			int updateCount = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATESKILL);
				
				pstmt.setInt(1, memberVO.getMemshit());
				pstmt.setInt(2, memberVO.getMemreb());
				pstmt.setInt(3, memberVO.getMemscore());
				pstmt.setInt(4, memberVO.getMemblock());
				pstmt.setInt(5, memberVO.getMemast());
				pstmt.setInt(6, memberVO.getMemsteal());
				pstmt.setInt(7, memberVO.getMemno());//
				updateCount = pstmt.executeUpdate();

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
			return updateCount;
//			
		}
		
		@Override
		public int updateSkill2(MemberVO memberVO) {
			int updateCount = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATESKILL2);
				
				pstmt.setInt(1, memberVO.getMemreb());
				pstmt.setInt(2, memberVO.getMemscore());
				pstmt.setInt(3, memberVO.getMemblock());
				pstmt.setInt(4, memberVO.getMemast());
				pstmt.setInt(5, memberVO.getMemsteal());
				pstmt.setInt(6, memberVO.getMemno());//
				updateCount = pstmt.executeUpdate();

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
			return updateCount;
//			
		}

		@Override
		public int updateInfo(MemberVO memberVO) {
			int updateCount = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATEINFO);
				pstmt.setString(1, memberVO.getMemname());
				pstmt.setString(2, memberVO.getMemadd());
				pstmt.setDate(3, memberVO.getMemage());
				pstmt.setString(4, memberVO.getMempassword());
				pstmt.setString(5, memberVO.getMemvarname());
				pstmt.setString(6, memberVO.getMemphone());
				pstmt.setString(7, memberVO.getMememail());
				pstmt.setString(8, memberVO.getMemsex());
				pstmt.setString(9, memberVO.getMemcheck());
				
				pstmt.setInt(10, memberVO.getMemballage());
				
				pstmt.setBytes(11, memberVO.getMemimg());
				
				pstmt.setInt(12, memberVO.getMemno());//
				updateCount = pstmt.executeUpdate();

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
			return updateCount;
		}
	
}