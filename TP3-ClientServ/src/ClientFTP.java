import java.io.*;
import java.net.*;
	
public class ClientFTP {
	private static int SERVER_PORT;
	private static String ADDRESS;
	private static String CLIENT_DIR;
	static BufferedReader streamIn;
	static BufferedReader userIn;
	static PrintStream streamOut;
	
	
	public static void main(String[] args) throws Exception, IOException {
		
		if(args.length == 3) { //If port specified, else use default (default port: 8080)
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
		
		System.out.println(args[0] + args[1] + args[2]);
		
		// Port and host obtainedobtained, try to connect
		
		Socket connectionSocket = new Socket(InetAddress.getByName(ADDRESS), SERVER_PORT);
		streamIn = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream() ));
		streamOut = new PrintStream(connectionSocket.getOutputStream());
		userIn = new BufferedReader ( new InputStreamReader(System.in) );
		
		System.out.println("Connected to: " + ADDRESS + ":" + SERVER_PORT);
		
		
	}
}
