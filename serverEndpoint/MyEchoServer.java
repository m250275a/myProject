package serverEndpoint;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.member.model.MemberVO;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

@ServerEndpoint("/ChatServer/{userName}")
public class MyEchoServer {

	// private static final Set<Session> allSessions =
	// Collections.synchronizedSet(new HashSet<Session>());
	private static final Map<String, Session> userList = Collections.synchronizedMap(new HashMap<String, Session>());

	// private static final Set<Session> allSessions =
	// Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws JSONException, IOException {
		// allSessions.add(userSession);
		// System.out.println(userName + " : " + userSession);
		userList.put(userName, userSession);

//		JSONObject returnMessage = new JSONObject();
//		returnMessage.put("sendUserName", "系統公告");
//		returnMessage.put("message", "aaaaaaaaaaaaaa");
//		userSession.getAsyncRemote().sendText(returnMessage.toString());
	}

	@OnMessage
	public void onMessage(Session userSession, String message) throws JSONException {
		JSONObject jsonObj = new JSONObject(message);
		Session session = null;
		String userName = jsonObj.getString("userName");
		String mess = jsonObj.getString("message");
//		session = userList.get(userName);// 取得收信方的SESSION
//		if (!jsonObj.getString("userName").equals("0")) {
//			if (!session.equals(userSession)) {
//				session.getAsyncRemote().sendText(message);
//				
//			}
			
			
//		} else if (jsonObj.getString("userName").equals("0")){
		//// for取值
					// for (Session session2 : allSessions) {
					// if (session2.isOpen())
					// session2.getAsyncRemote().sendText(message);
					// }

					//// Iterator取值
					Set set = userList.keySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Object myKey = it.next();
						session = userList.get(myKey);
						if(session.isOpen()){
							if (session.equals(userSession)||mess==myKey) {
								continue;
							}
							session.getAsyncRemote().sendText(message);
						}
						
					}
		
//		}else{
//			JSONObject returnMessage = new JSONObject();
//			returnMessage.put("sendUserName", jsonObj.getString("userName"));
//			returnMessage.put("message", "不在線上!");
//			userSession.getAsyncRemote().sendText(returnMessage.toString());
//		}

		// List<String> list = new ArrayList<String>();
		// String myName = (String)
		// userSession.getUserProperties().get("teamno");
		// System.out.println("myName:" + myName);
		//
		// for (Session session : allSessions) {
		// if (session.isOpen()&&session.getId().equals("1"))
		// session.getAsyncRemote().sendText(message);
		// }
		// System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		// e.printStackTrace();
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		userList.remove(userSession);
		// System.out.println(userSession.getId() + ": Disconnected: " +
		// Integer.toString(reason.getCloseCode().getCode()));
	}

}
