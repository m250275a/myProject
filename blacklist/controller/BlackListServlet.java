package com.blacklist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blacklist.model.BlackListService;
import com.blacklist.model.BlackListVO;



@WebServlet("/BlacklistServlet")
public class BlackListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
	
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("blackno");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer blackno = null;
				try {
					blackno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				BlackListService blackSvc = new BlackListService();
				BlackListVO blacklistVO = blackSvc.getOneBlack(blackno);
				if (blacklistVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // 資料庫取出的empVO物件,存入req
				String url = "/blacklist/listOneBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer blackno = new Integer(req.getParameter("blackno"));
				
				/***************************2.開始查詢資料****************************************/
				BlackListService blackSvc = new BlackListService();
				BlackListVO blacklistVO = blackSvc.getOneBlack(blackno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("blacklistVO", blacklistVO);         // 資料庫取出的empVO物件,存入req
				String url = "/blacklist/update_BlackList_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/listAllBlackList.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer blackno = new Integer(req.getParameter("blackno").trim());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memedno = new Integer(req.getParameter("memedno").trim());				
				

				

				BlackListVO blacklistVO = new BlackListVO();
				blacklistVO.setBlackno(blackno);
				blacklistVO.setMemno(memno);
				blacklistVO.setMemedno(memedno);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blacklistVO", blacklistVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/update_BlackList_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				BlackListService blackSvc = new BlackListService();
				blacklistVO = blackSvc.updateBlack(blackno,memno,memedno);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/blacklist/listOneBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/update_BlackList_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memedno = new Integer(req.getParameter("memedno").trim());				
				
				BlackListVO blacklistVO = new BlackListVO();
				blacklistVO.setMemno(memno);
				blacklistVO.setMemedno(memedno);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blackListVO", blacklistVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				BlackListService blackSvc = new BlackListService();
				blacklistVO = blackSvc.addBlack(memno,memedno);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/blacklist/listAllBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer blackno = new Integer(req.getParameter("blackno"));
				
				/***************************2.開始刪除資料***************************************/
				BlackListService blackSvc = new BlackListService();
				blackSvc.deleteBlack(blackno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/blacklist/listAllBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("getForMemno".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("memno");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				BlackListService blackSvc = new BlackListService();
				List<BlackListVO> blacklistVO = blackSvc.getByMemno(memno);
				if (blacklistVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/listAllByMemno.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // 資料庫取出的empVO物件,存入req
				String url = "/blacklist/listAllByMemno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("insertByJson".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String str = req.getParameter("memno");
				String str1 = req.getParameter("memedno");
				Integer memno = new Integer(str);
				Integer memedno = new Integer(str1);
				
				BlackListVO blackListVO = new BlackListVO();
				blackListVO.setMemno(memno);
				blackListVO.setMemedno(memedno);
				/***************************
				 * 2.開始新增資料
				 ***************************************/
				BlackListService blacklistSvc = new BlackListService();
				blacklistSvc.addBlack(memno, memedno);
				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				PrintWriter out = res.getWriter();
				out.print("已成功新增:"+str1);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.print("新增失敗:已新增過");
			}
		}
		
if ("delete2".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
	try {
		/***************************1.接收請求參數***************************************/
		Integer blackno = new Integer(req.getParameter("blackno"));
		
		/***************************2.開始刪除資料***************************************/
		BlackListService blacklistSvc = new BlackListService();
		blacklistSvc.deleteBlack(blackno);
		
		/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
		String url = "/front-end/memberPage.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
		successView.forward(req, res);
		/*************************** 其他可能的錯誤處理 **********************************/
	} catch (Exception e) {
//		RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
//		failureView.forward(req, res);
	}
}		

	}
}


