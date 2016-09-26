package create.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class JabberServerOne implements Runnable {
	private Socket socket ;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public JabberServerOne(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}


	@Override
	public void run() {
		try{	
		   while(true){
				
				try {
					String str = in.readLine();
					if ("END".equals(str)) {
						break;
					}
					out.println(str);
					
				} catch (IOException e) {}
			}
		}finally {
					try {
						socket.close();
					} catch (IOException e) {}
		         }
				
	}

}
