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

		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				String str = req.getParameter("msgno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�d���s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer msgno = null;
				try {
					msgno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s�������T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);
				if (personalMsgVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("personalMsgVO", personalMsgVO); // ��Ʈw���X��personalmsgVO����,�s�Jreq
				String url = "/personalMsg/listOnePersonalMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOnePersonalMsg.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllPersonalMsg.jsp 
		
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|:
																// �i�ର�i/personalmsg/listAllPersonalMsg.jsp�j
																
			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer msgno = new Integer(req.getParameter("msgno"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("personalMsgVO", personalMsgVO); // ��Ʈw���X��personalMsgVO����,�s�Jreq
				String url = "/personalMsg/update_personalMsg_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_personalMsg_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_personalMsg_input.jsp���ШD
			
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|:
																// �i�ର�i/personalMsg/listAllPersonalMsg.jsp�j
																
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer msgno = new Integer(req.getParameter("msgno").trim());								
				Integer memno = null;
				try{
					
					memno = new Integer(req.getParameter("memno").trim());
					}catch(NumberFormatException e){
						memno=0;
						errorMsgs.add("�п�J�|���s��");
					
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
					req.setAttribute("personalMsgVO", personalMsgVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/update_personalMsg_input.jsp");
					failureView.forward(req, res);
					
					return; // �{�����_
				}

				/***************************
				 * 2.�}�l�ק���
				 *****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.updatePersonalMsg(msgno,memno,memedno,msg,msgdate);
				
				/***************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				// �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/update_personalMsg_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 *************************/
				Integer memno = null;
				try{
					memno = new Integer(req.getParameter("memno").trim());
					}catch(NumberFormatException e){
						memno=0;
						errorMsgs.add("�п�J�|���s��");
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
					req.setAttribute("personalMsgVO", personalMsgVO); // �t����J�榡���~��personalMsgVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.�}�l�s�W���
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				String url = "/personalMsg/listAllPersonalMsg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllPersonalMsg.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp ��
										// /dept/listEmps_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|:
																// �i�ର�i/personalMsg/listAllPersonalMsg.jsp�j
																
			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ***************************************/
				Integer msgno = new Integer(req.getParameter("msgno"));

				/***************************
				 * 2.�}�l�R�����
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				PersonalMsgVO personalMsgVO = personalMsgSvc.getOnePersonalMsg(msgno);
				personalMsgSvc.deletePersonalMsg(msgno);

				/***************************
				 * 3.�R������,�ǳ����(Send the Success view)
				 ***********/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		//�Ӧ�select_jsp�ШD
		if("listMsgs_ByMemedno".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer memedno = new Integer(req.getParameter("memedno"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				List<PersonalMsgVO> set = personalMsgSvc.getMsgsByMemedno(memedno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listMsgs_ByMemedno", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listMsgs_ByMemno".equals(action))
					url = "/personalMsg/listMsgs_ByMemno.jsp";        // ���\���
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("insertByMsg".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
					req.setAttribute("personalMsgVO", personalMsgVO); // �t����J�榡���~��personalMsgVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.�}�l�s�W���
				 ***************************************/
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				String url = "/AA103G5UI/memberpage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllPersonalMsg.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insertByJson".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
				 * 2.�}�l�s�W���
				 ***************************************/
				
				PersonalMsgService personalMsgSvc = new PersonalMsgService();
				personalMsgVO = personalMsgSvc.addPersonalMsg(memno, memedno, msg, msgdate);

				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
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
				 
				
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/memberPage.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}
