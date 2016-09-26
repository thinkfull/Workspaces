package create.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberServer {
	public static final int PORT = 10600;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket =  new ServerSocket(PORT);
		System.out.println("Started:"+ serverSocket);
		
		try {
			Socket socket = serverSocket.accept();
			
			try {
				System.out.println("Server Socket:" + socket);
//				BufferedReader in = new BufferedReader(
//					new InputStreamReader(socket.getInputStream())
//				);
				
				InputStream in = socket.getInputStream();
				byte b[] = new byte[4000];
				String  st = new String(b, "utf-8");
				String  st2 = new String(b, "8859_1");
				String  st3 = new String(b, "gb2312");
				 System.out.println(st);
				 System.out.println(st2);
				 System.out.println(st3);
				PrintWriter out = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										socket.getOutputStream())), true);
				out.println("11111111||");
//				while (true) {
//					String str = in.readLine();
//					System.out.println(str);
//					if ("END".equals(str)) {
//						break;
//					}
//					out.println(str);
//				}
			} finally {
				socket.close();
			}
		} finally {
			serverSocket.close();
		}
	}

}
