package com.http.server.demo;

public abstract class Servlet {
	public void Service(Request req,Response resp) throws Exception{

		
		this.doGet(req,resp);
		this.doPost(req,resp);
	}
	
	public abstract void doGet(Request req,Response resp)throws Exception;
	public abstract void doPost(Request req,Response resp)throws Exception;
}
