package com.http.server.demo;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{
	private Response resp;
	private Request req;
	private Socket client;
	private Integer code=200;
	
	public Dispatcher(Socket client) {
		this.client=client;
		try {
			this.resp=new Response(client.getOutputStream());
			this.req=new Request(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			code=500;
			return ;
		}
	}
	
	@Override
	public void run() {
		try {
			Servlet servlet = WebApp.getServlet(req.getUrl());
			if(null==servlet){
				this.code=404;
			}
			servlet.Service(req, resp);
		} catch (Exception e1) {
			this.code=500;
		} 
		
		try {
			resp.pushToClient(code);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		CloseUtil.closeSocket(client);
	}

}
