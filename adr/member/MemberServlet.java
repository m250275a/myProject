package adr.member;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println(jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),JsonObject.class);
		MemberDao memberDao = new MemberDaoMySqlImpl();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);
		
		String jsonMemberVO = jsonObject.get("memberVO").getAsString();
		MemberVO memberVO = new Gson().fromJson(jsonMemberVO, MemberVO.class);
		
		if (action.equals("findByMememail")) {
			String mememail = memberVO.getMememail();
			MemberVO memberVO2 = memberDao.findByMememail(mememail);
			if(memberVO2==null){
				memberVO2 = new MemberVO(null,null,null,null);
				System.out.println("returnMemVO:  "+gson.toJson(memberVO2));
				writeText(response, gson.toJson(memberVO2));
				return;
			}
			String password  = memberVO.getMempassword(); 
			String password2 = memberVO2.getMempassword(); 
			if(password.equals(password2) && password2 != null ){
				memberVO2.setMempassword(null);
				System.out.println("returnMemVO:  "+gson.toJson(memberVO2));
				writeText(response, gson.toJson(memberVO2));
			}else {
				memberVO2.setMemno(null);
				memberVO2.setMemname(null);
				memberVO2.setMememail(null);;
				memberVO2.setMempassword(null);
				System.out.println("returnMemVO:  "+gson.toJson(memberVO2));
				writeText(response, gson.toJson(memberVO2));
			}
		}
	}

	

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
	}
}