package com.challmode.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.challmode.model.*;

public class ChallmodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("challmode");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入挑戰模式編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer challmode = null;
				try {
					challmode = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("挑戰模式編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				ChallmodeVO challmodeVO = challmodeSvc.getOneChallmode(challmode);
				if (challmodeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("challmodeVO", challmodeVO); // 資料庫取出的challmodeVO物件,存入req
				String url = "/challmode/listOneChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneChallmode.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllChallmode.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer challmode = new Integer(req.getParameter("challmode"));
				
				/***************************2.開始查詢資料****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				ChallmodeVO challmodeVO = challmodeSvc.getOneChallmode(challmode);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("challmodeVO", challmodeVO);         // 資料庫取出的challmodeVO物件,存入req
				String url = "/challmode/update_challmode_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_challmode_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/listAllChallmode.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_challmode_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer challmode = new Integer(req.getParameter("challmode").trim());
				String challcontent = req.getParameter("challcontent").trim();
			

				ChallmodeVO challmodeVO = new ChallmodeVO();
				challmodeVO.setChallmode(challmode);
				challmodeVO.setChallcontent(challcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("challmodeVO", challmodeVO); // 含有輸入格式錯誤的challmodeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/update_challmode_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeVO = challmodeSvc.updateChallmode(challmode, challcontent);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("challmodeVO", challmodeVO); // 資料庫update成功後,正確的的challmodeVO物件,存入req
				String url = "/challmode/listOneChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneChallmode.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/update_challmode_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addChallmode.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String challcontent = req.getParameter("challcontent").trim();
				
				
				ChallmodeVO challmodeVO = new ChallmodeVO();
				challmodeVO.setChallcontent(challcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("challmodeVO", challmodeVO); // 含有輸入格式錯誤的challmodeVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/addChallmode.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeVO = challmodeSvc.addChallmode(challcontent);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/challmode/listAllChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllChallmode.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/addChallmode.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllChallmode.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer challmode = new Integer(req.getParameter("challmode"));
				
				/***************************2.開始刪除資料***************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeSvc.deleteChallmode(challmode);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/challmode/listAllChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/listAllChallmode.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
