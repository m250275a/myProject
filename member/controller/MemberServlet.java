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
		//�q�ӤH��~�𪺰l�ܰe��
		int memnoID = Integer.valueOf(req.getParameter("memno"));//7016
		int memedno = Integer.valueOf(req.getParameter("memedno"));//7015
		
		//����Ʈwmemedno���S����memnoID
		BlackListService blacklistSvc =new BlackListService();
		List<BlackListVO> list = blacklistSvc.getByMemno(new Integer(memedno));//��7015�h�d�L���¦W��
		//�w�]�S���Q�¡A�����ܧ���URL����
		String url="/front-end/memberPage2.jsp?";
		for(int i=0;i<list.size();i++){
			
			 if(list.get(i).getMemedno()==memnoID){
				 url = "/front-end/blackListPage.jsp?";
					break;
			 }
		}
		//��memberedVO��session�A�H�KmemberPage2.jsp�i�H����memberedVO
		MemberService memberSvc = new MemberService();
		MemberVO memberedVO = memberSvc.getOneMember(memedno);
		HttpSession session = req.getSession();
		session.setAttribute("memberedVO", memberedVO);
		//�̫�P�_memnoID�Amemedno�O���O�P�@�ӤH
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
		//���U
		if ("insert".equals(action)) { // �Ӧ�addMember.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/************************ 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
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
					errorMsgs.add("�п�ܷӤ�");
				}
				InputStream in0 = memimg0.getInputStream();
				byte[] memimg = new byte[in0.available()];
				in0.read(memimg);
				in0.close();
				if(!mememail.matches("[a-zA-Z0-9]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]?+.+[a-zA-Z0-9]")){
					errorMsgs.add("email�榡���~!");
				}
						
				if (mempassword.equals("")) {
					errorMsgs.add("�п�J�K�X!");
				} else if (!mempassword.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,10}$")|| !mempassword.equals(mempassword2)) {
					errorMsgs.add("�K�X�T�{���~�A�̤֭n��J5��10�ӼƦr�έ^��r��!");
				}
				MemberService memSvc = new MemberService();
				if (memSvc.GET_MAIL_STMT(mememail) != null) {
					errorMsgs.add("EMAIL�w���U!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
					failureView.forward(req, res);
					return;
				}
				java.sql.Date memage = null;
		
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}

				MemberVO memberVO = toVO(memno, memname, memadd, memage, mempassword, memvarname, memphone, mememail,
						memsex, memcheck, memshit, memwow, memballage, memreb, memscore, memblock, memast, memsteal,memimg);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // �t����J�榡���~��memberVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/**************************** 2.�}�l�s�W��� ***************************************/

				memberVO = memSvc.addMember(memberVO);// �s�W

				/**************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/front-end/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("��Ƥ�����ο��~");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addMember.jsp");
				failureView.forward(req, res);
			}
		}

	//// TODO login//////////////////////////////////////////////////////////////////
		//�n�J
		if ("login".equals(action)) {
			// ���~�T��
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// ���o�ϥΪ̿�J���b���K�X
				String email = req.getParameter("email").trim();
				String password = req.getParameter("password").trim();
			
				if (email.length() == 0 ) {
					errorMsgs.add("�п�J�b��");
				}
				if (password.length() == 0 ) {
					errorMsgs.add("�п�J�K�X");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}
				
				/************************************************************************/
				// �}�l�d��
				MemberService memSvc = new MemberService();
				MemberVO memberVO = memSvc.GET_MAIL_STMT(email);
				// �b�K�P�_���~

				if (memberVO.getMememail().equals(email) && (memberVO.getMempassword()).equals(password)) {
					HttpSession session = req.getSession();
					session.setAttribute("memberVO", memberVO);
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/index.jsp");
					successView.forward(req, res);
					return;
				}else{
					errorMsgs.add("error�K�X");
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*********************** ��� *************************/
				// �d�ߧ����A�}�l���
			}catch (NumberFormatException e) {
				errorMsgs.add("�b�����~");
				errorMsgs.add("�^�ǰT��:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�b���αK�X���~");
				errorMsgs.add("�^�ǰT��:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}

	//// TODO getOne_For_Display///////////////////////////////////////////////////////////////
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

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
					errorMsgs.add("�п�Jmemno");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer memno = null;
				try {
					memno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("memno�榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}

				/***************************
				 * 2.�}�l�d�߸��
				 *****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);
				if (memberVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(login);
					failureView.forward(req, res);
					return;// �{�����_
				}
				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("memberVO", memberVO); // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneMember.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(login);
				failureView.forward(req, res);
			}
		}
	//// TODO getOne_For_Update////////////////////////////////////////////////////////////////
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllMember.jsp ��
													// /dept/listMembers_ByDeptno.jsp
													// ���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|:
																// �i�ର�i/member/listAllMember.jsp�j
															// �i/dept/listMembers_ByDeptno.jsp�j
																// �� �i
																// /dept/listAllDept.jsp�j

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.getOneMember(memno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("memberVO", memberVO); // ��Ʈw���X��memberVO����,�s�Jreq
				String url = "/member/update_member_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_member_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		//
		//// TODO update/////////////////////////////////////////////////////////
		if ("update".equals(action)) { // �Ӧ�update_member_input.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|:
																// �i�ର�i/member/listAllMember.jsp�j
			// ��
			// �i/dept/listMembers_ByDeptno.jsp�j
			// �� �i
			// /dept/listAllDept.jsp�j
			// �� �i
			// /member/listMembers_ByCompositeQuery.jsp�j

			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
					errorMsgs.add("�п�ܷӤ�");
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
					errorMsgs.add("�п�J�K�X!");
				} else if (mempassword != null && !mempassword.matches("[a-zA-Z0-9]{5,10}")) {
					errorMsgs.add("�K�X�榡���~�A�̤֭n��J5��10�ӼƦr�έ^��r��!");
				}
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}

				MemberVO memberVO = toVO(memno, memname, memadd, memage, mempassword, memvarname, memphone, mememail,
						memsex, memcheck, memshit, memwow, memballage, memreb, memscore, memblock, memast, memsteal,memimg);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberVO", memberVO); // �t����J�榡���~��memberVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/***************************
				 * 2.�}�l�ק���
				 *****************************************/
				MemberService memSvc = new MemberService();
				memberVO = memSvc.updateMember(memberVO);
				/***************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listMembers_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listMembers_ByDeptno",deptSvc.getMembersByDeptno(deptno));
				// // ��Ʈw���X��list����,�s�Jrequest

				if (requestURL.equals("/member/listMembers_ByCompositeQuery.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<MemberVO> list = memSvc.getAll(map);
					req.setAttribute("/member/update_member_input.jsp", list); // �ƦX�d��,
																				// ��Ʈw���X��list����,�s�Jrequest
				}

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
				failureView.forward(req, res);
			}
		}
		//
		////TODO getOne_For_Update//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if ("delete".equals(action)) { // �Ӧ�listAllMember.jsp ��
										// /dept/listMembers_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|:
																// �i�ର�i/member/listAllMember.jsp�j
																// ��
																// �i/dept/listMembers_ByDeptno.jsp�j
																// �� �i
																// /dept/listAllDept.jsp�j
																// �� �i
																// /member/listMembers_ByCompositeQuery.jsp�j

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ***************************************/
				Integer memno = new Integer(req.getParameter("memno"));

				/***************************
				 * 2.�}�l�R�����
				 ***************************************/
				MemberService memberSvc = new MemberService();
				memberSvc.deleteMember(memno);

				/***************************
				 * 3.�R������,�ǳ����(Send the Success view)
				 ***********/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listMembers_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listMembers_ByDeptno",deptSvc.getMembersByDeptno(memberVO.getDeptno()));
				// // ��Ʈw���X��list����,�s�Jrequest

				if (requestURL.equals("/member/listMembers_ByCompositeQuery.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<MemberVO> list = memberSvc.getAll(map);
					req.setAttribute("listMembers_ByCompositeQuery", list); // �ƦX�d��,
																			// ��Ʈw���X��list����,�s�Jrequest
				}

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

		////TODO getOne_For_Update////////////////////////////////////////////////////////////////
		if ("listMembers_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.�N��J����ରMap
				 **********************************/
				// �ĥ�Map<String,String[]> getParameterMap()����k
				// �`�N:an immutable java.util.Map
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
				 * 2.�}�l�ƦX�d��
				 ***************************************/
				MemberService memberSvc = new MemberService();
				List<MemberVO> list = memberSvc.getAll(map);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("listMembers_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/member/listMembers_ByCompositeQuery.jsp"); // ���\���listMembers_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
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
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer memno = new Integer(req.getParameter("memno"));
				session.setAttribute("memno", memno);
				/*************************** 2.�}�l�d�߸�� ****************************************/
				MemberService memberSvc = new MemberService();
				Set<OrderedVO> set = memberSvc.getOrderedsByMemno(memno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listOrdereds_ByMemno", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listOrdereds_ByMemno_A".equals(action))
					url = "/front-end/listOrdereds_ByMemno.jsp";        // ���\��� dept/listEmps_ByDeptno.jsp
				else if ("listEmps_ByDeptno_B".equals(action))
					url = "/dept/listAllDept.jsp";              // ���\��� dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		if ("insertByJson".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
			try {
				String requestURL = req.getParameter("requestURL");
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
				 * 2.�}�l�s�W���
				 ***************************************/
				
				 MemberService memSvc = new MemberService();
				 memSvc.updateSkill(memberVO);
				
		 
				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
//				 HttpSession session = req.getSession();
//				 session.setAttribute("memberedVO", memberVO);
				String url ="/front-end/memberPage2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Memberskill.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insertByJson2".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
			System.out.println("2222222222");
			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
				 * 2.�}�l�s�W���
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
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
//				 HttpSession session = req.getSession();
//				 session.setAttribute("memberedVO", memberVO);
			
				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Memberskill.jsp");
//				failureView.forward(req, res);
			}
		}
		
		
		if ("updateInfo".equals(req.getParameter("action"))) { // �Ӧ�addEmp.jsp���ШD
			try {
				String requestURL = req.getParameter("requestURL");
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
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
					errorMsgs.add("�п�ܷӤ�");
				}
				InputStream in0 = memimg0.getInputStream();
				byte[] memimg = new byte[in0.available()];
				in0.read(memimg);
				
			
				java.sql.Date memage = null;
				if (mempassword == null || mempassword.length() == 0) {
					errorMsgs.add("�п�J�K�X!");
				} else if (mempassword != null && !mempassword.matches("[a-zA-Z0-9]{5,10}")) {
					errorMsgs.add("�K�X�榡���~�A�̤֭n��J5��10�ӼƦr�έ^��r��!");
				}
				try {
					memage = java.sql.Date.valueOf(req.getParameter("memage").trim());
				} catch (IllegalArgumentException e) {
					memage = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
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
						errorMsgs.add("�w�ק�ӤH���!");
					}
					
				/***************************
				 * 2.�}�l�s�W���
				 ***************************************/
				
				 MemberService memSvc = new MemberService();
				 memSvc.updateInfo(memberVO);
				
				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				 HttpSession session = req.getSession();
				 session.setAttribute("memberVO", memberVO);
				String url ="/front-end/memberPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllPersonalMsg.jsp
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z **********************************/
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
