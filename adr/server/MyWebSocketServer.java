package adr.server;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

@ServerEndpoint("/MyWebSocketServer/{courtno}/{memno}/{memname}")
public class MyWebSocketServer {

//	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	private static final Map<String[],Session> connectedSessions = Collections.synchronizedMap(new HashMap<String[],Session>());

	@OnOpen
	public void onOpen(@PathParam("courtno") String courtno, @PathParam("memno") String memno, @PathParam("memname") String memname, Session userSession) throws IOException {
//		connectedSessions.add(userSession);
		String[] courtMem =  {courtno,memno,memname};
		connectedSessions.put(courtMem, userSession);
		String text = String.format("Session ID = %s connected,", userSession.getId());
		System.out.println(text + "   courtno: "+ courtMem[0] + " memno: "+ courtMem[1] + " memname: "+ courtMem[2]);
	}

	@OnMessage
//	public void onMessage(Session userSession, String message) {
	public void onMessage(@PathParam("courtno") String courtno, @PathParam("memno") String memno, @PathParam("memname") String memname, Session userSession, String message) {
		System.out.println("courtno: "+courtno + " and " + "memno: " + memno + " and " + "memname: " + memname);  
//		for (Session session : connectedSessions) {
//			if (session.isOpen())
//				session.getAsyncRemote().sendText(message);
//		}
		
		for (Map.Entry<String[],Session> entry : connectedSessions.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			   String[] courtMem = (String[]) entry.getKey();
			   String mapCourtno = courtMem[0];
			   String mapMemno = courtMem[1];
			   String mapMemname = courtMem[2];
			   Session mapSession = (Session) entry.getValue();
			   if(mapCourtno.equals(courtno)){
				   if (mapSession.isOpen())
					   mapSession.getAsyncRemote().sendText(message);
			   }
		}

		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		connectedSessions.remove(userSession);
		String text = String.format("session ID = %s, disconnected; close code = %d", userSession.getId(),
				reason.getCloseCode().getCode());
		System.out.println(text);
	}
}
