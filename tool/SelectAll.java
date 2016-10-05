package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;
import com.member.model.MemberVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SelectAll extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		String action = req.getParameter("action");
		if ("getSelect".equals(action)) {

			String name = "%" + req.getParameter("name").trim() + "%";
			JSONArray array = new JSONArray();
			MemberService memberService = new MemberService();
			List<MemberVO> list = memberService.getAllByName(name);
			
			for (MemberVO memberVO : list) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("memname", memberVO.getMemname());
					
				} catch (Exception e) {
				}
				array.add(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}

	}
}
