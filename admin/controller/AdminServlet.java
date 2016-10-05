package com.admin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminService;
import com.admin.model.AdminVO;

public class AdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String update = "/back-end/update_admin_input.jsp";
		String listAllAdmin = "/back-end/listAllAdmin.jsp";
		String listOneAdmin = "/back-end/listOneAdmin.jsp";
		String login = "/back-end/Home.jsp";
		String addAdmin = "/back-end/addAdmin.jsp";
		HttpSession session = req.getSession();

		// TODO///////////////////////// ///////////////////////////
		// //登入//////////////////
		if ("login".equals(action)) {
			// 錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 取得使用者輸入的帳號密碼
				Integer adminno = new Integer(req.getParameter("adminno").trim());
				String adminpsw = req.getParameter("password").trim();

				if (adminpsw.equals("")) {
					errorMsgs.add("請輸入密碼");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/************************************************************************/
				// 開始查詢
				AdminService memSvc = new AdminService();
				AdminVO adminVO = memSvc.getOneAdmin(adminno);
				// 帳密判斷錯誤

				if (adminVO.getAdminno().equals(adminno) && (adminVO.getAdminpsw()).equals(adminpsw)) {

					
					session.setAttribute("adminVO", adminVO);

					
					String url = "/back-end/backhead.jsp";
					

					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					return;
					
				} else {
					errorMsgs.add("error密碼");
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*********************** 轉交 *************************/
				// 查詢完成，開始轉交
			} catch (NumberFormatException e) {
				errorMsgs.add("帳號錯誤");

				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("帳號或密碼錯誤");

				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}
		// TODO//
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp請求

			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope, in case we need to
			// send the errorpage view
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 接收請求參數--輸入格式錯誤處理

				String str = req.getParameter("adminno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}

				// send the use back to the form if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return; // program stop
				}

				Integer adminno = null;
				try {
					adminno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}

				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return; // program stop
				}

				// 開始查詢
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(adminno);
				if (adminVO == null) {
					errorMsgs.add("查無資料");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return; // program stop
				}

				// 查詢完成,準備轉交
				req.setAttribute("adminVO", adminVO);// 資料庫取出adminvo

				RequestDispatcher successView = req.getRequestDispatcher(listOneAdmin);
				// 成功轉交listOneAdmin.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}

		// TODO//// 來自listAllAdmin.jsp請求
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope in case we need to
			// send the errorpage view
			req.setAttribute("errormsgs", errorMsgs);

			// 接收請求參數
			try {
				Integer adminno = new Integer(req.getParameter("adminno"));

				// 開始查詢
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(adminno);

				// 查詢完成,準備轉交
				req.setAttribute("adminVO1", adminVO);

				// 成功轉交update_admin_input.jsp
				RequestDispatcher successView = req.getRequestDispatcher(update);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料");
				RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
				failureView.forward(req, res);
			}
		}

		// TODO// 來自update_admin_input.jsp請求
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope in case we need to
			// send the errorpage view
			req.setAttribute("errorMsgs", errorMsgs);

			// 接受請求參數--錯誤格式處
			 try {
			Integer adminno = new Integer(req.getParameter("adminno"));

			String adminname = req.getParameter("adminname").trim();

			if (adminname == null || (adminname.trim()).length() == 0) {
				errorMsgs.add("請輸入員工姓名");
			}

			String adminvarname = req.getParameter("adminvarname").trim();
			String adminid = req.getParameter("adminid").trim();
			String adminphone = req.getParameter("adminphone");

			if (adminphone == null || (adminphone.trim()).length() == 0) {
				errorMsgs.add("請輸入員工電話");
			}

			String adminaddr = req.getParameter("adminaddr");

			if (adminaddr == null || (adminaddr.trim()).length() == 0) {
				errorMsgs.add("請輸入員工地址");
			}

			String adminpsw = req.getParameter("adminpsw");
			String adminpsw2 = req.getParameter("adminpsw2");

			if (adminpsw.equals("")) {
				errorMsgs.add("請輸入密碼");
			}
			if (!adminpsw.equals(adminpsw2)) {
				errorMsgs.add("密碼不同");
			}

			String adminemail = req.getParameter("adminemail");

			if (adminemail == null || (adminemail.trim()).length() == 0) {
				errorMsgs.add("請輸入員工E-mail");
			}

			String adminlevel = req.getParameter("adminlevel");

			AdminVO adminVO = new AdminVO();

			adminVO.setAdminno(adminno);
			adminVO.setAdminname(adminname);
			adminVO.setAdminvarname(adminvarname);
			adminVO.setAdminid(adminid);
			adminVO.setAdminphone(adminphone);
			adminVO.setAdminaddr(adminaddr);
			adminVO.setAdminpsw(adminpsw);
			adminVO.setAdminemail(adminemail);
			adminVO.setAdminlevel(adminlevel);

			// send the use back to the form if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminVO", adminVO);
				RequestDispatcher failureView = req.getRequestDispatcher(update);
				failureView.forward(req, res);
				return; // 程式中斷
			}

			// 開始修改資料
			AdminService adminSvc = new AdminService();
			adminVO = adminSvc.updateAdmin(adminno, adminname, adminvarname, adminid, adminphone, adminaddr, adminpsw,
					adminemail, adminlevel);

			// 修改完成,準備轉交
//			req.setAttribute("adminVO", adminVO);
			RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin);
			successView.forward(req, res);

			// 其他錯誤可能
			 } catch (Exception e) {
			 errorMsgs.add("修改失敗");
			 RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
			 failureView.forward(req, res);
			 }
		}
		// TODO// //// 註冊
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// 接收請求參數-錯誤格式處理
			try {

				String adminname = req.getParameter("adminname").trim();

				if (adminname == null || (adminname.trim()).length() == 0) {
					errorMsgs.add("請輸入員工姓名");
				}

				String adminvarname = req.getParameter("adminvarname").trim();
				
				String adminid = req.getParameter("adminid").trim();
				if (adminid == null || (adminid.trim()).length() == 0) {
					errorMsgs.add("請輸入員工身分證字號");
				}
				String adminphone = req.getParameter("adminphone");

				if (adminphone == null || (adminphone.trim()).length() == 0) {
					errorMsgs.add("請輸入員工電話");
				}

				String adminaddr = req.getParameter("adminaddr");

				if (adminaddr == null || (adminaddr.trim()).length() == 0) {
					errorMsgs.add("請輸入員工地址");
				}

				char ch[] = new char[6];
				String adminpsw = "";
				for (int i = 0; i < 6; i++) {
					ch[i] = (char) (Math.random() * 26 + 97);
					adminpsw += ch[i];
				}

				String adminemail = req.getParameter("adminemail");

				if (adminemail == null || (adminemail.trim()).length() == 0) {
					errorMsgs.add("請輸入員工E-mail");
				}

				String adminlevel = req.getParameter("adminlevel");
				AdminVO adminVO = new AdminVO();
				adminVO.setAdminname(adminname);
				adminVO.setAdminvarname(adminvarname);
				adminVO.setAdminid(adminid);
				adminVO.setAdminphone(adminphone);
				adminVO.setAdminaddr(adminaddr);
				adminVO.setAdminpsw(adminpsw);
				adminVO.setAdminemail(adminemail);
				adminVO.setAdminlevel(adminlevel);

				// send the use back to the form if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminVO", adminVO);
					RequestDispatcher failureView = req.getRequestDispatcher(addAdmin);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				// 開始新增資料

				AdminService adminSvc = new AdminService();
				adminVO = adminSvc.addAdmin(adminname, adminvarname, adminid, adminphone, adminaddr, adminpsw,
						adminemail, adminlevel);


				/****************************
				 * 轉交後，發送電子郵件
				 *********************************************/
				
				 MailService mailscv = new MailService();				
				 String subject = "後端管理者帳號註冊成功";
				 String messageText= "Hello! " + adminname + "你的帳號已註冊成功\n密碼:"
				 + adminpsw + "\n (已經啟用)";
				 String to = "aa103g05@gmail.com";
				 mailscv.sendMail(to, subject, messageText);
//				 mailscv.sendMail(adminemail, subject, messageText);

				 // 新增完成,準備轉交
//				 req.setAttribute("whichPage", new Integer(2));
				 RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin);
				 successView.forward(req, res);
				// 其他錯誤可能
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				req.getRequestDispatcher("admin/listAllAdmin.jsp");
			}
		}
		// TODO
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// 接收請求參數
			try {
				Integer adminno = new Integer(req.getParameter("adminno"));

				// 開始刪除資料

				AdminService adminSvc = new AdminService();
				adminSvc.deleteAdmin(adminno);

				// 刪除完成,準備轉交

				RequestDispatcher success = req.getRequestDispatcher(listAllAdmin);
				success.forward(req, res);

				// 其他錯誤格式
			} catch (Exception e) {
				errorMsgs.add("刪除資料錯誤" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
				failureView.forward(req, res);
			}
		}

		// 登出
		if ("clean".equals(action)) {
			if (!session.isNew()) {
				session.invalidate();
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/Home.jsp");
				successView.forward(req, res); // get a new session
			}

		}

	}

}
