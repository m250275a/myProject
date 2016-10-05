package com.court.controller;

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
import com.court.model.*;
import com.team.model.*;

/**
 * Servlet implementation class JsonResponse
 */
public class CourtJsonRes extends HttpServlet {
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
	public CourtJsonRes() {
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
		if ("getCourt".equals(action)) {
			Connection con = null;
			

			try {
				con = ds.getConnection();

				CourtService courtSvc = new CourtService();
				List<CourtVO> list = courtSvc.getAll();
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
	          
				try {
					for (CourtVO courtVO : list) {
						obj.put("courtno", courtVO.getCourtno());
						obj.put("courtlat", courtVO.getCourtlat());
						obj.put("courtlng", courtVO.getCourtlng());
						obj.put("courtname", courtVO.getCourtname());
						obj.put("courtloc", courtVO.getCourtloc());
						obj.put("courtnum", courtVO.getCourtnum());
						obj.put("basketnum", courtVO.getCourtnum());
						obj.put("courttype", courtVO.getCourttype());
						obj.put("opentime", courtVO.getOpentime());
						obj.put("courtlight", courtVO.getCourtlight());
						obj.put("courtdesc", courtVO.getCourtdesc());
						
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
		
		if ("getTeam".equals(action)) {
			Connection con = null;

			try {
				con = ds.getConnection();
				Integer courtno = new Integer(request.getParameter("courtno"));
				

				/*************************** 2.開始查詢資料 ****************************************/
				CourtService courtSvc = new CourtService();
				Set<TeamVO> set = courtSvc.getTeamsByCourtno(courtno);
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
	          
				try {
					for (TeamVO teamVO : set) {
						obj.put("teamno", teamVO.getTeamno());
						obj.put("teamname", teamVO.getTeamname());
						obj.put("teamlevel", teamVO.getTeamlevel());
						obj.put("gender", teamVO.getGender());
						obj.put("wins", teamVO.getWins());
						
						//obj.put("courtimg", courtVO.getCourtimg());
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
	}
}
