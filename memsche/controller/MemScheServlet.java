package com.memsche.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.memsche.model.MemScheService;
import com.memsche.model.MemScheVO;



@WebServlet("/MemScheServlet")
public class MemScheServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
	
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("memsche");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/memsche/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer memsche = null;
				try {
					memsche = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/memsche/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemScheService memscheSvc = new MemScheService();
				MemScheVO memscheVO = memscheSvc.getOneMemSche(memsche);
				if (memscheVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/memsche/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memscheVO", memscheVO); // 資料庫取出的empVO物件,存入req
				String url = "/memsche/listOneMemSche.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memsche/select_page.jsp");
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
				Integer memsche = new Integer(req.getParameter("memsche"));
				
				/***************************2.開始查詢資料****************************************/
				MemScheService memscheSvc = new MemScheService();
				MemScheVO memscheVO = memscheSvc.getOneMemSche(memsche);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("memscheVO", memscheVO);         // 資料庫取出的empVO物件,存入req
				String url = "/memsche/update_MemSche_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memsche/listAllMemSche.jsp");
				failureView.forward(req, res);
			}
		}
		
		
if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer memsche = new Integer(req.getParameter("memsche").trim());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer free = new Integer(req.getParameter("free").trim());
				Date mdate = java.sql.Date.valueOf(req.getParameter("mdate").trim());				
				

				MemScheVO memscheVO = new MemScheVO();
				memscheVO.setMemsche(memsche);
				memscheVO.setMemno(memno);
				memscheVO.setFree(free);
				memscheVO.setMdate(mdate);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memscheVO", memscheVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/memsche/update_MemSche_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemScheService memscheSvc = new MemScheService();
				memscheVO = memscheSvc.updateMemSche(memsche,memno,free,mdate);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
				req.setAttribute("memscheVO", memscheVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/memsche/listAllMemSche.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/update_emp_input.jsp");
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
				Integer free = new Integer(req.getParameter("free").trim());
				Date mdate = java.sql.Date.valueOf(req.getParameter("mdate").trim());
				
				
				MemScheVO memscheVO = new MemScheVO();
				memscheVO.setMemno(memno);
				memscheVO.setFree(free);
				memscheVO.setMdate(mdate);
				

				// Send the use back to the form, if there were errors
				if(memscheVO != null){
					errorMsgs.add("行程表新增好了!");
				}
				
				/***************************2.開始新增資料***************************************/
				MemScheService memscheSvc = new MemScheService();
				memscheVO = memscheSvc.addMemSche(memno,free,mdate);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/memberPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memsche/addMemSche.jsp");
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
				Integer memsche = new Integer(req.getParameter("memsche"));
				
				/***************************2.開始刪除資料***************************************/
				MemScheService memscheSvc = new MemScheService();
				memscheSvc.deleteMemSche(memsche);
				
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/memberPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memsche/listAllMemSche.jsp");
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
							.getRequestDispatcher("/memsche/select_page.jsp");
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
							.getRequestDispatcher("/memsche/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemScheService memscheSvc = new MemScheService();
				List<MemScheVO> memscheVO = memscheSvc.getByMemno(memno);
				if (memscheVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if(memscheVO != null){
					errorMsgs.add("已刪除行程表!");
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("memscheVO", memscheVO); // 資料庫取出的empVO物件,存入req
				String url = "/memsche/listAllByMemno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/memsche/select_page.jsp");
				failureView.forward(req, res);
			}
		}	
		
	}
}


