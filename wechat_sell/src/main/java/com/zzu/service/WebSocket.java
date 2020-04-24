package com.zzu.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {
    //要引入websocketsession这个包
	private Session session;
	//定义一个容器来储存这些session
	private static CopyOnWriteArraySet<WebSocket> webSocket=new CopyOnWriteArraySet<>();
	@OnOpen
	public void opOpen(Session session){
		this.session=session;
		webSocket.add(this);
		System.out.println("有新的连接，总数"+webSocket.size());
	}
	@OnClose
	public void onClose(){
		webSocket.remove(this);
		System.out.println("连接端开，总数"+webSocket.size());
	}
	@OnMessage
	public void onMessage(String message){
		
		System.out.println("收到客户端发来的消息"+message);
	}
	
	public void sendMessage(String message){
		for(WebSocket ws:webSocket){
			System.out.println("广播消息"+message);
			try {
				ws.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
