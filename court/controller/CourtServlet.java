package com.court.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.game.model.*;
import com.team.model.TeamVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.court.model.*;


import javax.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold=1024*1024,maxFileSize=5*1024*1024,maxRequestSize=5*5*1024*1024) 

public class CourtServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("multipart/form-data; charset=UTF-8");
		String action = req.getParameter("action");
		
		if ("listTeams_ByCourtno_A".equals(action) || "listTeams_ByCourtno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));

				/*************************** 2.開始查詢資料 ****************************************/
				CourtService courtSvc = new CourtService();
				Set<TeamVO> set = courtSvc.getTeamsByCourtno(courtno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listTeams_ByCourtno", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listTeams_ByCourtno_A".equals(action))
					url = "/court/listTeams_ByCourtno.jsp";        // 成功轉交 court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByCourtno_B".equals(action))
					url = "/front-end/listAllCourt3.jsp";              // 成功轉交 court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
			System.out.println(action);
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("courtno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入球場編號");
				}
				System.out.println("CourtServlet courtno: " + str);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer courtno = null;
				try {
					courtno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("球場編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page1.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				CourtService courtSvc = new CourtService();
				CourtVO courtVO = courtSvc.getOneCourt(courtno);
				GameService gameSvc = new GameService();
				List<GameVO> courtgamelist = gameSvc.getGamesByCourtno(courtno);
				
				if (courtVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page2.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				HttpSession session= req.getSession();
				session.setAttribute("courtgamelist", courtgamelist);
				session.setAttribute("courtVO", courtVO); // 資料庫取出的courtVO物件,存入session
				String url = "/front-end/chatRoom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCourt.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/court/select_page3.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllCourt.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));
				
				/***************************2.開始查詢資料****************************************/
				CourtService courtSvc = new CourtService();
				CourtVO courtVO = courtSvc.getOneCourt(courtno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("courtVO", courtVO);         // 資料庫取出的courtVO物件,存入req
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_court_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/listAllCourt3.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_court_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer courtno = new Integer(req.getParameter("courtno").trim());
//				Double courtlat =new Double(req.getParameter("courtlat").trim());
//				Double courtlng =new Double(req.getParameter("courtlng").trim());
//				String courtloc = req.getParameter("courtloc").trim();
				String courtname = req.getParameter("courtname").trim();
//				Integer courtnum = new Integer(req.getParameter("courtnum").trim());
//				Integer basketnum = new Integer(req.getParameter("basketnum").trim());
//				String courttype = req.getParameter("courttype").trim();
//				String opentime = req.getParameter("opentime").trim();
//				String courtlight = req.getParameter("courtlight").trim();
//				String courthost = req.getParameter("courthost").trim();
//				String courtphone = req.getParameter("courtphone").trim();
//				String courtdesc = req.getParameter("courtdesc").trim();
//				Integer courtmoney = new Integer(req.getParameter("courtmoney").trim());
//				
//				Part courtimg0 = req.getPart("courtimg");
//				if (courtimg0 == null || courtimg0.getSize() == 0) {
//					errorMsgs2.add("請選擇照片");
//				}
//				InputStream in0 = courtimg0.getInputStream();
//				byte[] courtimg = new byte[in0.available()];
//				in0.read(courtimg);
//				in0.close();
				Integer whoname = new Integer(req.getParameter("whoname").trim());

				CourtVO courtVO = new CourtVO();
				courtVO.setCourtno(courtno);
//				courtVO.setCourtlat(courtlat);
//				courtVO.setCourtlng(courtlng);
//				courtVO.setCourtloc(courtloc);
				courtVO.setCourtname(courtname);
//				courtVO.setCourtnum(courtnum);
//				courtVO.setBasketnum(basketnum);
//				courtVO.setCourttype(courttype);
//				courtVO.setOpentime(opentime);
//				courtVO.setCourtlight(courtlight);
//				courtVO.setCourthost(courthost);
//				courtVO.setCourtphone(courtphone);
//				courtVO.setCourtdesc(courtdesc);
//				courtVO.setCourtmoney(courtmoney);
//				courtVO.setCourtimg(courtimg);
				courtVO.setWhoname(whoname);

				// Send the use back to the form, if there were errors
				if(courtVO != null){
					errorMsgs.add("命名成功!");
				}
				
				/***************************2.開始修改資料*****************************************/
				CourtService courtSvc = new CourtService();
				courtVO = courtSvc.updateCourt(courtno, courtname, whoname);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("courtVO", courtVO); // 資料庫update成功後,正確的的courtVO物件,存入req
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCourt.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/update_court_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addCourt.jsp的請求  
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Double courtlat =new Double(req.getParameter("courtlat").trim());
				Double courtlng =new Double(req.getParameter("courtlng").trim());
				String courtloc = req.getParameter("courtloc").trim();
				String courtname = req.getParameter("courtname").trim();
				Integer courtnum = new Integer(req.getParameter("courtnum").trim());
				Integer basketnum = new Integer(req.getParameter("basketnum").trim());
				String courttype = req.getParameter("courttype").trim();
				String opentime = req.getParameter("opentime").trim();
				String courtlight = req.getParameter("courtlight").trim();
				String courthost = req.getParameter("courthost").trim();
				String courtphone = req.getParameter("courtphone").trim();
				String courtdesc = req.getParameter("courtdesc").trim();
				Integer courtmoney = new Integer(req.getParameter("courtmoney").trim());
							
				//圖片
				//byte[] courtimg = null;
				Part courtimg0 = req.getPart("courtimg");
				if (courtimg0 == null || courtimg0.getSize() == 0) {
					errorMsgs2.add("請選擇照片");
				}
				InputStream in0 = courtimg0.getInputStream();
				byte[] courtimg = new byte[in0.available()];
				in0.read(courtimg);
				in0.close();
				Integer whoname = new Integer(req.getParameter("whoname").trim());
				
				CourtVO courtVO = new CourtVO();
				courtVO.setCourtlat(courtlat);
				courtVO.setCourtlng(courtlng);
				courtVO.setCourtloc(courtloc);
				courtVO.setCourtname(courtname);
				courtVO.setCourtnum(courtnum);
				courtVO.setBasketnum(basketnum);
				courtVO.setCourttype(courttype);
				courtVO.setOpentime(opentime);
				courtVO.setCourtlight(courtlight);
				courtVO.setCourthost(courthost);
				courtVO.setCourtphone(courtphone);
				courtVO.setCourtdesc(courtdesc);
				courtVO.setCourtmoney(courtmoney);
				courtVO.setCourtimg(courtimg);
				courtVO.setWhoname(whoname);
				
				if(courtVO != null){
					errorMsgs.add("球場新增好了!");
				}
				
				/***************************2.開始新增資料***************************************/
				CourtService courtSvc = new CourtService();
				courtVO = courtSvc.addCourt(courtlat, courtlng, courtloc, courtname,courtnum, basketnum, courttype,
						opentime, courtlight, courthost, courtphone, courtdesc, courtmoney, courtimg, whoname);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("courtVO", courtVO);
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCourt.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/court/addCourt.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllCourt.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));
				
				/***************************2.開始刪除資料***************************************/
				CourtService courtSvc = new CourtService();
				courtSvc.deleteCourt(courtno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/court/listAllCourt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/court/listAllCourt.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listCourts_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				//Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>)map1.clone();
					session.setAttribute("map",map2);
					map = (HashMap<String, String[]>)req.getParameterMap();
				} 
				
				/***************************2.開始複合查詢***************************************/
				CourtService courtSvc = new CourtService();
				List<CourtVO> list  = courtSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
	          
				try {
					for (CourtVO courtVO : list) {
						obj.put("courtno", courtVO.getCourtno());
						obj.put("courtlat", courtVO.getCourtlat());
						obj.put("courtlng", courtVO.getCourtlng());
						obj.put("courtname", courtVO.getCourtname());
						obj.put("courtloc", courtVO.getCourtloc());
						obj.put("courttype", courtVO.getCourttype());
						obj.put("courtdesc", courtVO.getCourtdesc());
						array.add(obj);
					}
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					out.write(array.toString());
					out.flush();
					out.close();
				} catch (Exception e) {

				}
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
