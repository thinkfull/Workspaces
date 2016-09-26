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

/**
* 1.  first time modified: this is modified on github ：update by --> git pull https://github.com/thinkfull/Workspaces.git

* 2.  second time modified: this is modified on github : git pull origin master
* 4.  这一行是在git上作的修改，测试和本地冲突的情况
**/
public class JabberClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InetAddress ip = InetAddress.getByName(null);
		Socket socket = new Socket(ip, JabberServer.PORT);
		try {
			System.out.println("Client JabberClient2 socket:" + socket);
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream())
				);
				
				PrintWriter out = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										socket.getOutputStream())), true);
				for (int i = 0; i < 10; i++) {
					out.println("Hello " + i);
					String str = in.readLine();
					System.out.println(str);
				}
				out.print("END");
		} finally {
			socket.close();
		}
	}

}
