package adr.court;

import adr.tool.util.ImageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/CourtServlet")
public class CourtServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CourtDao courtDAO = new CourtDaoMySqlImpl();
		List<CourtVO> courtList = courtDAO.getAll();
		writeText(response, new Gson().toJson(courtList));
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
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		CourtDao courtDao = new CourtDaoMySqlImpl();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);
		
		//action.equals("getDots")
		ServletContext context = getServletContext();
		if(context.getAttribute("dotVOSet")==null){
			context.setAttribute("dotVOSet", new LinkedHashSet<DotVO>());
		}
		LinkedHashSet<DotVO> dotVOSet = (LinkedHashSet<DotVO>) context.getAttribute("dotVOSet");
		//action.equals("getDots")
		//�y�����
		dotVOSet.add(new DotVO(5410, "����D", "KK10@KK.com", 24.97, 121.18, true));
		dotVOSet.add(new DotVO(5411, "�y�t��", "KK11@KK.com", 24.93, 121.17, true));
		dotVOSet.add(new DotVO(5404, "������", "KK4@KK.com", 24.93, 121.26, true));
		dotVOSet.add(new DotVO(5414, "�T����", "KK14@KK.com", 24.89, 121.25, true));
		dotVOSet.add(new DotVO(5407, "�c���}��", "KK7@KK.com", 24.99, 121.23, true));
		dotVOSet.add(new DotVO(5405, "�������", "KK5@KK.com", 24.96, 121.27, true));
		dotVOSet.add(new DotVO(5400, "�w��нm", "KK00@KK.com", 24.90, 121.20, true));
		
		
		if (action.equals("getAll")) {
			List<CourtVO> courtList = courtDao.getAll();
			writeText(response, gson.toJson(courtList));
			System.out.println("getAll: KKKKKKKKKKKKKKK");
		}
		
		if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int courtno = jsonObject.get("courtno").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = courtDao.getImage(courtno);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				System.out.println("getImage courtno: "+courtno);
			}
			os.write(image);
		}
		
		if (action.equals("getDots")) {
			String jsonDotVO = jsonObject.get("dotVO").getAsString();
			DotVO dotVO = new Gson().fromJson(jsonDotVO, DotVO.class);
			//�W���w�g���o�F"dotVOSet"  LinkedHashSet<DotVO> dotVOSet = (LinkedHashSet<DotVO>) context.getAttribute("dotVOSet");
			dotVOSet.remove(dotVO);
			writeText(response, gson.toJson(dotVOSet));
			if(dotVO.getInField()){      //true  : �^�Ǫ��O�Ҧ��b�p�F�d�򤺦����]�t�ۤv���I�A�M��A��ۤv���I"�]�i"���A����context���L�H"�]��"���o
				dotVOSet.add(dotVO);
			}else{						 //false : �^�Ǫ��O�Ҧ��b�p�F�d�򤺦����]�t�ۤv���I�A�M��A��ۤv���I"�c�X"���A����context���L�H"����"���o
				//nothing				
			}
			context.setAttribute("dotVOSet", dotVOSet);
			System.out.println("getDots: KKKKKKKKKKKKKKK");
		}
		
		if (action.equals("courtInsert") || action.equals("courtUpdate")) {
			String courtJson = jsonObject.get("courtVO").getAsString();
			CourtVO courtVO = gson.fromJson(courtJson, CourtVO.class);
			String imageBase64 = jsonObject.get("imageBase64").getAsString();
			byte[] image = Base64.decodeBase64(imageBase64);
			int count = 0;
			if (action.equals("courtInsert")) {
				count = courtDao.insert(courtVO, image);
			} else if (action.equals("courtUpdate")) {
				count = courtDao.update(courtVO, image);
			}
			writeText(response, String.valueOf(count));
		}
		
		if (action.equals("courtDelete")) {
			String courtJson = jsonObject.get("courtVO").getAsString();
			CourtVO courtVO = gson.fromJson(courtJson, CourtVO.class);
			int count = courtDao.delete(courtVO.getCourtno());
			writeText(response, String.valueOf(count));
		}
		
		if (action.equals("findByCourtno")) {
			Integer courtno = jsonObject.get("courtno").getAsInt();
			CourtVO courtVO = courtDao.findByCourtno(courtno);
			writeText(response, gson.toJson(courtVO));
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