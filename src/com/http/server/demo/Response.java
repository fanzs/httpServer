package com.http.server.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Response {
	public final static String BLANK=" ";
	public final static String CRLF="\r\n";
	//存储头信息
	private StringBuilder headInfo;
	//响应消息内容
	private StringBuilder context;
	//消息响应字符长度
	private int len;
	//
	private BufferedWriter bw;
	/**
	 * 构造函数
	 */
	public Response() {
		headInfo=new StringBuilder();
		context=new StringBuilder();
		len=0;
	}
	/**
	 * 构造函数
	 * @param 输出流 os
	 */
	public Response(OutputStream os) {
		this();
		bw=new BufferedWriter(new OutputStreamWriter(os));
	}
	/**
	 * 添加响应语句
	 * @param info
	 * @return
	 */
	public Response print(String info){
		if(info==null || info.equals("")){
			return null;
		}
		context.append(info);
		len+=info.getBytes().length;
		return this;
	}
	/**
	 * 添加带回车的响应语句
	 * @param info
	 * @return
	 */
	public Response println(String info){
		if(info==null || info.equals("")){
			return null;
		}
		context.append(info).append(CRLF);
		len+=info.getBytes().length+CRLF.getBytes().length;
		return this;
	}
	
	/**
	 * 创建HTTP协议头，非常重要，一个字都不能错
	 * @param code
	 */
	public void createHeadInfo(int code){
		//1) HTTP 协议版本，状态代码，描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch(code){
		case 200:headInfo.append("OK");break;
		case 404:headInfo.append("NOT FOUND");break;
		case 505:headInfo.append("SERVER ERROR");break;
		}
		headInfo.append(CRLF);
		//2) 响应头(Response Head)
		headInfo.append("server:fzs Server0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
		//正文长度，字节长度
		headInfo.append("Content-Length:").append(len).append(CRLF);
		//3) 正文之前加空行
		headInfo.append(CRLF);
	}
	/**
	 * 
	 * @param code
	 * @throws IOException
	 */
	public void pushToClient(int code) throws IOException{
		if(headInfo==null || bw==null){
			code=500;
		}
		//创建头信息
		createHeadInfo(code);
		//输出头信息
		bw.append(headInfo);
		//输出正文
		bw.append(context);
		//推送
		bw.flush();
	}
	/**
	 * 关闭输出流
	 */
	public void close(){
		CloseUtil.closeIO(bw);
	}
}
