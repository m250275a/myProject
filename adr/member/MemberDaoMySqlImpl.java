package adr.member;

import adr.tool.util.Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDaoMySqlImpl implements MemberDao {

	public MemberDaoMySqlImpl() {
		super();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	@Override
	public MemberVO findByMememail(String mememail) {
		String sql = "SELECT memno, memname, mempassword FROM Member WHERE mememail LIKE ?";
		Connection conn = null;
		PreparedStatement ps = null;
		MemberVO memberVO = null;
		try {
			conn = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = conn.prepareStatement(sql);
			System.out.println(mememail);
			ps.setString(1, mememail);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Integer memno = rs.getInt(1);
				String memname = rs.getString(2);
				String mempassword = rs.getString(3);
				
				memberVO = new MemberVO(memno, memname, mememail, mempassword);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memberVO;
	}

}
