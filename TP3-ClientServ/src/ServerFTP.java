import java.io.*;
import java.net.*;

@SuppressWarnings("unused")
public class ServerFTP {
	
	static ServerSocket listenSocket;
	static Socket clientSocket;
	static BufferedReader in;
	static PrintStream out;
	
	private static int SERVER_PORT;
	
	private void GET_FILE() {
		System.out.println("Get File command");
	}
	
	private void PUT_FILE() {
		System.out.println("Put file command");
	}
	
	private void LS_DIR() {
		System.out.println("List command");
	}
	
	public static void main(String[] args) throws Exception, IOException {
		if(args.length == 1) {
			try {
				SERVER_PORT = Integer.parseInt(args[1]);
			} catch (Exception ex) {
				System.err.println("Error while parsing the host address or port: " + ex );
				ex.printStackTrace();
				System.exit(-1);
			}
		} else {
			System.out.println("Please check the specified arguments, then retry.");
			System.exit(0);
		}
		
		try {
			System.out.println("---- SERVER PROCESS ----");
			boolean endFlag = true;
			
			// Open socket
			System.out.println("Creating listen/client sockets");
			listenSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Socket creation successful.");
			
			//Listen for commands from Client
			in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
			out = new PrintStream(clientSocket.getOutputStream());
			String inputString = "";
			
			while (!inputString.equals("STOP") && !inputString.equals("STOP SERVER")) {
				switch(inputString) {
					case "GET":
						
						break;
					case "PUT":
						
				}
			}
		} catch (Exception e) {
			System.err.println("Erreur : " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
