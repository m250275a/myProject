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
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("newsno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入新聞編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer newsno = null;
				try {
					newsno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("新聞編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
				if (newsVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
				String url = "/front-end/listOneNews2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneNews.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllNews.jsp 或  /dept/listNewss_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/news/listAllNews.jsp】 或  【/dept/listNewss_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer newsno = new Integer(req.getParameter("newsno"));
				
				/***************************2.開始查詢資料****************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
				String url = "/news/update_news_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_news_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_news_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/news/listAllNews.jsp】 或  【/dept/listNewss_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer newsno = new Integer(req.getParameter("newsno").trim());
				java.sql.Date newsdate = null;
				try {
					newsdate = java.sql.Date.valueOf(req.getParameter("newsdate").trim());
				} catch (IllegalArgumentException e) {
					newsdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				String newscon = req.getParameter("newscon").trim();
				String newsfullcon = req.getParameter("newsfullcon").trim();
				
				Part newsimg0 = req.getPart("newsimg");
				if (newsimg0 == null || newsimg0.getSize() == 0) {
					errorMsgs2.add("請選擇照片");
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
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/update_news_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, newsdate, newscon, newsfullcon, newsimg);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				

				if(requestURL.equals("/news/listAllNews.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllNews",newsSvc.getAll()); // 資料庫取出的list物件,存入request

                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/update_news_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addNews.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			List<String> errorMsgs2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs2", errorMsgs2);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String newscon = req.getParameter("newscon").trim();
				String newsfullcon = req.getParameter("newsfullcon").trim();
				
				Part newsimg0 = req.getPart("newsimg");
				if (newsimg0 == null || newsimg0.getSize() == 0) {
					errorMsgs2.add("請選擇照片");
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
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/addNews.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(newscon, newsfullcon, newsimg);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllNews.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/addNews.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // 來自listAllNews.jsp 或  /dept/listNewss_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/news/listAllNews.jsp】 或  【/dept/listNewss_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				Integer newsno = new Integer(req.getParameter("newsno"));
				
				/***************************2.開始刪除資料***************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(newsno);
				newsSvc.deleteNews(newsno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				NewsService deptSvc = new DeptService();
				if(requestURL.equals("/news/listAllNews.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
					req.setAttribute("listAllNews",newsSvc.getAll()); // 資料庫取出的list物件,存入request

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
		// 後端news新增
				if ("insertNews".equals(action)) { // 來自addNews2.jsp的請求

					List<String> errorMsgs = new LinkedList<String>();

					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/***********************
						 * 1.接收請求參數 - 輸入格式的錯誤處理
						 *************************/

						String newscon = req.getParameter("newscon").trim();
						String newsfullcon = req.getParameter("newsfullcon").trim();

						Part newsimg0 = req.getPart("newsimg");
						if (newsimg0 == null || newsimg0.getSize() == 0) {
							errorMsgs.add("請選擇照片");
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
							errorMsgs.add("未輸入資訊");
						}

						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
							RequestDispatcher failureView = req.getRequestDispatcher("/back-end/addNews2.jsp");
							failureView.forward(req, res);
							return;
						}

						/***************************
						 * 2.開始新增資料
						 ***************************************/
						NewsService newsSvc = new NewsService();
						newsVO = newsSvc.addNews(newscon, newsfullcon, newsimg);

						if (newsVO != null) {
							errorMsgs.add("已新增新訊!");
						}

						if (!errorMsgs.isEmpty()) {
							String url = "/back-end/addNews2.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交/back-end/addNews2.jsp.jsp
							successView.forward(req, res);
							return;
						}

						/*************************** 其他可能的錯誤處理 **********************************/
					} catch (Exception e) {
						errorMsgs.add(e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/addNews2.jsp");
						failureView.forward(req, res);
					}
				}
		
	}
}
