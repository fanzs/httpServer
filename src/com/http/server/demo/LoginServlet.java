package com.http.server.demo;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request req, Response resp) throws Exception {
		resp.println("<html><head><title>index</title>");
		resp.println("</head><body><h1>");
		if(login(req.getParameterValue("name"),req.getParameterValue("passwd"))){
			resp.print("欢迎 ");
			resp.print(req.getParameterValue("name"));
			resp.println(" 回来");
		}else{
			resp.println("登录失败");
		}
		resp.println("</h1></body></html>");
	}
	
	private boolean login(String name,String pwd){
		return "fzs".equals(name) && "123".equals(pwd);
	}

	@Override
	public void doPost(Request req, Response resp) throws Exception {

	}

}
