package ssl.socket.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


// reference :  http://blog.csdn.net/yangdelong/article/details/4575983
public class SSLSocketServer {

	// keytool -genkey -keystore SSLKey -keyalg rsa -alias SSL

	static int port = 8266;
	static SSLServerSocket server;

	/*
	 * 构造函数
	 */

	public SSLSocketServer() {

	}

	/*
	 * @param port 监听的端口号
	 * 
	 * @return 返回一个SSLServerSocket对象
	 */

	private static SSLServerSocket getServerSocket(int thePort) {
		SSLServerSocket s = null;
		try {
			String key = "SSLKey"; // 要使用的证书名

			char keyStorePass[] = "12345678".toCharArray(); // 证书密码

			char keyPassword[] = "12345678".toCharArray(); // 证书别称所使用的主要密码

			KeyStore ks = KeyStore.getInstance("JKS"); // 创建JKS密钥库
			
			// 创建管理JKS密钥库的X.509密钥管理器
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
						
			System.out.println( ks.getType()  + "================");

			ks.load( SSLSocketServer.class.getClassLoader().getResourceAsStream(key), keyStorePass);

			kmf.init(ks, keyPassword);

			SSLContext sslContext = SSLContext.getInstance("SSLv3");

			sslContext.init(kmf.getKeyManagers(), null, null);

			// 根据上面配置的SSL上下文来产生SSLServerSocketFactory,与通常的产生方法不同
			SSLServerSocketFactory factory = sslContext
					.getServerSocketFactory();

			s = (SSLServerSocket) factory.createServerSocket(thePort);

		} catch (Exception e) {
			System.out.println(e);
		}
		return (s);
	}
	
	
	public static void main(String[] args) {
		
		try {
			server = getServerSocket(port);
			System.out.println("server starting ..." + server);

			while (true) {
				SSLSocket socket = (SSLSocket) server.accept();

				// 将得到的socket交给CreateThread对象处理,主线程继续监听
				new CreateThread(socket).start();

			}
		} catch (Exception e) {
			System.out.println("main方法错误80:" + e);
		}
	}
	
	  /*
	    *内部类,获得主线程的socket连接,生成子线程来处理
	    */
	    
	   public static class CreateThread extends Thread
	    {
		     BufferedReader in;
		     PrintWriter out;
		     Socket s;
	    
		/*
		 * 构造函数,获得socket连接,初始化in和out对象
		 */

		public CreateThread(Socket socket) {
			try {
				s = socket;
				in = new BufferedReader(new InputStreamReader(
						s.getInputStream(), "UTF-8"));

				out = new PrintWriter(s.getOutputStream(), true);


			} catch (Exception e) {
				System.out.println(e);
			}

		}

		/*
		 * 线程方法,处理socket传递过来的数据
		 */

		public void run() {
			try {
				String msg = in.readLine();
				System.out.println(msg );
				
				out.write(msg + " too !");
				
				out.close();
				in.close();
				
			} catch (Exception e) {
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(e);
			}
		}
	}
}
