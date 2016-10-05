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
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				CourtService courtSvc = new CourtService();
				Set<TeamVO> set = courtSvc.getTeamsByCourtno(courtno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listTeams_ByCourtno", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listTeams_ByCourtno_A".equals(action))
					url = "/court/listTeams_ByCourtno.jsp";        // ���\��� court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByCourtno_B".equals(action))
					url = "/front-end/listAllCourt3.jsp";              // ���\��� court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD
			System.out.println(action);
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("courtno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�y���s��");
				}
				System.out.println("CourtServlet courtno: " + str);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer courtno = null;
				try {
					courtno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�y���s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page1.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				CourtService courtSvc = new CourtService();
				CourtVO courtVO = courtSvc.getOneCourt(courtno);
				GameService gameSvc = new GameService();
				List<GameVO> courtgamelist = gameSvc.getGamesByCourtno(courtno);
				
				if (courtVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/court/select_page2.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				HttpSession session= req.getSession();
				session.setAttribute("courtgamelist", courtgamelist);
				session.setAttribute("courtVO", courtVO); // ��Ʈw���X��courtVO����,�s�Jsession
				String url = "/front-end/chatRoom.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneCourt.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o���:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/court/select_page3.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllCourt.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));
				
				/***************************2.�}�l�d�߸��****************************************/
				CourtService courtSvc = new CourtService();
				CourtVO courtVO = courtSvc.getOneCourt(courtno);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("courtVO", courtVO);         // ��Ʈw���X��courtVO����,�s�Jreq
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_court_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/listAllCourt3.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_court_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
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
//					errorMsgs2.add("�п�ܷӤ�");
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
					errorMsgs.add("�R�W���\!");
				}
				
				/***************************2.�}�l�ק���*****************************************/
				CourtService courtSvc = new CourtService();
				courtVO = courtSvc.updateCourt(courtno, courtname, whoname);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("courtVO", courtVO); // ��Ʈwupdate���\��,���T����courtVO����,�s�Jreq
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneCourt.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/update_court_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addCourt.jsp���ШD  
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
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
							
				//�Ϥ�
				//byte[] courtimg = null;
				Part courtimg0 = req.getPart("courtimg");
				if (courtimg0 == null || courtimg0.getSize() == 0) {
					errorMsgs2.add("�п�ܷӤ�");
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
					errorMsgs.add("�y���s�W�n�F!");
				}
				
				/***************************2.�}�l�s�W���***************************************/
				CourtService courtSvc = new CourtService();
				courtVO = courtSvc.addCourt(courtlat, courtlng, courtloc, courtname,courtnum, basketnum, courttype,
						opentime, courtlight, courthost, courtphone, courtdesc, courtmoney, courtimg, whoname);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				req.setAttribute("courtVO", courtVO);
				String url = "/front-end/listAllCourt3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllCourt.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/court/addCourt.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllCourt.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));
				
				/***************************2.�}�l�R�����***************************************/
				CourtService courtSvc = new CourtService();
				courtSvc.deleteCourt(courtno);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/court/listAllCourt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/court/listAllCourt.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listCourts_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
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
				
				/***************************2.�}�l�ƦX�d��***************************************/
				CourtService courtSvc = new CourtService();
				List<CourtVO> list  = courtSvc.getAll(map);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
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
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
