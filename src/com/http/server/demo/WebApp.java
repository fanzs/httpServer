package com.http.server.demo;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static ServletContext context;
	static{
		
		try {
			//获取解析工厂
			SAXParserFactory factory=SAXParserFactory.newInstance();
			//获取解析器
			SAXParser sax=factory.newSAXParser();
			//指定xml处理器
			WebHandler web = new WebHandler();
			sax.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("com/http/server/demo/web.xml"),web);
			
			context=new ServletContext();
			
			List<Mapping> mappings=(List<Mapping>) web.getMappingList();
			for(Mapping map:mappings){
				String servletName=map.getName();
				for(String servletValue:map.getMappingList()){
					context.getMapping().put(servletValue, servletName);
				}	
			}
			
			List<Entity> entitys=web.getEntityList();
			for(Entity e:entitys){
				context.getServlet().put(e.getName(), e.getClazz());
			}
			
//			Map<String,String>mapping=context.getMapping();
//			mapping.put("/login", "login");
//			mapping.put("/log", "login");
//			mapping.put("/reg", "register");
//			
//			Map<String,String> servlet=context.getServlet();
//			servlet.put("login", "com.http.server.demo.LoginServlet"); 
//			servlet.put("register", "com.http.server.demo.RegisterServlet"); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
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
