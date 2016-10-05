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

		if ("challteamInsert".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				Integer challmode = new Integer(req.getParameter("challmode").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				ChallteamService challteamSvc = new ChallteamService();
				ChallteamVO challteamVO = challteamSvc.addChallteam(teamno, challmode);
				if (challteamVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("memberVO", challteamVO); // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listAllChallteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
				failureView.forward(req, res);
			}
		}
		if ("challteamDelete".equals(action)) { // �Ӧ�select_page.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				Integer challmode = new Integer(req.getParameter("challmode").trim());
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				ChallteamService challteamSvc = new ChallteamService();
				challteamSvc.delete(teamno,challmode);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				 // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listAllChallteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllChallteam.jsp");
				failureView.forward(req, res);
			}
		}

	}//
}//
