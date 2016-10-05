package com.member.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.blacklist.model.BlackListService;
import com.blacklist.model.BlackListVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.member.model.*;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.ordered.model.*;
import com.team.model.TeamService;
import com.team.model.TeamVO;

import adr.member.MemberDao;
import adr.member.MemberDaoMySqlImpl;


@MultipartConfig(fileSizeThreshold=1024*1024,maxFileSize=5*1024*1024,maxRequestSize=5*5*1024*1024) 

public class MemberServlet extends HttpServlet {
	private MemberVO toVO(Integer memno, String memname, String memadd, java.sql.Date memage, String mempassword,
			String memvarname, String memphone, String mememail, String memsex, String memcheck, Integer memshit,
			Integer memwow, Integer memballage, Integer memreb, Integer memscore, Integer memblock, Integer memast,
			Integer memsteal,byte[] memimg) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemno(memno);
		memberVO.setMemname(memname);
		memberVO.setMemadd(memadd);
		memberVO.setMemage(memage);
		memberVO.setMempassword(mempassword);
		memberVO.setMemvarname(memvarname);
		memberVO.setMemphone(memphone);
		memberVO.setMememail(mememail);
		memberVO.setMemsex(memsex);
		memberVO.setMemcheck(memcheck);
		memberVO.setMemshit(memshit);
		memberVO.setMemwow(memwow);
		memberVO.setMemballage(memballage);
		memberVO.setMemreb(memreb);
		memberVO.setMemscore(memscore);
		memberVO.setMemblock(memblock);
		memberVO.setMemast(memast);
		memberVO.setMemsteal(memsteal);
		memberVO.setMemimg(memimg);
		return memberVO;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//從個人塗鴉牆的追蹤送來
		int memnoID = Integer.valueOf(req.getParameter("memno"));//7016
		int memedno = Integer.valueOf(req.getParameter("memedno"));//7015
		
		//比對資料庫memedno有沒有黑memnoID
		BlackListService blacklistSvc =new BlackListService();
		List<BlackListVO> list = blacklistSvc.getByMemno(new Integer(memedno));//用7015去查他的黑名單
		//預設沒有被黑，有的話改變URL的值
		String url="/front-end/memberPage2.jsp?";
		for(int i=0;i<list.size();i++){
			
			 if(list.get(i).getMemedno()==memnoID){
				 url = "/front-end/blackListPage.jsp?";
					break;
			 }
		}
		//放memberedVO到session，以便memberPage2.jsp可以拿到memberedVO
		MemberService memberSvc = new MemberService();
		MemberVO memberedVO = memberSvc.getOneMember(memedno);
		HttpSession session = req.getSession();
		session.setAttribute("memberedVO", memberedVO);
		//最後判斷memnoID，memedno是不是同一個人
				if(memnoID==memedno){
					url="/front-end/memberPage.jsp?";
				}
		
		
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String login ="/member/login.jsp";
		
	//// TODO insert//////////////////////////////////////////////////////////////////////////
		//註冊
		if ("insert".equals(action)) { // 來自addMember.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer memno = null;
				String memname = req.getParameter("memname").trim();
				String memadd = req.getParameter("memadd").trim();
				String mempassword = req.getParameter("mempassword").trim();
				String memvarname = req.getParameter("memvarname").trim();
				String memphone = req.getParameter("memphone").trim();
				String mememail = req.getParameter("mememail").trim();
				String memsex = req.getParameter("memsex").trim();
				String memcheck = req.getParameter("memcheck").trim();
				Integer memshit = new Integer(req.getParameter("memshit").trim());
				Integer memwow = new Integer(req.getParameter("memwow").trim());
				Integer memballage = new Integer(req.getParameter("memballage").trim());
				Integer memreb = new Integer(req.getParameter("memreb").trim());
				Integer memscore = new Integer(req.getParameter("memscore").trim());
				Integer memblock = new Integer(req.getParameter("memblock").trim());
				Integer memast = new Integer(req.getParameter("memast").trim());
				Integer memsteal = new Integer(req.getParameter("memsteal").trim());
				String mempassword2 = req.getParameter("mempassword2").trim();
				Part memimg0 = req.getPart("memimg");
				if (memimg0 == null || memimg0.getSize() == 0) {
					errorMsgs.add("請選擇照片");
				}
				InputStream in0 = memimg0.getInputStream();
				byte[] memimg = new byte[in0.available()];
				in0.read(memimg);
				in0.close();
				if(!mememail.matches("[a-zA-Z0-9]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]?+.+[a-zA-Z0-9]")){
					errorMsgs.add("email格式錯誤!");
				}
						
				if (mempassword.equals("")) {
					errorMsgs.add("請輸入密碼!");
				} else if (!mempassword.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,10}$")|| !mempassword.equals(mempassword2)) {
					errorMsgs.add("密碼確認錯誤，最少要輸入5到10個數字或英文字母!");
				}
				MemberService memSvc = new MemberService();
				if (memSvc.GET_MAIL_STMT(mememail) != null) {
					errorMsgs.add("EMAIL已註冊!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
					failureView.forward(req, res);
					return;
				}
				java.sql.Date memage = null;
		
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				MemberVO memberVO = toVO(memno, memname, memadd, memage, mempassword, memvarname, memphone, mememail,
						memsex, memcheck, memshit, memwow, memballage, memreb, memscore, memblock, memast, memsteal,memimg);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/**************************** 2.開始新增資料 ***************************************/

				memberVO = memSvc.addMember(memberVO);// 新增

				/**************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("資料不完整或錯誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
				failureView.forward(req, res);
			}
		}

	//// TODO login//////////////////////////////////////////////////////////////////
		//登入
		if ("login".equals(action)) {
			// 錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 取得使用者輸入的帳號密碼
				String email = req.getParameter("email").trim();
				String password = req.getParameter("password").trim();
			
				if (email.length() == 0 ) {
					errorMsgs.add("請輸入帳號");
				}
				if (password.length() == 0 ) {
					errorMsgs.add("請輸入密碼");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/************************************************************************/
				// 開始查詢
				MemberService memSvc = new MemberService();
				MemberVO memberVO = memSvc.GET_MAIL_STMT(email);
				// 帳密判斷錯誤

				if (memberVO.getMememail().equals(email) && (memberVO.getMempassword()).equals(password)) {
					HttpSession session = req.getSession();
					session.setAttribute("memberVO", memberVO);
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/index.jsp");
					successView.forward(req, res);
					return;
				}else{
					errorMsgs.add("error密碼");
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*********************** 轉交 *************************/
				// 查詢完成，開始轉交
			}catch (NumberFormatException e) {
				errorMsgs.add("帳號錯誤");
				errorMsgs.add("回傳訊息:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("帳號或密碼錯誤");
				errorMsgs.add("回傳訊息:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}

	//// TODO getOne_For_Display///////////////////////////////////////////////////////////////
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

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
					errorMsgs.add("請輸入memno");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("memno格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);
				if (memberVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
				String url = "/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneMember.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}
	//// TODO getOne_For_Update////////////////////////////////////////////////////////////////
		if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp 或
													// /dept/listMembers_ByDeptno.jsp
													// 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:
																// 可能為【/member/listAllMember.jsp】
															// 【/dept/listMembers_ByDeptno.jsp】
																// 或 【
																// /dept/listAllDept.jsp】

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
				String url = "/member/update_member_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_member_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		//
		//// TODO update/////////////////////////////////////////////////////////
		if ("update".equals(action)) { // 來自update_member_input.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:
																// 可能為【/member/listAllMember.jsp】
			// 或
			// 【/dept/listMembers_ByDeptno.jsp】
			// 或 【
			// /dept/listAllDept.jsp】
			// 或 【
			// /member/listMembers_ByCompositeQuery.jsp】

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				String memname = req.getParameter("memname").trim();
				String memadd = req.getParameter("memadd").trim();
				String mempassword = req.getParameter("mempassword").trim();
				String memvarname = req.getParameter("memvarname").trim();
				String memphone = req.getParameter("memphone").trim();
				String mememail = req.getParameter("mememail").trim();
				String memsex = req.getParameter("memcheck").trim();
				String memcheck = req.getParameter("memcheck").trim();
				Integer memshit = new Integer(req.getParameter("memshit").trim());
				Integer memwow = new Integer(req.getParameter("memwow").trim());
				Integer memballage = new Integer(req.getParameter("memballage").trim());
				Integer memreb = new Integer(req.getParameter("memreb").trim());
				Integer memscore = new Integer(req.getParameter("memscore").trim());
				Integer memblock = new Integer(req.getParameter("memblock").trim());
				Integer memast = new Integer(req.getParameter("memast").trim());
				Integer memsteal = new Integer(req.getParameter("memsteal").trim());
				Part memimg0 = req.getPart("memimg");
				if (memimg0 == null || memimg0.getSize() == 0) {
					errorMsgs.add("請選擇照片");
				}
				InputStream in0 = memimg0.getInputStream();
//				byte[] buf0 = new byte[in0.available()];
				byte[] memimg = new byte[in0.available()];
//				in0.read(buf0);
				in0.read(memimg);
				in0.close();
//				byte[] courtimg = buf0;	
				java.sql.Date memage = null;
				if (mempassword == null || mempassword.length() == 0) {
					errorMsgs.add("請輸入密碼!");
				} else if (mempassword != null && !mempassword.matches("[a-zA-Z0-9]{5,10}")) {
					errorMsgs.add("密碼格式錯誤，最少要輸入5到10個數字或英文字母!");
				}
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				MemberVO memberVO = toVO(memno, memname, memadd, memage, mempassword, memvarname, memphone, mememail,
						memsex, memcheck, memshit, memwow, memballage, memreb, memscore, memblock, memast, memsteal,memimg);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/***************************
				 * 2.開始修改資料
				 *****************************************/
				MemberService memSvc = new MemberService();
				memberVO = memSvc.updateMember(memberVO);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listMembers_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listMembers_ByDeptno",deptSvc.getMembersByDeptno(deptno));
				// // 資料庫取出的list物件,存入request

				if (requestURL.equals("/member/listMembers_ByCompositeQuery.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<MemberVO> list = memSvc.getAll(map);
					req.setAttribute("/member/update_member_input.jsp", list); // 複合查詢,
																				// 資料庫取出的list物件,存入request
				}

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
				failureView.forward(req, res);
			}
		}
		//
		////TODO getOne_For_Update//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if ("delete".equals(action)) { // 來自listAllMember.jsp 或
										// /dept/listMembers_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑:
																// 可能為【/member/listAllMember.jsp】
																// 或
																// 【/dept/listMembers_ByDeptno.jsp】
																// 或 【
																// /dept/listAllDept.jsp】
																// 或 【
																// /member/listMembers_ByCompositeQuery.jsp】

			try {
				/***************************
				 * 1.接收請求參數
				 ***************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/***************************
				 * 2.開始刪除資料
				 ***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.deleteMember(memno);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listMembers_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listMembers_ByDeptno",deptSvc.getMembersByDeptno(memberVO.getDeptno()));
				// // 資料庫取出的list物件,存入request

				if (requestURL.equals("/member/listMembers_ByCompositeQuery.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<MemberVO> list = memberSvc.getAll(map);
					req.setAttribute("listMembers_ByCompositeQuery", list); // 複合查詢,
																			// 資料庫取出的list物件,存入request
				}

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

		////TODO getOne_For_Update////////////////////////////////////////////////////////////////
		if ("listMembers_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.將輸入資料轉為Map
				 **********************************/
				// 採用Map<String,String[]> getParameterMap()的方法
				// 注意:an immutable java.util.Map
				// Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
	
				if (req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>) map1.clone();
					session.setAttribute("map", map2);
					map = (HashMap<String, String[]>) req.getParameterMap();
				}

				/***************************
				 * 2.開始複合查詢
				 ***************************************/
				MemberService memberSvc = new MemberService();
				List<MemberVO> list = memberSvc.getAll(map);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("listMembers_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/member/listMembers_ByCompositeQuery.jsp"); // 成功轉交listMembers_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}
		if ("listOrdereds_ByMemno_A".equals(action) || "listEmps_ByDeptno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));
				session.setAttribute("memno", memno);
				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memberSvc = new MemberService();
				Set<OrderedVO> set = memberSvc.getOrderedsByMemno(memno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listOrdereds_ByMemno", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listOrdereds_ByMemno_A".equals(action))
					url = "/front-end/listOrdereds_ByMemno.jsp";        // 成功轉交 dept/listEmps_ByDeptno.jsp
				else if ("listEmps_ByDeptno_B".equals(action))
					url = "/dept/listAllDept.jsp";              // 成功轉交 dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("insertByJson".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
			try {
				String requestURL = req.getParameter("requestURL");
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memshit = new Integer(req.getParameter("memshit").trim());
				Integer memreb = new Integer(req.getParameter("memreb").trim());
				Integer memscore = new Integer(req.getParameter("memscore").trim());
				Integer memblock = new Integer(req.getParameter("memblock").trim());
				Integer memast = new Integer(req.getParameter("memast").trim());
				Integer memsteal = new Integer(req.getParameter("memsteal").trim());
				
				String [] value = req.getParameterValues("skill");
				
				 for(int i=0;i<value.length;i++){
					 
					 if(value[i].equals("reb")){
						 memreb+=1;
					 }else if(value[i].equals("score")){
						 memscore+=1;
					 }else if(value[i].equals("assit")){
						 memast+=1;
					 }else if(value[i].equals("block")){
						 memblock+=1;
					 }else if(value[i].equals("steal")){
						 memsteal+=1;
					 }else{
						 memshit+=1;
					 }
					 
					}
				 MemberVO memberVO = new MemberVO();
				 memberVO.setMemshit(memshit);
				 
				 memberVO.setMemreb(memreb);
				 
				 memberVO.setMemscore(memscore);
				 
				 memberVO.setMemblock(memblock);
				 
				 memberVO.setMemast(memast);
				 
				 memberVO.setMemsteal(memsteal);
				 memberVO.setMemno(memno);
				/***************************
				 * 2.開始新增資料
				 ***************************************/
				
				 MemberService memSvc = new MemberService();
				 memSvc.updateSkill(memberVO);
				
		 
				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
//				 HttpSession session = req.getSession();
//				 session.setAttribute("memberedVO", memberVO);
				String url ="/front-end/memberPage2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Memberskill.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insertByJson2".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
			System.out.println("2222222222");
			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
//				req.setCharacterEncoding("UTF-8");
//				Gson gson = new Gson();
//				BufferedReader br = req.getReader();
//				StringBuilder jsonIn = new StringBuilder();
//				String line = null;
//				while ((line = br.readLine()) != null) {
//					jsonIn.append(line);
//				}
//				System.out.println(jsonIn);
//				JsonObject jsonObject = gson.fromJson(jsonIn.toString(),JsonObject.class);
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memreb = new Integer(req.getParameter("memreb").trim());
				Integer memscore = new Integer(req.getParameter("memscore").trim());
				Integer memblock = new Integer(req.getParameter("memblock").trim());
				Integer memast = new Integer(req.getParameter("memast").trim());
				Integer memsteal = new Integer(req.getParameter("memsteal").trim());
				System.out.println(memno);
				System.out.println(memreb);
				System.out.println(memscore);
				System.out.println(memblock);
				
//				String action2 = jsonObject.get("action").getAsString();
//				System.out.println("action2: " + action2);
				
	
				/***************************
				 * 2.開始新增資料
				 ***************************************/
				
				 MemberService memSvc = new MemberService();
				 MemberVO memberVO=memSvc.getOneMember(memno);
				 memreb+=memberVO.getMemreb();
				 memscore+=memberVO.getMemscore();
				 memblock+=memberVO.getMemblock();
				 memast+=memberVO.getMemast();
				 memsteal+=memberVO.getMemsteal();
				 
				 memberVO.setMemreb(memreb);
				 memberVO.setMemscore(memscore);
				 memberVO.setMemblock(memblock);
				 memberVO.setMemast(memast);
				 memberVO.setMemsteal(memsteal);
				 memSvc.updateSkill(memberVO);
		 
				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
//				 HttpSession session = req.getSession();
//				 session.setAttribute("memberedVO", memberVO);
			
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Memberskill.jsp");
//				failureView.forward(req, res);
			}
		}
		
		
		if ("updateInfo".equals(req.getParameter("action"))) { // 來自addEmp.jsp的請求
			try {
				String requestURL = req.getParameter("requestURL");
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				
				Integer memno = new Integer(req.getParameter("memno").trim());
				String memname = req.getParameter("memname").trim();
				String memadd = req.getParameter("memadd").trim();
				String mempassword = req.getParameter("mempassword").trim();
				String memvarname = req.getParameter("memvarname").trim();
				String memphone = req.getParameter("memphone").trim();
				String mememail = req.getParameter("mememail").trim();
				String memsex = req.getParameter("memcheck").trim();
				String memcheck = req.getParameter("memcheck").trim();
				Integer memballage = new Integer(req.getParameter("memballage").trim());
				
				Part memimg0 = req.getPart("memimg");
				
				if (memimg0 == null || memimg0.getSize() == 0) {
					errorMsgs.add("請選擇照片");
				}
				InputStream in0 = memimg0.getInputStream();
				byte[] memimg = new byte[in0.available()];
				in0.read(memimg);
				
			
				java.sql.Date memage = null;
				if (mempassword == null || mempassword.length() == 0) {
					errorMsgs.add("請輸入密碼!");
				} else if (mempassword != null && !mempassword.matches("[a-zA-Z0-9]{5,10}")) {
					errorMsgs.add("密碼格式錯誤，最少要輸入5到10個數字或英文字母!");
				}
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
			
				 MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setMemname(memname);
					memberVO.setMemadd(memadd);
					memberVO.setMemage(memage);
					memberVO.setMempassword(mempassword);
					memberVO.setMemvarname(memvarname);
					memberVO.setMemphone(memphone);
					memberVO.setMememail(mememail);
					memberVO.setMemsex(memsex);
					memberVO.setMemcheck(memcheck);
					memberVO.setMemballage(memballage);
					memberVO.setMemimg(memimg);
					in0.close();
					if(memberVO != null){
						errorMsgs.add("已修改個人資料!");
					}
					
				/***************************
				 * 2.開始新增資料
				 ***************************************/
				
				 MemberService memSvc = new MemberService();
				 memSvc.updateInfo(memberVO);
				
				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				 HttpSession session = req.getSession();
				 session.setAttribute("memberVO", memberVO);
				String url ="/front-end/memberPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memberPage.jsp");
				failureView.forward(req, res);
			}
		}
		if ("selectAll".equals(action)) {

			String name = "%" + req.getParameter("name").trim() + "%";
			
		    MemberService memberService = new MemberService();
		    List<MemberVO> list = memberService.getAllByName(name);
		    for (MemberVO memberVO : list)
		    {
		    }
		    HttpSession session = req.getSession();
			session.setAttribute("list", list);
			/*********************************/
			
			/*********************************/
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/selectPage.jsp");
			successView.forward(req, res);
			return;
		}
		
	}// doPost
}// MemberServlet
