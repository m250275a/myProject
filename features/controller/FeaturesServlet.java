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
				/*************************** 1.接收請求參數 ****************************************/
				Integer feano = new Integer(req.getParameter("feano"));

				/*************************** 2.開始查詢資料 ****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				Set<PowerVO> set = featuresSvc.getPowersByFeaturesno(feano);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listPowers_ByFeaturesno", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listPowers_ByFeaturesno_A".equals(action))
					url = "/features/listPowers_ByFeaturesno.jsp";        // 成功轉交 court/listGames_ByCourtno.jsp
				else if ("listPowers_ByFeaturesno_B".equals(action))
					url = "/features/listAllFeatures.jsp";              // 成功轉交 court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("feano");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer feano = null;
				try {
					feano = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
				if (featuresVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("featuresVO", featuresVO); // 資料庫取出的featuresVO物件,存入req
				String url = "/features/listOneFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneFeatures.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllFeatures.jsp 或  /dept/listFeaturess_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/features/listAllFeatures.jsp】 或  【/dept/listFeaturess_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer feano = new Integer(req.getParameter("feano"));
				
				/***************************2.開始查詢資料****************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("featuresVO", featuresVO); // 資料庫取出的featuresVO物件,存入req
				String url = "/features/update_features_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_features_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		////TODO
		if ("insert".equals(action)) { // 來自addFeatures.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String feapower = req.getParameter("feapower").trim();
				
				FeaturesVO featuresVO = new FeaturesVO();
//				featuresVO.setFeapower(feapower);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("featuresVO", featuresVO); // 含有輸入格式錯誤的featuresVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/features/addFeatures.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				FeaturesService featuresSvc = new FeaturesService();
				featuresVO = featuresSvc.addFeatures(feapower);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/features/listAllFeatures.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(listAllAdmin); // 新增成功後轉交listAllFeatures.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/features/addFeatures.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // 來自listAllFeatures.jsp 或  /dept/listFeaturess_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/features/listAllFeatures.jsp】 或  【/dept/listFeaturess_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				Integer feano = new Integer(req.getParameter("feano"));
				
				/***************************2.開始刪除資料***************************************/
				FeaturesService featuresSvc = new FeaturesService();
				FeaturesVO featuresVO = featuresSvc.getOneFeatures(feano);
				featuresSvc.deleteFeatures(feano);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				FeaturesService deptSvc = new DeptService();
				if(requestURL.equals("/features/listAllFeatures.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllFeatures",featuresSvc.getAll()); // 資料庫取出的list物件,存入request

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}
