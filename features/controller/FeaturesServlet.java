package com.features.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.features.model.*;
import com.power.model.PowerVO;


public class FeaturesServlet  extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String listAllAdmin = "/adminChk/listAllAdmin.jsp";
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("listPowers_ByFeaturesno_A".equals(action) || "listPowers_ByFeaturesno_B".equals(action)) {
			                                                 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				Integer feano = new Integer(req.getParameter("feano"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				Set<PowerVO> set = featuresSvc.getPowersByFeaturesno(feano);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listPowers_ByFeaturesno", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listPowers_ByFeaturesno_A".equals(action))
					url = "/features/listPowers_ByFeaturesno.jsp";        // ���\��� court/listGames_ByCourtno.jsp
				else if ("listPowers_ByFeaturesno_B".equals(action))
					url = "/features/listAllFeatures.jsp";              // ���\��� court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("feano");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				Integer feano = null;
				try {
					feano = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
				if (featuresVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("featuresVO", featuresVO); // ��Ʈw���X��featuresVO����,�s�Jreq
				String url = "/features/listOneFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneFeatures.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllFeatures.jsp ��  /dept/listFeaturess_ByDeptno.jsp ���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/features/listAllFeatures.jsp�j ��  �i/dept/listFeaturess_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer feano = new Integer(req.getParameter("feano"));
				
				/***************************2.�}�l�d�߸��****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("featuresVO", featuresVO); // ��Ʈw���X��featuresVO����,�s�Jreq
				String url = "/features/update_features_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_features_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		////TODO
		if ("insert".equals(action)) { // �Ӧ�addFeatures.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				
				String feapower = req.getParameter("feapower").trim();
				
				FeaturesVO featuresVO = new FeaturesVO();
//				featuresVO.setFeapower(feapower);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featuresVO", featuresVO); // �t����J�榡���~��featuresVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/addFeatures.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				FeaturesService featuresSvc = new FeaturesService();
				featuresVO = featuresSvc.addFeatures(feapower);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/features/listAllFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin); // �s�W���\�����listAllFeatures.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/features/addFeatures.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // �Ӧ�listAllFeatures.jsp ��  /dept/listFeaturess_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/features/listAllFeatures.jsp�j ��  �i/dept/listFeaturess_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j

			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer feano = new Integer(req.getParameter("feano"));
				
				/***************************2.�}�l�R�����***************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
				featuresSvc.deleteFeatures(feano);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
//				FeaturesService deptSvc = new DeptService();
				if(requestURL.equals("/features/listAllFeatures.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllFeatures",featuresSvc.getAll()); // ��Ʈw���X��list����,�s�Jrequest

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
	}
}
