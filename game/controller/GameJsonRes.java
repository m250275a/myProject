package com.game.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.challmode.model.*;
import com.court.model.*;
import com.team.model.*;
import com.game.model.*;

/**
 * Servlet implementation class JsonResponse
 */
public class GameJsonRes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameJsonRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		// TODO Auto-generated method stub
		
		if ("changeName".equals(action)) {
			Connection con = null;

			try {
				con = ds.getConnection();
//				Integer memno = new Integer(request.getParameter("memno"));
				Integer courtno = new Integer(request.getParameter("courtno"));
				//Integer teamno = new Integer(request.getParameter("teamno"));
				/*************************** 2.開始查詢資料 ****************************************/
				CourtService courtSvc=new CourtService();
				CourtVO courtVO=courtSvc.getOneCourt(courtno);
				GameService gameSvc = new GameService();
				List<GameVO> list = gameSvc.getGamesByCourtnoToChange(courtno);
				//List<GameVO> list2 = gameSvc.getGamesByTeam(teamno);
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
	          
				try {
					obj.put("whoname", courtVO.getWhoname());
					array.add(obj);
					for (GameVO gameVO : list) {
						obj.put("courtname", courtVO.getCourtname());
						obj.put("courtloc", courtVO.getCourtloc());
						obj.put("gameno", gameVO.getGameno());
						obj.put("memno", gameVO.getMemno());
						obj.put("teamno", gameVO.getTeamno());
						obj.put("teamno2", gameVO.getTeamno2());
						obj.put("courtno", gameVO.getCourtno());
						obj.put("gametype", gameVO.getGametype());
						obj.put("gameresult", gameVO.getGameresult());
						array.add(obj);
					}
					
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					out.write(array.toString());
					out.flush();
					out.close();
				} catch (Exception e) {

				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if ("getAllGame1".equals(action)) {
			Connection con = null;

			try {
				con = ds.getConnection();
				Integer gametype = new Integer(request.getParameter("gametype"));
//				Integer courtno = new Integer(request.getParameter("courtno"));
//				Integer teamno = new Integer(request.getParameter("teamno"));

				/*************************** 2.開始查詢資料 ****************************************/
				CourtService courtSvc = new CourtService();
				List<CourtVO> list= courtSvc.getAll();
				TeamService teamSvc = new TeamService();
				List<TeamVO> list2= teamSvc.getAll();
				ChallmodeService challmodeSvc=new ChallmodeService();
				List<ChallmodeVO> list3=challmodeSvc.getAll();
				GameService gameSvc = new GameService();
				List<GameVO> list4 = gameSvc.getGamesByGametype(gametype);
				JSONArray array = new JSONArray();
				JSONObject obj1 = new JSONObject();
				JSONObject obj2 = new JSONObject();
				JSONObject obj3 = new JSONObject();
				JSONObject obj4 = new JSONObject();
	          
				try {
					
					for (CourtVO courtVO : list) {
						obj1.put("courtno1", courtVO.getCourtno());
						obj1.put("courtname", courtVO.getCourtname());
						obj1.put("courtloc", courtVO.getCourtloc());
						obj1.put("courtdesc", courtVO.getCourtdesc());
						array.add(obj1);
					}
					
					for (TeamVO teamVO : list2) {
						obj2.put("teamno1", teamVO.getTeamno());
						obj2.put("teamname", teamVO.getTeamname());
						array.add(obj2);
					}
					for (ChallmodeVO challmodeVO : list3) {
						obj3.put("challmode", challmodeVO.getChallmode());
						obj3.put("challcontent", challmodeVO.getChallcontent());
						array.add(obj3);
					}
						
						
					for (GameVO gameVO : list4) {
						obj4.put("gameno", gameVO.getGameno());
						obj4.put("memno", gameVO.getMemno());
						obj4.put("teamno", gameVO.getTeamno());
						obj4.put("teamno2", gameVO.getTeamno2());
						obj4.put("courtno", gameVO.getCourtno());
						obj4.put("gametype", gameVO.getGametype());
						obj4.put("gameresult", gameVO.getGameresult());
						array.add(obj4);
					}
					
					
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					out.write(array.toString());
					out.flush();
					out.close();
				} catch (Exception e) {

				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
