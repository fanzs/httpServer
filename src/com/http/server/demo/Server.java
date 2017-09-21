package com.http.server.demo;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public final static String BLANK=" ";
	public final static String CRLF="\r\n";
	
	private ServerSocket server;
	private boolean isShutDown=false;
	
	/**
	 * 创建服务器
	 */
	public void start(){
		start(7878);
	}
	
	/**
	 * 创建指定端口的服务器
	 */
	public void start(int port){
		try {
			server=new ServerSocket(port);
			this.receive();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 接收客户端
	 */
	private void receive(){
		try {
			while(!isShutDown){
				new Thread(new Dispatcher(server.accept())).start();
			}
		} catch (IOException e) {
			stop();
		}
	}
	private void stop() {
		isShutDown=true;
		CloseUtil.closeSocket(server);
	}
	/**
	 * 关闭服务器
	 */
	public void close(){
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Server server=new Server();
		server.start();
	}
}
