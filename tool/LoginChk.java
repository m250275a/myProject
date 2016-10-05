package tool;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.controller.AdminServlet;
import com.admin.model.AdminService;
import com.admin.model.AdminVO;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
public class LoginChk implements Filter {

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
		
	
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session= request.getSession();
		AdminVO adminVO=(AdminVO)session.getAttribute("adminVO");
	
		if(adminVO==null){
			
			List<String> errorMsgs = new LinkedList<String>();
			errorMsgs.add("請先登入管理者");
			req.setAttribute("errorMsgs", errorMsgs);
			
			RequestDispatcher failureView = req.getRequestDispatcher("/UIBack/login.jsp");
			failureView.forward(req, res);
			
//			response.sendRedirect(request.getContextPath()+"/UIBack/login.jsp");
			return;
		}else{
			
			chain.doFilter(req, res);
		}

	}
}