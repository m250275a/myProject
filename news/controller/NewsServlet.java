package com.news.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.news.model.*;

import javax.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold=1024*1024,maxFileSize=5*1024*1024,maxRequestSize=5*5*1024*1024)
public class NewsServlet  extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("multipart/form-data; charset=UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("newsno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�s�D�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer newsno = null;
				try {
					newsno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s�D�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
				if (newsVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO); // ��Ʈw���X��newsVO����,�s�Jreq
				String url = "/front-end/listOneNews2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneNews.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllNews.jsp ��  /dept/listNewss_ByDeptno.jsp ���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/news/listAllNews.jsp�j ��  �i/dept/listNewss_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer newsno = new Integer(req.getParameter("newsno"));
				
				/***************************2.�}�l�d�߸��****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("newsVO", newsVO); // ��Ʈw���X��newsVO����,�s�Jreq
				String url = "/news/update_news_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_news_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_news_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/news/listAllNews.jsp�j ��  �i/dept/listNewss_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer newsno = new Integer(req.getParameter("newsno").trim());
				java.sql.Date newsdate = null;
				try {
					newsdate = java.sql.Date.valueOf(req.getParameter("newsdate").trim());
				} catch (IllegalArgumentException e) {
					newsdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				String newscon = req.getParameter("newscon").trim();
				String newsfullcon = req.getParameter("newsfullcon").trim();
				
				Part newsimg0 = req.getPart("newsimg");
				if (newsimg0 == null || newsimg0.getSize() == 0) {
					errorMsgs2.add("�п�ܷӤ�");
				}
				InputStream in0 = newsimg0.getInputStream();
				byte[] newsimg = new byte[in0.available()];
				in0.read(newsimg);
				in0.close();

				
				NewsVO newsVO = new NewsVO();
				newsVO.setNewsno(newsno);
				newsVO.setNewsdate(newsdate);
				newsVO.setNewscon(newscon);
				newsVO.setNewsfullcon(newsfullcon);
				newsVO.setNewsimg(newsimg);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // �t����J�榡���~��newsVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/update_news_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, newsdate, newscon, newsfullcon, newsimg);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/				

				if(requestURL.equals("/news/listAllNews.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllNews",newsSvc.getAll()); // ��Ʈw���X��list����,�s�Jrequest

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/update_news_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addNews.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/

				String newscon = req.getParameter("newscon").trim();
				String newsfullcon = req.getParameter("newsfullcon").trim();
				
				Part newsimg0 = req.getPart("newsimg");
				if (newsimg0 == null || newsimg0.getSize() == 0) {
					errorMsgs2.add("�п�ܷӤ�");
				}
				InputStream in0 = newsimg0.getInputStream();
				byte[] newsimg = new byte[in0.available()];
				in0.read(newsimg);
				in0.close();
				
				NewsVO newsVO = new NewsVO();
				newsVO.setNewscon(newscon);
				newsVO.setNewsfullcon(newsfullcon);
				newsVO.setNewsimg(newsimg);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // �t����J�榡���~��newsVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/addNews.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(newscon, newsfullcon, newsimg);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllNews.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/addNews.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // �Ӧ�listAllNews.jsp ��  /dept/listNewss_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/news/listAllNews.jsp�j ��  �i/dept/listNewss_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j

			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer newsno = new Integer(req.getParameter("newsno"));
				
				/***************************2.�}�l�R�����***************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
				newsSvc.deleteNews(newsno);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
//				NewsService deptSvc = new DeptService();
				if(requestURL.equals("/news/listAllNews.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllNews",newsSvc.getAll()); // ��Ʈw���X��list����,�s�Jrequest

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		// ���news�s�W
				if ("insertNews".equals(action)) { // �Ӧ�addNews2.jsp���ШD

					List<String> errorMsgs = new LinkedList<String>();

					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/***********************
						 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
						 *************************/

						String newscon = req.getParameter("newscon").trim();
						String newsfullcon = req.getParameter("newsfullcon").trim();

						Part newsimg0 = req.getPart("newsimg");
						if (newsimg0 == null || newsimg0.getSize() == 0) {
							errorMsgs.add("�п�ܷӤ�");
						}
						InputStream in0 = newsimg0.getInputStream();
						byte[] newsimg = new byte[in0.available()];
						in0.read(newsimg);
						in0.close();

						NewsVO newsVO = new NewsVO();
						newsVO.setNewscon(newscon);
						newsVO.setNewsfullcon(newsfullcon);
						newsVO.setNewsimg(newsimg);

						if (newsVO == null) {
							errorMsgs.add("����J��T");
						}

						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("newsVO", newsVO); // �t����J�榡���~��newsVO����,�]�s�Jreq
							RequestDispatcher failureView = req.getRequestDispatcher("/back-end/addNews2.jsp");
							failureView.forward(req, res);
							return;
						}

						/***************************
						 * 2.�}�l�s�W���
						 ***************************************/
						NewsService newsSvc = new NewsService();
						newsVO = newsSvc.addNews(newscon, newsfullcon, newsimg);

						if (newsVO != null) {
							errorMsgs.add("�w�s�W�s�T!");
						}

						if (!errorMsgs.isEmpty()) {
							String url = "/back-end/addNews2.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����/back-end/addNews2.jsp.jsp
							successView.forward(req, res);
							return;
						}

						/*************************** ��L�i�઺���~�B�z **********************************/
					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/addNews2.jsp");
						failureView.forward(req, res);
					}
				}
		
	}
}
