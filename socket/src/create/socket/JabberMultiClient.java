package create.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class JabberMultiClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		InetAddress ip = InetAddress.getByName(null);
		Socket socket = null;
		for (int i = 0; i < 50; i++) {
			 socket = new Socket(ip, JabberMultiServer.PORT);
			 System.out.println(socket);
			try {
				new Thread(new JabberMultiClient.ThreadClient(socket,i)).start();
				Thread.currentThread().sleep(1000);
			} finally {
			//	socket.close();
			}
		}
	}
	
	static class ThreadClient implements Runnable{
		private BufferedReader in;
		private PrintWriter out;
		private Socket socket;
		private int clientNo;
		public ThreadClient(Socket socket,int clientNo) throws IOException {
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			
			this.clientNo = clientNo;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					String str = " Hello " + clientNo +":" +i;
					out.println(str);
					try {
						System.out.println(in.readLine());
					} catch (IOException e) {
					}
				}
				out.println("END");
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
}
