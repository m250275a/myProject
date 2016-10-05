package com.game.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.game.model.*;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.memteam.model.MemteamVO;
import com.team.model.TeamService;
import com.team.model.TeamVO;
import com.challmode.model.ChallmodeVO;
import com.court.model.*;

public class GameServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getType_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer gametype = new Integer(req.getParameter("gametype"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByGametype(gametype);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // 資料庫取出的set物件,存入request
				String url = null;
				if ("getType_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // 成功轉交
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // 成功轉交
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("getCourt_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByCourtno(courtno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // 資料庫取出的set物件,存入request
				String url = null;
				if ("getCourt_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // 成功轉交
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // 成功轉交
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("getTeam_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer teamno = new Integer(req.getParameter("teamno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByTeam(teamno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // 資料庫取出的set物件,存入request
				String url = null;
				if ("getTeam_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // 成功轉交
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // 成功轉交
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("listTeams_ByGameno_A".equals(action) || "listTeams_ByGameno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = teamSvc.getOneTeam(gameVO.getTeamno());
				TeamVO team2VO = teamSvc.getOneTeam(gameVO.getTeamno2());
				Set<TeamVO> listTeams_ByGameno = new LinkedHashSet<>();
				listTeams_ByGameno.add(teamVO);
				listTeams_ByGameno.add(team2VO);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("listTeams_ByGameno", listTeams_ByGameno); // 資料庫取出的set物件,存入request

				String url = null;
				if ("listTeams_ByGameno_A".equals(action))
					url = "/front-end/listTeams_ByGameno.jsp"; // 成功轉交
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // 成功轉交
														// court/listAllCourt.jsp

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
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String str = req.getParameter("gameno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入比賽編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer gameno = null;
				try {
					gameno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("比賽編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 2.開始查詢資料
				 *****************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);
				if (gameVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("gameVO", gameVO); // 資料庫取出的gameVO物件,存入req
				String url = "/front-end/listOneGame.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneGame.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllGame.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.接收請求參數
				 ****************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.開始查詢資料
				 ****************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("gameVO", gameVO); // 資料庫取出的gameVO物件,存入req
				String url = "/front-end/update_game_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_game_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_game_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer gameno = new Integer(req.getParameter("gameno").trim());
				// java.sql.Date gamedate = null;
				// try {
				// gamedate =
				// java.sql.Date.valueOf(req.getParameter("gamedate").trim());
				// } catch (IllegalArgumentException e) {
				// gamedate=new java.sql.Date(System.currentTimeMillis());
				// errorMsgs.add("請輸入日期!");
				// }
				// Integer memno = new
				// Integer(req.getParameter("memno").trim());
				// Integer teamno = new
				// Integer(req.getParameter("teamno").trim());
				Integer teamno2 = new Integer(req.getParameter("teamno2").trim());
				// Integer teamno2 = null;
				// try {
				// teamno2 = new Integer(req.getParameter("teamno2").trim());
				// } catch (NumberFormatException e) {
				// teamno2 = 0;
				// errorMsgs.add("還沒有參加球隊喔!");
				// }
				// Integer courtno = new
				// Integer(req.getParameter("courtno").trim());
				// Integer gametype = new
				// Integer(req.getParameter("gametype").trim());
				// String gameresult = req.getParameter("gameresult").trim();

				GameVO gameVO = new GameVO();
				gameVO.setGameno(gameno);
				// gameVO.setGamedate(gamedate);
				// gameVO.setMemno(memno);
				// gameVO.setTeamno(teamno);
				gameVO.setTeamno2(teamno2);
				// gameVO.setCourtno(courtno);
				// gameVO.setGametype(gametype);
				// gameVO.setGameresult(gameresult);

				// Send the use back to the form, if there were errors
				if (gameVO != null) {
					errorMsgs.add("已送出挑戰囉!");
				}

				/***************************
				 * 2.開始修改資料
				 *****************************************/
				GameService gameSvc = new GameService();
				gameVO = gameSvc.updateGame(gameno, teamno2);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("gameVO", gameVO); // 資料庫update成功後,正確的的courtVO物件,存入req
				String url = "/front-end/listAllGame2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCourt.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllGame2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addGame.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				java.sql.Date gamedate = null;
				try {
					gamedate = java.sql.Date.valueOf(req.getParameter("gamedate").trim());
				} catch (IllegalArgumentException e) {
					gamedate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Integer teamno = null;
				// try {
				// teamno = new Integer(req.getParameter("teamno").trim());
				// } catch (NumberFormatException e) {
				// teamno = 0;
				// errorMsgs.add("還沒有參加球隊喔!");
				// }

				Integer courtno = new Integer(req.getParameter("courtno").trim());
				Integer gametype = new Integer(req.getParameter("gametype").trim());

				GameVO gameVO = new GameVO();
				gameVO.setGamedate(gamedate);
				gameVO.setMemno(memno);
				gameVO.setTeamno(teamno);
				gameVO.setCourtno(courtno);
				gameVO.setGametype(gametype);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gameVO", gameVO); // 含有輸入格式錯誤的gameVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addGame.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.開始新增資料
				 ***************************************/
				GameService gameSvc = new GameService();
				gameVO = gameSvc.addGame(gamedate, memno, teamno, courtno, gametype);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/front-end/listAllGame2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllGame.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllGame2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllGame.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.接收請求參數
				 ***************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.開始刪除資料
				 ***************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);
				gameSvc.deleteGame(gameno);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				CourtService courtSvc = new CourtService();
				if (requestURL.equals("/court/listGames_ByCourtno.jsp") || requestURL.equals("/court/listAllCourt.jsp"))
					req.setAttribute("listGames_ByCourtno", courtSvc.getGamesByCourtno(gameVO.getCourtno())); // 資料庫取出的list物件,存入request

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		// 修改比賽結果
		if ("updateresult".equals(action)) { // 來自teamWins.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				Integer gameno = new Integer(req.getParameter("gameno"));
				String gameresult = req.getParameter("gameresult").trim();
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer gametype = new Integer(req.getParameter("gametype"));


				/***************************
				 * 2.開始修改資料
				 *****************************************/
				GameVO gameVO = new GameVO();
				gameVO.setGameno(gameno);
				gameVO.setGameresult(gameresult);
				GameService gameSvc = new GameService();
				gameVO = gameSvc.updateGameresult(gameno, gameresult);
				
				// 取得對手球隊no
				GameVO gameVO1 = gameSvc.getOneGame(gameno);
				Integer teamno2 = null;
				if (teamno.equals(gameVO1.getTeamno())) {
					teamno2 = gameVO1.getTeamno2();
				} else {
					teamno2 = gameVO1.getTeamno();
				}
								
				// 新增球隊勝負值
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = null;

				if ("勝".equals(gameresult)) {
					teamVO = teamSvc.addTeamWins(teamno);
					if (gameVO != null && (gametype == 2 || gametype == 5)) {
						errorMsgs.add("你可以去對方的球隊頁面插旗囉!");
						req.setAttribute("teamno2", teamno2);
					}
					
					if (gameVO != null && (gametype == 3 || gametype == 6)) {
						errorMsgs.add("你取得對方球場的命名權囉!");
						req.setAttribute("gametype", gametype);
					}
				}
				if ("敗".equals(gameresult)) {
					teamVO = teamSvc.addTeamLose(teamno);
				}

				// 修改後的資料
				TeamService teamSvc1 = new TeamService();
				teamVO = teamSvc1.getOneOfTeam(teamno);
				req.setAttribute("teamVO", teamVO);


				// select 當前球隊勝場
				TeamService teamSvc3 = new TeamService();
				Set<GameVO> teamwin = teamSvc3.getGameByTeamno(teamno);
				req.setAttribute("teamwin", teamwin);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交winsload.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("未點選勝負");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teamWins.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
