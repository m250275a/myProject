package com.personalmsg.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.http.*;


import com.personalmsg.model.*;

public class PersonalMsgServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("msgno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入留言編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer msgno = null;
				try {
					msgno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);
				if (personalMsgVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("personalMsgVO", personalMsgVO); // 資料庫取出的personalmsgVO物件,存入req
				String url = "/personalMsg/listOnePersonalMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOnePersonalMsg.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllPersonalMsg.jsp 
		
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:
																// 可能為【/personalmsg/listAllPersonalMsg.jsp】
																
			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer msgno = new Integer(req.getParameter("msgno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("personalMsgVO", personalMsgVO); // 資料庫取出的personalMsgVO物件,存入req
				String url = "/personalMsg/update_personalMsg_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_personalMsg_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_personalMsg_input.jsp的請求
			
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:
																// 可能為【/personalMsg/listAllPersonalMsg.jsp】
																
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer msgno = new Integer(req.getParameter("msgno").trim());								
				Integer memno = null;
				try{
					
					memno = new Integer(req.getParameter("memno").trim());
					}catch(NumberFormatException e){
						memno=0;
						errorMsgs.add("請輸入會員編號");
					
					}
				
				Integer memedno = new Integer(req.getParameter("memedno").trim());
				String msg = req.getParameter("msg");			
				Timestamp msgdate = java.sql.Timestamp.valueOf(req.getParameter("msgdate"));
				
				
				PersonalMsgVO personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMsgno(msgno);
				personalMsgVO.setMemno(memno);
				personalMsgVO.setMemedno(memedno);
				personalMsgVO.setMsg(msg);
				personalMsgVO.setMsgdate(msgdate);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("personalMsgVO", personalMsgVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/update_personalMsg_input.jsp");
					failureView.forward(req, res);
					
					return; // 程式中斷
				}

				/***************************
				 * 2.開始修改資料
				 *****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.updatePersonalMsg(msgno,memno,memedno,msg,msgdate);
				
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				// 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/update_personalMsg_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				Integer memno = null;
				try{
					memno = new Integer(req.getParameter("memno").trim());
					}catch(NumberFormatException e){
						memno=0;
						errorMsgs.add("請輸入會員編號");
					}
				Integer memedno = new Integer(req.getParameter("memedno").trim());
				String msg = req.getParameter("msg");
				Timestamp msgdate = java.sql.Timestamp.valueOf(req.getParameter("msgdate"));
				PersonalMsgVO personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMemno(memno);
				personalMsgVO.setMemedno(memedno);
				personalMsgVO.setMsg(msg);
				personalMsgVO.setMsgdate(msgdate);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("personalMsgVO", personalMsgVO); // 含有輸入格式錯誤的personalMsgVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.開始新增資料
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/personalMsg/listAllPersonalMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPersonalMsg.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或
										// /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑:
																// 可能為【/personalMsg/listAllPersonalMsg.jsp】
																
			try {
				/***************************
				 * 1.接收請求參數
				 ***************************************/
				Integer msgno = new Integer(req.getParameter("msgno"));

				/***************************
				 * 2.開始刪除資料
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);
				personalMsgSvc.deletePersonalMsg(msgno);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		//來自select_jsp請求
		if("listMsgs_ByMemedno".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer memedno = new Integer(req.getParameter("memedno"));

				/*************************** 2.開始查詢資料 ****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				List<PersonalMsgVO> set = personalMsgSvc.getMsgsByMemedno(memedno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listMsgs_ByMemedno", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listMsgs_ByMemno".equals(action))
					url = "/personalMsg/listMsgs_ByMemno.jsp";        // 成功轉交
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("insertByMsg".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				Integer memno = 7010;
				Integer memedno = 7006;
				String msg = req.getParameter("msg");
				Timestamp msgdate = new Timestamp(System.currentTimeMillis());
				PersonalMsgVO personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMemno(memno);
				personalMsgVO.setMemedno(memedno);
				personalMsgVO.setMsg(msg);
				personalMsgVO.setMsgdate(msgdate);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("personalMsgVO", personalMsgVO); // 含有輸入格式錯誤的personalMsgVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.開始新增資料
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/AA103G5UI/memberpage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPersonalMsg.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insertByJson".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String requestURL = req.getParameter("requestURL");
				int memno = Integer.valueOf(req.getParameter("memno"));
				int memedno = Integer.valueOf(req.getParameter("memedno"));
				String msg = req.getParameter("msg");
				Timestamp msgdate = new Timestamp(System.currentTimeMillis());
				
				
				PersonalMsgVO personalMsgVO = new PersonalMsgVO();
				personalMsgVO.setMemno(memno);
				personalMsgVO.setMemedno(memedno);
				personalMsgVO.setMsg(msg);
				personalMsgVO.setMsgdate(msgdate);
				/***************************
				 * 2.開始新增資料
				 ***************************************/
				
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = requestURL;
//				if(memno==memedno){
//					url = "/front-end/memberPage.jsp";
//				}else{
//					HttpSession session = req.getSession();
//					String location =(String)session.getAttribute("location");
//					res.sendRedirect(location);
//					if(location==null){
//						session.setAttribute("location", req.getRequestURI());
//						res.sendRedirect(req.getContextPath());
//						return;
//					}
//				}
				 
				
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memberPage.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}
