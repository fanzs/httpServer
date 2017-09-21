package com.http.server.demo;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
	//为每一个Servlet去个别名
	private Map<String,Servlet> servlet;

	//
	private Map<String,String> mapping;
	
	public ServletContext() {
		servlet=new HashMap<String,Servlet>();
		mapping=new HashMap<String,String>();
	}
	
	public Map<String, Servlet> getServlet() {
		return servlet;
	}
	
	public void setServlet(Map<String, Servlet> servlet) {
		this.servlet = servlet;
	}
	
	public Map<String, String> getMapping() {
		return mapping;
	}
	
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
}
