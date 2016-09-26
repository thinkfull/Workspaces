package create.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberMultiServer {
	public static final int PORT = 9080;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("ServerSocket :" + serverSocket);
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Server socket:" + socket);
				new Thread(new JabberServerOne(socket)).start();
			}
		} finally {
			serverSocket.close();
		}
	}

}
