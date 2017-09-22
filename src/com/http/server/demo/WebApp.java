package com.http.server.demo;

import java.util.Map;

public class WebApp {
	private static ServletContext context;
	static{
		context=new ServletContext();
		
		Map<String,String>mapping=context.getMapping();
		mapping.put("/login", "login");
		mapping.put("/log", "login");
		mapping.put("/reg", "register");
		
		Map<String,String> servlet=context.getServlet();
		servlet.put("login", "com.http.server.demo.LoginServlet"); 
		servlet.put("register", "com.http.server.demo.RegisterServlet"); 
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Servlet getServlet(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if((null==url) || (url=url.trim()).equals("")){
			return null;
		}
		
//		return context.getServlet().get(context.getMapping().get(url));
		//通过反射根据完整路径名获取实体类
		//确保空构造存在
		return (Servlet) Class.forName(context.getServlet().get(context.getMapping().get(url))).newInstance();
	}
}
