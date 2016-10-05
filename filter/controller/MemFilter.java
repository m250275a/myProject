package com.filter.controller;

import java.io.*;

import javax.mail.Session;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
public class MemFilter implements Filter {

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
		req.setCharacterEncoding("UTF-8");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session= request.getSession();
		MemberVO memberVO=(MemberVO)session.getAttribute("memberVO");
		if(memberVO==null){
			RequestDispatcher failureView = req.getRequestDispatcher("/login.jsp");
			failureView.forward(req, res);System.out.println(failureView.toString());
		}
		System.out.println(memberVO);
		chain.doFilter(req, res);
	}
}