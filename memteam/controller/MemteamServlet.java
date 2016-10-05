package com.memteam.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.memteam.model.MemteamService;
import com.memteam.model.MemteamVO;
import com.team.model.TeamService;
import com.team.model.TeamVO;

public class MemteamServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAllMemteam".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("memno");

				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入好友編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("好友編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				List<MemteamVO> friendVO = friendSvc.getAll();
				if (friendVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memberVO", friendVO); // 資料庫取出的memberVO物件,存入req
				String url = "/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("memteamInsert".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				MemteamVO friendVO = friendSvc.addMemteam(memno, teamno);
				if (friendVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memberVO", friendVO); // 資料庫取出的memberVO物件,存入req
				String url = "/member/listAllMemteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
				failureView.forward(req, res);
			}
		}
		if ("memteamDelete".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				friendSvc.delete(memno, teamno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				// 資料庫取出的memberVO物件,存入req
				String url = "/member/listAllMemteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
				failureView.forward(req, res);
			}
		}
		// 會員申請加入球隊
		if ("joinTeam".equals(action)) { // 來自teampage.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				String memcheck = null;
				MemteamService memteams = new MemteamService();
				List<MemteamVO> list = memteams.getTeamBYMemno(memno);
				for (MemteamVO teamlist : list) {

					Integer no = teamlist.getTeamno();

					if (new Integer(req.getParameter("teamno")).equals(no)) {
						errorMsgs.add("已加入球隊");
					}
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				memcheck = req.getParameter("teamno").trim();
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.updateJoinCheck(memno, memcheck);

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				
				if (memberVO != null) {
					errorMsgs.add("已申請成功,待審核");
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				if (!errorMsgs.isEmpty()) {

					String url = "/front-end/teampage.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交teampage.jsp
					successView.forward(req, res);
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法申請:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
				failureView.forward(req, res);
			}
		}
		// 退出球隊
		if ("DeleteTeam".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				String memcheck = req.getParameter("memcheck");

				MemberService memberSvc = new MemberService();
				MemberVO memberVO = new MemberVO();
				memberVO = memberSvc.updateJoinCheck(memno, memcheck);
				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemteamService memteamSvc = new MemteamService();
				memteamSvc.deleteTeam(memno, teamno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				// 資料庫取出的memberVO物件,存入req
				String url = "/front-end/teamHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交teampage.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
				failureView.forward(req, res);
			}
		}

		// 接受會員加入球隊
		if ("pass".equals(action)) { // 來自memList.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				// 取得使用者(memadno),所屬球隊(teamno),申請者(memno),接受申請(memcheck)
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer memno = new Integer(req.getParameter("memno"));
				String memcheck = req.getParameter("memcheck");
				Integer memadno = new Integer(req.getParameter("memadno"));

				// 權限判斷
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = teamSvc.getOneTeam(teamno);
				MemteamVO memteamVO = null;	
				MemteamService memteamSvc = new MemteamService();
				if (memadno.equals(teamVO.getTeamadmin())) {

					memteamVO = new MemteamVO();
					memteamVO.setMemno(memno);
					memteamVO.setTeamno(teamno);
					memteamVO = memteamSvc.joinTeam(memno, teamno);
				} else {
					errorMsgs.add("非球隊管理員");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				if(memteamVO != null){
					errorMsgs.add("已新增隊員!");
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
	
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = new MemberVO();
				memberVO.setMemno(memno);
				memberVO.setMemcheck(memcheck);
				memberVO = memberSvc.updateJoinCheck(memno, memcheck);// update memberVO物件
				
				String url = "/front-end/memList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交memList.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
				failureView.forward(req, res);
			}
		}

		// 拒絕申請入隊
		if ("nopass".equals(action)) { // 來自memList.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				// 取得使用者(memadno),所屬球隊(teamno),申請者(memno),狀態(memcheck)
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer memno = new Integer(req.getParameter("memno"));
				String memcheck = req.getParameter("memcheck");
				Integer memadno = new Integer(req.getParameter("memadno"));

				// 權限判斷
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = teamSvc.getOneTeam(teamno);
				MemberVO memberVO = null;
				MemberService memberSvc = new MemberService();
				if (memadno.equals(teamVO.getTeamadmin())) {

					memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setMemcheck(memcheck);
					memberVO = memberSvc.updateJoinCheck(memno, memcheck);
				} else {
					errorMsgs.add("非球隊管理員");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				if(memberVO != null){
					errorMsgs.add("已拒絕申請!");
				}
				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemteamService memteamSvc = new MemteamService();
				memteamSvc.delete(memno, teamno);

				// Send the use back to the form, if there were errors
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				// 資料庫取出的memberVO物件,存入req
				String url = "/front-end/memList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交memList.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
				failureView.forward(req, res);
			}
		}

	}//
}//
