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
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("challmode");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�D�ԼҦ��s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer challmode = null;
				try {
					challmode = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�D�ԼҦ��s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				ChallmodeVO challmodeVO = challmodeSvc.getOneChallmode(challmode);
				if (challmodeVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("challmodeVO", challmodeVO); // ��Ʈw���X��challmodeVO����,�s�Jreq
				String url = "/challmode/listOneChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneChallmode.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllChallmode.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer challmode = new Integer(req.getParameter("challmode"));
				
				/***************************2.�}�l�d�߸��****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				ChallmodeVO challmodeVO = challmodeSvc.getOneChallmode(challmode);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("challmodeVO", challmodeVO);         // ��Ʈw���X��challmodeVO����,�s�Jreq
				String url = "/challmode/update_challmode_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_challmode_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/listAllChallmode.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_challmode_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer challmode = new Integer(req.getParameter("challmode").trim());
				String challcontent = req.getParameter("challcontent").trim();
			

				ChallmodeVO challmodeVO = new ChallmodeVO();
				challmodeVO.setChallmode(challmode);
				challmodeVO.setChallcontent(challcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("challmodeVO", challmodeVO); // �t����J�榡���~��challmodeVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/update_challmode_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeVO = challmodeSvc.updateChallmode(challmode, challcontent);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("challmodeVO", challmodeVO); // ��Ʈwupdate���\��,���T����challmodeVO����,�s�Jreq
				String url = "/challmode/listOneChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneChallmode.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/update_challmode_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addChallmode.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String challcontent = req.getParameter("challcontent").trim();
				
				
				ChallmodeVO challmodeVO = new ChallmodeVO();
				challmodeVO.setChallcontent(challcontent);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("challmodeVO", challmodeVO); // �t����J�榡���~��challmodeVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/challmode/addChallmode.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeVO = challmodeSvc.addChallmode(challcontent);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/challmode/listAllChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllChallmode.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/addChallmode.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllChallmode.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer challmode = new Integer(req.getParameter("challmode"));
				
				/***************************2.�}�l�R�����***************************************/
				ChallmodeService challmodeSvc = new ChallmodeService();
				challmodeSvc.deleteChallmode(challmode);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/challmode/listAllChallmode.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/challmode/listAllChallmode.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
