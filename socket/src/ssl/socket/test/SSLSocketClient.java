package ssl.socket.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class SSLSocketClient {
	static int port = 8266;
	
	// 客户端没有证书的话，服务端将报错: javax.net.ssl.SSLHandshakeException: Received fatal alert: certificate_unknown'
	
	public static void main(String args[]) {
		
		  //执行客户端方法一：
		  System.setProperty("javax.net.ssl.trustStore",System.getProperty("user.dir")+"\\bin\\SSLKey"); 
		  
		  //方法二：
		  //把证书拷贝到java home/lib/security目录下，名字改为jssecacerts，然后可以直接执行客户端：
		  
		  Socket s = null;
		try {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();

			 s = factory.createSocket("localhost", port);

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("I LOVE YOU ! 谢谢");
			BufferedReader	in = new BufferedReader(new InputStreamReader(
					s.getInputStream(), "UTF-8"));
			
			System.out.println( in.readLine());
			
			in.close();
			out.close();
			
		} catch (Exception e) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
