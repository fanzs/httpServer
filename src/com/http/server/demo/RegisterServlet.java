package com.http.server.demo;

public class RegisterServlet extends Servlet{

	@Override
	public void doGet(Request req, Response resp) throws Exception {
		
	}

	@Override
	public void doPost(Request req, Response resp) throws Exception {
		resp.println("<html><head><title>返回注册 </title>");
		resp.println("</head><body><h1>");
		resp.print("你的用户名为： ");
		resp.print(req.getParameterValue("name"));
		resp.println("</h1></body></html>");
	}

}
