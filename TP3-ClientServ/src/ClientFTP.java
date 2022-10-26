import java.io.*;
import java.net.*;
	
public class ClientFTP {
	private static int SERVER_PORT;
	private static String ADDRESS;
	private static String CLIENT_DIR;
	static BufferedReader streamIn;
	static BufferedReader userIn;
	static PrintStream streamOut;
	
	static void dispHelp(){
		System.out.println("LS: List files in server directory.");
		System.out.println("GET <filename>: Get file specified in argument.");
		System.out.println("PUT <filename>: Send file specified to server.");
	}
	public static void main(String[] args) throws Exception, IOException {
		
		if(args.length == 3) { //Check if all arguments are passed
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
		
		CLIENT_DIR = args[2];
		// Port and host obtained, trying to connect
		
		Socket connectionSocket = new Socket(InetAddress.getByName(ADDRESS), SERVER_PORT);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
		System.out.println("Connected to: " + InetAddress.getByName(ADDRESS) + ":" + SERVER_PORT);
		System.out.println("Sent working Directory to server.");
		pw.println(CLIENT_DIR);

		String cmd, rsp;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader buff_srv = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

		do { //Start getting commands from user.
			System.out.print(">>> ");
			cmd = bf.readLine();
			if(cmd.contains("HELP")){
				dispHelp();
			} else {
				pw.println(cmd);
				pw.flush();
				rsp = buff_srv.readLine();
				System.out.println(rsp);
				System.out.println("\n");
			}			
			
		} while (!cmd.equals("EXIT"));
		
		pw.close();
		bf.close();
		connectionSocket.close();
		
	}
}
