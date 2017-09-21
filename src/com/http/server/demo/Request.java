package com.http.server.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
	//请求方式
	private String method;
	//请求资源路径
	private String url;
	//请求参数
	private Map<String,List<String>> parameterMapValues;
	//
	public static final String CRLF="\r\n";
	private InputStream is;
	private String requestInfo;
	
	public Request() {
		method="";
		setUrl("");
		parameterMapValues=new HashMap<String, List<String>>();
		requestInfo="";
	}
	public Request(InputStream is){
		this();
		this.is=is;
		try {
			byte[] data=new byte[20480];
			int len=this.is.read(data);
			requestInfo=new String(data,0,len);
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
		//分析请求信息
		parseRequestInfo();
	}
	/**
	 * 分析请求信息
	 */
	private void parseRequestInfo() {
		if(null==requestInfo ||(requestInfo=requestInfo.trim()).equals("")){
			return ;
		}
		/**
		 * =====================================================================
		 * 从信息的首行分解出：请求方式，请求路径，请求参数(get可能存在)
		 * 	如: Get /index.html?name=fzs&passwd123 HTTP/1.1
		 * 
		 * GET方式的参数在第一行
		 *  GET /index.html?name=fzs&passwd=123&submit=SUBMIT HTTP/1.1
		 *  
		 * POST方式的参数在最后一行
		 *  name=fzs&passwd=123&submit=SUBMIT
		 * 
		 * =====================================================================
		 */
		//请求接收参数
		String paramString="";
		
		//1.获取请求方式
		String firstLine=requestInfo.substring(0,requestInfo.indexOf(CRLF));
		int idx=requestInfo.indexOf("/");
		this.method=firstLine.substring(0,idx).trim();
		String urlStr=firstLine.substring(idx,firstLine.indexOf("HTTP/")).trim();
		if(this.method.equals("POST")){
			this.setUrl(urlStr);
			//获取POST的最后一行参数
			paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		}else if(this.method.equals("GET")){
			//判断是否接收参数
			if(urlStr.contains("?")){
				String[] urlArray=urlStr.split("\\?");
				this.setUrl(urlArray[0]);
				//接收请求参数
				paramString=urlArray[1];
			}else{
				this.setUrl(urlStr);
			}
		}
		
		//2.将请求封装到Map
		if(paramString!=null && !paramString.equals("")){
			paraseParams(paramString);
		}
		
	}
	private void paraseParams(String paramString) {
		//
		StringTokenizer token=new StringTokenizer(paramString,"&");
		while(token.hasMoreElements()){
			String keyValue = token.nextToken();
			String[] keyValues=keyValue.split("=");
			
			if(keyValues.length==1){
				keyValues=Arrays.copyOf(keyValues, 2);
				keyValues[1]=null;
			}
			
			String key=keyValues[0].trim();
			String value=keyValues[1]==null?null:decoder(keyValues[1].trim(),"gbk");
			//
			if(!parameterMapValues.containsKey(key)){
				parameterMapValues.put(key, new ArrayList<String>());
			}
			
			List<String> values=parameterMapValues.get(key);
			values.add(value);
		}
	}
	/**
	 * 根据页面名获取多个值
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name){
		List<String> values=null;
		if((values=parameterMapValues.get(name))==null){
			return null;
		}else {
			return values.toArray(new String[0]);
		}
	}
	/**
	 * 根据页面名获取单个值
	 * @param name
	 * @return
	 */
	public String getParameterValue(String name){
		List<String> values=null;
		if((values=parameterMapValues.get(name))==null){
			return null;
		}else {
			return values.get(0);
		}
	}
	
	private String decoder(String value,String charType){
		try {
			return java.net.URLDecoder.decode(value, charType);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
