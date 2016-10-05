package com.blacklist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blacklist.model.BlackListService;
import com.blacklist.model.BlackListVO;



@WebServlet("/BlacklistServlet")
public class BlackListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
	
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("blackno");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer blackno = null;
				try {
					blackno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("���u�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				BlackListService blackSvc = new BlackListService();
				BlackListVO blacklistVO = blackSvc.getOneBlack(blackno);
				if (blacklistVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/blacklist/listOneBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer blackno = new Integer(req.getParameter("blackno"));
				
				/***************************2.�}�l�d�߸��****************************************/
				BlackListService blackSvc = new BlackListService();
				BlackListVO blacklistVO = blackSvc.getOneBlack(blackno);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("blacklistVO", blacklistVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/blacklist/update_BlackList_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/listAllBlackList.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer blackno = new Integer(req.getParameter("blackno").trim());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memedno = new Integer(req.getParameter("memedno").trim());				
				

				

				BlackListVO blacklistVO = new BlackListVO();
				blacklistVO.setBlackno(blackno);
				blacklistVO.setMemno(memno);
				blacklistVO.setMemedno(memedno);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blacklistVO", blacklistVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/update_BlackList_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				BlackListService blackSvc = new BlackListService();
				blacklistVO = blackSvc.updateBlack(blackno,memno,memedno);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/blacklist/listOneBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/update_BlackList_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer memedno = new Integer(req.getParameter("memedno").trim());				
				
				BlackListVO blacklistVO = new BlackListVO();
				blacklistVO.setMemno(memno);
				blacklistVO.setMemedno(memedno);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blackListVO", blacklistVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				BlackListService blackSvc = new BlackListService();
				blacklistVO = blackSvc.addBlack(memno,memedno);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/blacklist/listAllBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer blackno = new Integer(req.getParameter("blackno"));
				
				/***************************2.�}�l�R�����***************************************/
				BlackListService blackSvc = new BlackListService();
				blackSvc.deleteBlack(blackno);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/blacklist/listAllBlackList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("getForMemno".equals(action)) { // �Ӧ�select_page.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("memno");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("���u�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				BlackListService blackSvc = new BlackListService();
				List<BlackListVO> blacklistVO = blackSvc.getByMemno(memno);
				if (blacklistVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/blacklist/listAllByMemno.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("blacklistVO", blacklistVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/blacklist/listAllByMemno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/blacklist/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
if ("insertByJson".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 *************************/
				String str = req.getParameter("memno");
				String str1 = req.getParameter("memedno");
				Integer memno = new Integer(str);
				Integer memedno = new Integer(str1);
				
				BlackListVO blackListVO = new BlackListVO();
				blackListVO.setMemno(memno);
				blackListVO.setMemedno(memedno);
				/***************************
				 * 2.�}�l�s�W���
				 ***************************************/
				BlackListService blacklistSvc = new BlackListService();
				blacklistSvc.addBlack(memno, memedno);
				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				PrintWriter out = res.getWriter();
				out.print("�w���\�s�W:"+str1);
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				PrintWriter out = res.getWriter();
				out.print("�s�W����:�w�s�W�L");
			}
		}
		
if ("delete2".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
	try {
		/***************************1.�����ШD�Ѽ�***************************************/
		Integer blackno = new Integer(req.getParameter("blackno"));
		
		/***************************2.�}�l�R�����***************************************/
		BlackListService blacklistSvc = new BlackListService();
		blacklistSvc.deleteBlack(blackno);
		
		/***************************3.�R������,�ǳ����(Send the Success view)***********/								
		String url = "/front-end/memberPage.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
		successView.forward(req, res);
		/*************************** ��L�i�઺���~�B�z **********************************/
	} catch (Exception e) {
//		RequestDispatcher failureView = req.getRequestDispatcher("/personalMsg/addPersonalMsg.jsp");
//		failureView.forward(req, res);
	}
}		

	}
}


