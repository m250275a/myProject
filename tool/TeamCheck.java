package tool;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.controller.AdminServlet;
import com.admin.model.AdminService;
import com.member.model.MemberVO;
import com.memteam.model.MemteamService;
import com.memteam.model.MemteamVO;
import com.team.model.TeamVO;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class TeamCheck implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		// String user=config.getInitParameter("user");
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
		MemteamService memteamSvc = new MemteamService();
		List<MemteamVO> myteamlist = memteamSvc.getTeamBYMemno(memberVO.getMemno());
		TeamVO teamVO = (TeamVO) session.getAttribute("teamVO");// 當前進入的球隊
		for (MemteamVO list : myteamlist) {
			if (teamVO.getTeamno().equals(list.getTeamno())) {
				chain.doFilter(req, res);
				return;
			}
		}

		List<String> errorMsgs = new LinkedList<String>();
		errorMsgs.add("尚未加入球隊");
		req.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
		failureView.forward(req, res);

	}
}