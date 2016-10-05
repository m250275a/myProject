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
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer gametype = new Integer(req.getParameter("gametype"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByGametype(gametype);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // ��Ʈw���X��set����,�s�Jrequest
				String url = null;
				if ("getType_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // ���\���
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // ���\���
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("getCourt_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer courtno = new Integer(req.getParameter("courtno"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByCourtno(courtno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // ��Ʈw���X��set����,�s�Jrequest
				String url = null;
				if ("getCourt_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // ���\���
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // ���\���
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("getTeam_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer teamno = new Integer(req.getParameter("teamno"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByTeam(teamno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("listAllGameBySearch", list); // ��Ʈw���X��set����,�s�Jrequest
				String url = null;
				if ("getTeam_For_Display".equals(action))
					url = "/front-end/listAllGameBySearch.jsp"; // ���\���
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // ���\���
														// court/listAllCourt.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("listTeams_ByGameno_A".equals(action) || "listTeams_ByGameno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.�}�l�d�߸��
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
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("listTeams_ByGameno", listTeams_ByGameno); // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listTeams_ByGameno_A".equals(action))
					url = "/front-end/listTeams_ByGameno.jsp"; // ���\���
																// court/listTeams_ByCourtno.jsp
				else if ("listTeams_ByGameno_B".equals(action))
					url = "/front-end/listAllGame.jsp"; // ���\���
														// court/listAllCourt.jsp

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
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				String str = req.getParameter("gameno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���ɽs��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer gameno = null;
				try {
					gameno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("���ɽs���榡�����T");
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
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);
				if (gameVO == null) {
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
				req.setAttribute("gameVO", gameVO); // ��Ʈw���X��gameVO����,�s�Jreq
				String url = "/front-end/listOneGame.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���
																				// listOneGame.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllGame.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ****************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.�}�l�d�߸��
				 ****************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);

				/***************************
				 * 3.�d�ߧ���,�ǳ����(Send the Success view)
				 ************/
				req.setAttribute("gameVO", gameVO); // ��Ʈw���X��gameVO����,�s�Jreq
				String url = "/front-end/update_game_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\���
																				// update_game_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_game_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer gameno = new Integer(req.getParameter("gameno").trim());
				// java.sql.Date gamedate = null;
				// try {
				// gamedate =
				// java.sql.Date.valueOf(req.getParameter("gamedate").trim());
				// } catch (IllegalArgumentException e) {
				// gamedate=new java.sql.Date(System.currentTimeMillis());
				// errorMsgs.add("�п�J���!");
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
				// errorMsgs.add("�٨S���ѥ[�y����!");
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
					errorMsgs.add("�w�e�X�D���o!");
				}

				/***************************
				 * 2.�}�l�ק���
				 *****************************************/
				GameService gameSvc = new GameService();
				gameVO = gameSvc.updateGame(gameno, teamno2);

				/***************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/
				req.setAttribute("gameVO", gameVO); // ��Ʈwupdate���\��,���T����courtVO����,�s�Jreq
				String url = "/front-end/listAllGame2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneCourt.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllGame2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addGame.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 *************************/
				java.sql.Date gamedate = null;
				try {
					gamedate = java.sql.Date.valueOf(req.getParameter("gamedate").trim());
				} catch (IllegalArgumentException e) {
					gamedate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				Integer memno = new Integer(req.getParameter("memno").trim());
				Integer teamno = new Integer(req.getParameter("teamno").trim());

				// Integer teamno = null;
				// try {
				// teamno = new Integer(req.getParameter("teamno").trim());
				// } catch (NumberFormatException e) {
				// teamno = 0;
				// errorMsgs.add("�٨S���ѥ[�y����!");
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
					req.setAttribute("gameVO", gameVO); // �t����J�榡���~��gameVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/addGame.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************
				 * 2.�}�l�s�W���
				 ***************************************/
				GameService gameSvc = new GameService();
				gameVO = gameSvc.addGame(gamedate, memno, teamno, courtno, gametype);

				/***************************
				 * 3.�s�W����,�ǳ����(Send the Success view)
				 ***********/
				String url = "/front-end/listAllGame2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllGame.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllGame2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllGame.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/***************************
				 * 1.�����ШD�Ѽ�
				 ***************************************/
				Integer gameno = new Integer(req.getParameter("gameno"));

				/***************************
				 * 2.�}�l�R�����
				 ***************************************/
				GameService gameSvc = new GameService();
				GameVO gameVO = gameSvc.getOneGame(gameno);
				gameSvc.deleteGame(gameno);

				/***************************
				 * 3.�R������,�ǳ����(Send the Success view)
				 ***********/
				CourtService courtSvc = new CourtService();
				if (requestURL.equals("/court/listGames_ByCourtno.jsp") || requestURL.equals("/court/listAllCourt.jsp"))
					req.setAttribute("listGames_ByCourtno", courtSvc.getGamesByCourtno(gameVO.getCourtno())); // ��Ʈw���X��list����,�s�Jrequest

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		// �ק���ɵ��G
		if ("updateresult".equals(action)) { // �Ӧ�teamWins.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/***************************
				 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
				 **********************/
				Integer gameno = new Integer(req.getParameter("gameno"));
				String gameresult = req.getParameter("gameresult").trim();
				Integer teamno = new Integer(req.getParameter("teamno"));
				Integer gametype = new Integer(req.getParameter("gametype"));


				/***************************
				 * 2.�}�l�ק���
				 *****************************************/
				GameVO gameVO = new GameVO();
				gameVO.setGameno(gameno);
				gameVO.setGameresult(gameresult);
				GameService gameSvc = new GameService();
				gameVO = gameSvc.updateGameresult(gameno, gameresult);
				
				// ���o���y��no
				GameVO gameVO1 = gameSvc.getOneGame(gameno);
				Integer teamno2 = null;
				if (teamno.equals(gameVO1.getTeamno())) {
					teamno2 = gameVO1.getTeamno2();
				} else {
					teamno2 = gameVO1.getTeamno();
				}
								
				// �s�W�y���ӭt��
				TeamService teamSvc = new TeamService();
				TeamVO teamVO = null;

				if ("��".equals(gameresult)) {
					teamVO = teamSvc.addTeamWins(teamno);
					if (gameVO != null && (gametype == 2 || gametype == 5)) {
						errorMsgs.add("�A�i�H�h��誺�y���������X�o!");
						req.setAttribute("teamno2", teamno2);
					}
					
					if (gameVO != null && (gametype == 3 || gametype == 6)) {
						errorMsgs.add("�A���o���y�����R�W�v�o!");
						req.setAttribute("gametype", gametype);
					}
				}
				if ("��".equals(gameresult)) {
					teamVO = teamSvc.addTeamLose(teamno);
				}

				// �ק�᪺���
				TeamService teamSvc1 = new TeamService();
				teamVO = teamSvc1.getOneOfTeam(teamno);
				req.setAttribute("teamVO", teamVO);


				// select ��e�y���ӳ�
				TeamService teamSvc3 = new TeamService();
				Set<GameVO> teamwin = teamSvc3.getGameByTeamno(teamno);
				req.setAttribute("teamwin", teamwin);

				/***************************
				 * 3.�ק粒��,�ǳ����(Send the Success view)
				 *************/

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���winsload.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("���I��ӭt");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/teamWins.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
