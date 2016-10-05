package com.memteam.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.memteam.model.MemteamService;
import com.memteam.model.MemteamVO;
import com.team.model.TeamService;
import com.team.model.TeamVO;

public class MemteamServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAllMemteam".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				String str = req.getParameter("memno");

				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�n�ͽs��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�n�ͽs���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				List<MemteamVO> friendVO = friendSvc.getAll();
				if (friendVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("memberVO", friendVO); // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("memteamInsert".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				MemteamVO friendVO = friendSvc.addMemteam(memno, teamno);
				if (friendVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("memberVO", friendVO); // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listAllMemteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
				failureView.forward(req, res);
			}
		}
		if ("memteamDelete".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemteamService friendSvc = new MemteamService();
				friendSvc.delete(memno, teamno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				// ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listAllMemteam.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMemteam.jsp");
				failureView.forward(req, res);
			}
		}
		// �|���ӽХ[�J�y��
		if ("joinTeam".equals(action)) { // �Ӧ�teampage.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				String memcheck = null;
				MemteamService memteams = new MemteamService();
				List<MemteamVO> list = memteams.getTeamBYMemno(memno);
				for (MemteamVO teamlist : list) {

					Integer no = teamlist.getTeamno();

					if (new Integer(req.getParameter("teamno")).equals(no)) {
						errorMsgs.add("�w�[�J�y��");
					}
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				memcheck = req.getParameter("teamno").trim();
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.updateJoinCheck(memno, memcheck);

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				
				if (memberVO != null) {
					errorMsgs.add("�w�ӽЦ��\,�ݼf��");
				}

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/

				if (!errorMsgs.isEmpty()) {

					String url = "/front-end/teampage.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���teampage.jsp
					successView.forward(req, res);
				}

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k�ӽ�:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
				failureView.forward(req, res);
			}
		}
		// �h�X�y��
		if ("DeleteTeam".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());
				String memcheck = req.getParameter("memcheck");

				MemberService memberSvc = new MemberService();
				MemberVO memberVO = new MemberVO();
				memberVO = memberSvc.updateJoinCheck(memno, memcheck);
				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemteamService memteamSvc = new MemteamService();
				memteamSvc.deleteTeam(memno, teamno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				// ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/front-end/teamHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���teampage.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teampage.jsp");
				failureView.forward(req, res);
			}
		}

		// �����|���[�J�y��
		if ("pass".equals(action)) { // �Ӧ�memList.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				// ���o�ϥΪ�(memadno),���ݲy��(teamno),�ӽЪ�(memno),�����ӽ�(memcheck)
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer memno = new Integer(req.getParameter("memno"));
				String memcheck = req.getParameter("memcheck");
				Integer memadno = new Integer(req.getParameter("memadno"));

				// �v���P�_
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = teamSvc.getOneTeam(teamno);
				MemteamVO memteamVO = null;	
				MemteamService memteamSvc = new MemteamService();
				if (memadno.equals(teamVO.getTeamadmin())) {

					memteamVO = new MemteamVO();
					memteamVO.setMemno(memno);
					memteamVO.setTeamno(teamno);
					memteamVO = memteamSvc.joinTeam(memno, teamno);
				} else {
					errorMsgs.add("�D�y���޲z��");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				if(memteamVO != null){
					errorMsgs.add("�w�s�W����!");
				}

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
	
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = new MemberVO();
				memberVO.setMemno(memno);
				memberVO.setMemcheck(memcheck);
				memberVO = memberSvc.updateJoinCheck(memno, memcheck);// update memberVO����
				
				String url = "/front-end/memList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���memList.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
				failureView.forward(req, res);
			}
		}

		// �ڵ��ӽФJ��
		if ("nopass".equals(action)) { // �Ӧ�memList.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				// ���o�ϥΪ�(memadno),���ݲy��(teamno),�ӽЪ�(memno),���A(memcheck)
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer memno = new Integer(req.getParameter("memno"));
				String memcheck = req.getParameter("memcheck");
				Integer memadno = new Integer(req.getParameter("memadno"));

				// �v���P�_
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = teamSvc.getOneTeam(teamno);
				MemberVO memberVO = null;
				MemberService memberSvc = new MemberService();
				if (memadno.equals(teamVO.getTeamadmin())) {

					memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setMemcheck(memcheck);
					memberVO = memberSvc.updateJoinCheck(memno, memcheck);
				} else {
					errorMsgs.add("�D�y���޲z��");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				if(memberVO != null){
					errorMsgs.add("�w�ڵ��ӽ�!");
				}
				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemteamService memteamSvc = new MemteamService();
				memteamSvc.delete(memno, teamno);

				// Send the use back to the form, if there were errors
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				// ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/front-end/memList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���memList.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memList.jsp");
				failureView.forward(req, res);
			}
		}

	}//
}//
