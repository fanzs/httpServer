package com.http.server.demo;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request req, Response resp) throws Exception {
		resp.println("<html><head><title>index</title>");
		resp.println("</head><body><h1>");
		resp.print("欢迎 ");
		resp.print(req.getParameterValue("name"));
		resp.println(" 回来");
		resp.println("</h1></body></html>");
	}

	@Override
	public void doPost(Request req, Response resp) throws Exception {

	}

}
