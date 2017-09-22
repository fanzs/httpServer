package com.http.server.demo;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
	@SafeVarargs
	public static <T extends Closeable> void closeIO(T... io){
		for(Closeable temp:io){
			try {
				if(null!=temp){
					temp.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SafeVarargs
	public static <T extends Closeable> void closeSocket(T... sockets){
		for(Closeable socket:sockets){
			try {
				if(null!=socket){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
