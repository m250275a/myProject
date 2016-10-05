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
		// //�n�J//////////////////
		if ("login".equals(action)) {
			// ���~�T��
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// ���o�ϥΪ̿�J���b���K�X
				Integer adminno = new Integer(req.getParameter("adminno").trim());
				String adminpsw = req.getParameter("password").trim();

				if (adminpsw.equals("")) {
					errorMsgs.add("�п�J�K�X");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}

				/************************************************************************/
				// �}�l�d��
				AdminService memSvc = new AdminService();
				AdminVO adminVO = memSvc.getOneAdmin(adminno);
				// �b�K�P�_���~

				if (adminVO.getAdminno().equals(adminno) && (adminVO.getAdminpsw()).equals(adminpsw)) {

					
					session.setAttribute("adminVO", adminVO);

					
					String url = "/back-end/backhead.jsp";
					

					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					return;
					
				} else {
					errorMsgs.add("error�K�X");
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*********************** ��� *************************/
				// �d�ߧ����A�}�l���
			} catch (NumberFormatException e) {
				errorMsgs.add("�b�����~");

				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�b���αK�X���~");

				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}
		// TODO//
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp�ШD

			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope, in case we need to
			// send the errorpage view
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// �����ШD�Ѽ�--��J�榡���~�B�z

				String str = req.getParameter("adminno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�s��");
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
					errorMsgs.add("���u�s���榡�����T");
				}

				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return; // program stop
				}

				// �}�l�d��
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(adminno);
				if (adminVO == null) {
					errorMsgs.add("�d�L���");
				}
				// send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return; // program stop
				}

				// �d�ߧ���,�ǳ����
				req.setAttribute("adminVO", adminVO);// ��Ʈw���Xadminvo

				RequestDispatcher successView = req.getRequestDispatcher(listOneAdmin);
				// ���\���listOneAdmin.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}

		// TODO//// �Ӧ�listAllAdmin.jsp�ШD
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope in case we need to
			// send the errorpage view
			req.setAttribute("errormsgs", errorMsgs);

			// �����ШD�Ѽ�
			try {
				Integer adminno = new Integer(req.getParameter("adminno"));

				// �}�l�d��
				AdminService adminSvc = new AdminService();
				AdminVO adminVO = adminSvc.getOneAdmin(adminno);

				// �d�ߧ���,�ǳ����
				req.setAttribute("adminVO1", adminVO);

				// ���\���update_admin_input.jsp
				RequestDispatcher successView = req.getRequestDispatcher(update);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���");
				RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
				failureView.forward(req, res);
			}
		}

		// TODO// �Ӧ�update_admin_input.jsp�ШD
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// store this set in the request scope in case we need to
			// send the errorpage view
			req.setAttribute("errorMsgs", errorMsgs);

			// �����ШD�Ѽ�--���~�榡�B
			 try {
			Integer adminno = new Integer(req.getParameter("adminno"));

			String adminname = req.getParameter("adminname").trim();

			if (adminname == null || (adminname.trim()).length() == 0) {
				errorMsgs.add("�п�J���u�m�W");
			}

			String adminvarname = req.getParameter("adminvarname").trim();
			String adminid = req.getParameter("adminid").trim();
			String adminphone = req.getParameter("adminphone");

			if (adminphone == null || (adminphone.trim()).length() == 0) {
				errorMsgs.add("�п�J���u�q��");
			}

			String adminaddr = req.getParameter("adminaddr");

			if (adminaddr == null || (adminaddr.trim()).length() == 0) {
				errorMsgs.add("�п�J���u�a�}");
			}

			String adminpsw = req.getParameter("adminpsw");
			String adminpsw2 = req.getParameter("adminpsw2");

			if (adminpsw.equals("")) {
				errorMsgs.add("�п�J�K�X");
			}
			if (!adminpsw.equals(adminpsw2)) {
				errorMsgs.add("�K�X���P");
			}

			String adminemail = req.getParameter("adminemail");

			if (adminemail == null || (adminemail.trim()).length() == 0) {
				errorMsgs.add("�п�J���uE-mail");
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
				return; // �{�����_
			}

			// �}�l�ק���
			AdminService adminSvc = new AdminService();
			adminVO = adminSvc.updateAdmin(adminno, adminname, adminvarname, adminid, adminphone, adminaddr, adminpsw,
					adminemail, adminlevel);

			// �ק粒��,�ǳ����
//			req.setAttribute("adminVO", adminVO);
			RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin);
			successView.forward(req, res);

			// ��L���~�i��
			 } catch (Exception e) {
			 errorMsgs.add("�ק異��");
			 RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
			 failureView.forward(req, res);
			 }
		}
		// TODO// //// ���U
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// �����ШD�Ѽ�-���~�榡�B�z
			try {

				String adminname = req.getParameter("adminname").trim();

				if (adminname == null || (adminname.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�m�W");
				}

				String adminvarname = req.getParameter("adminvarname").trim();
				
				String adminid = req.getParameter("adminid").trim();
				if (adminid == null || (adminid.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�����Ҧr��");
				}
				String adminphone = req.getParameter("adminphone");

				if (adminphone == null || (adminphone.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�q��");
				}

				String adminaddr = req.getParameter("adminaddr");

				if (adminaddr == null || (adminaddr.trim()).length() == 0) {
					errorMsgs.add("�п�J���u�a�}");
				}

				char ch[] = new char[6];
				String adminpsw = "";
				for (int i = 0; i < 6; i++) {
					ch[i] = (char) (Math.random() * 26 + 97);
					adminpsw += ch[i];
				}

				String adminemail = req.getParameter("adminemail");

				if (adminemail == null || (adminemail.trim()).length() == 0) {
					errorMsgs.add("�п�J���uE-mail");
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
					return; // �{�����_
				}

				// �}�l�s�W���

				AdminService adminSvc = new AdminService();
				adminVO = adminSvc.addAdmin(adminname, adminvarname, adminid, adminphone, adminaddr, adminpsw,
						adminemail, adminlevel);


				/****************************
				 * ����A�o�e�q�l�l��
				 *********************************************/
				
				 MailService mailscv = new MailService();				
				 String subject = "��ݺ޲z�̱b�����U���\";
				 String messageText= "Hello! " + adminname + "�A���b���w���U���\\n�K�X:"
				 + adminpsw + "\n (�w�g�ҥ�)";
				 String to = "aa103g05@gmail.com";
				 mailscv.sendMail(to, subject, messageText);
//				 mailscv.sendMail(adminemail, subject, messageText);

				 // �s�W����,�ǳ����
//				 req.setAttribute("whichPage", new Integer(2));
				 RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin);
				 successView.forward(req, res);
				// ��L���~�i��
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

			// �����ШD�Ѽ�
			try {
				Integer adminno = new Integer(req.getParameter("adminno"));

				// �}�l�R�����

				AdminService adminSvc = new AdminService();
				adminSvc.deleteAdmin(adminno);

				// �R������,�ǳ����

				RequestDispatcher success = req.getRequestDispatcher(listAllAdmin);
				success.forward(req, res);

				// ��L���~�榡
			} catch (Exception e) {
				errorMsgs.add("�R����ƿ��~" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(listAllAdmin);
				failureView.forward(req, res);
			}
		}

		// �n�X
		if ("clean".equals(action)) {
			if (!session.isNew()) {
				session.invalidate();
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/Home.jsp");
				successView.forward(req, res); // get a new session
			}

		}

	}

}
