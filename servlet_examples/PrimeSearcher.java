package servlet_examples;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.member.controller.MailService;
import com.member.model.*;
public class PrimeSearcher extends HttpServlet implements Runnable {

  long lastprime = 0;                    // last prime found
  Date lastprimeModified = new Date();   // when it was found
  Thread searcher;                       // background search thread
  static String memberInfo;
  static String memberMail;
  public void destroy() {
    searcher=null;
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();
    if (lastprime == 0) {
      out.println("Still searching for first prime...");
    }
    else {
      out.println("The last prime discovered was " + lastprime);
      out.println(" at " + lastprimeModified);
    }
  }
  public void init() throws ServletException {
    searcher = new Thread(this);
    searcher.setPriority(Thread.MIN_PRIORITY);  // be a good citizen
    searcher.start();
  }
  private static boolean isToDay(Date birthday) {
	  MemberService memberSvc = new MemberService();
	  List<MemberVO> memberVO = memberSvc.getAll();
	 
	  for(MemberVO list:memberVO){
		 if((list.getMemage()).equals(birthday)){
			 memberInfo=list.getMemname();
			 memberMail=list.getMememail();
			 return true;
		 }
	  }
    return false;
  }
  public void run() {
	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  java.util.Date time = null;
	try {
		time = df.parse(df.format(lastprimeModified));
	} catch (ParseException e) {
	}
//	  Long currentime = time.getTime();
	  java.sql.Date birthday = new java.sql.Date(time.getTime());
    while (true) {         
      if (isToDay(birthday)) {
    	//  String to = memberMail;  //動態寫法，抓當下會員MAIL寄出
    	  	String to = "aa103g05@gmail.com";
			String subject = "會員生日通知信";
			String messageText = "您好:"+memberInfo+"\n"+
								"此信件由'全台制霸'網站寄出,"+
								"歡迎常回來逛逛\n"+
								"生日快樂!!!";
			
			MailService reportmail = new MailService();
			reportmail.sendMail(to, subject, messageText);  
      }
//      time += 1;     
      try {
    	  System.out.println(birthday); //表示背景執行續有在跑
    	  Thread.sleep(24*60*60*1000);
      }
      catch (InterruptedException ignored) {
    	  
      }
    }
  }
}
