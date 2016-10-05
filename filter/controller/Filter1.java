package com.filter.controller;

import java.io.*;

import javax.mail.Session;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdminVO;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
public class Filter1 implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
//		String user=config.getInitParameter("user");		
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session= request.getSession();
		AdminVO adminVO=(AdminVO)session.getAttribute("adminVO");
		if(adminVO==null){
			RequestDispatcher failureView = req.getRequestDispatcher("/admin/login.jsp");
			failureView.forward(req, res);System.out.println(adminVO);
		}
		System.out.println(adminVO);
		chain.doFilter(req, res);
	}
}