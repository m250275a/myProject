package com.challteam.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.challteam.model.ChallteamService;
import com.challteam.model.ChallteamVO;

public class ChallteamServlet extends HttpServlet {



	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("challteamInsert".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				Integer challmode = new Integer(req.getParameter("challmode").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				ChallteamService challteamSvc = new ChallteamService();
				ChallteamVO challteamVO = challteamSvc.addChallteam(teamno, challmode);
				if (challteamVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memberVO", challteamVO); // 資料庫取出的memberVO物件,存入req
				String url = "/member/listAllChallteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
				failureView.forward(req, res);
			}
		}
		if ("challteamDelete".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				Integer challmode = new Integer(req.getParameter("challmode").trim());
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				ChallteamService challteamSvc = new ChallteamService();
				challteamSvc.delete(teamno,challmode);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				 // 資料庫取出的memberVO物件,存入req
				String url = "/member/listAllChallteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
				failureView.forward(req, res);
			}
		}

	}//
}//
