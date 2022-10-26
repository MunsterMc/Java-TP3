import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.rmi.ServerRuntimeException;

public class ServerFTP {
	
	static ServerSocket listenSocket;
	static Socket clientSocket;
	static BufferedReader in;
	static PrintStream out;
	
	private static int SERVER_PORT;
	private static String SERVER_DIR;
	private static String CLIENT_DIR;
	
	public static void main(String[] args) throws Exception, IOException {
		if(args.length >= 1) {
			try {
				SERVER_PORT = Integer.parseInt(args[0]);
				if(args.length == 2) {
					SERVER_DIR = args[1];
				} else {
					SERVER_DIR = "/Downloads";
				}
			} catch (Exception ex) {
				System.err.println("Error while parsing the port or path: " + ex );
				ex.printStackTrace();
				System.exit(-1);
			}
		} else {
			System.out.println("Please check the specified arguments, then retry.");
			System.exit(0);
		}
		
		try {
			System.out.println("---- SERVER PROCESS START ----");
			
			// Open socket
			System.out.println("Creating listen/client sockets");
			listenSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Socket creation successful.");
			
			System.out.println("Awaiting connection, server ready.");
			Socket service = listenSocket.accept();
			//Listen for commands from Client
			BufferedReader bf = new BufferedReader(new InputStreamReader(service.getInputStream()));
			CLIENT_DIR = bf.readLine();
			System.out.println("Client Dir obtained: "+ CLIENT_DIR);


			String cmd;
			Path origin, dest;
			String[] cmd_args;
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(service.getOutputStream()));
			do {
				cmd = bf.readLine();
				cmd_args = cmd.split(" ");
				switch(cmd_args[0]){
					case "GET":
						pw.println("GET FILE");
						origin = Paths.get(SERVER_DIR.concat("\\").concat(cmd_args[1]));
						dest = Paths.get(CLIENT_DIR.concat("\\").concat(cmd_args[1]));
						Files.move(origin, dest, StandardCopyOption.REPLACE_EXISTING);
						break;
					case "PUT":
						pw.println("PUT FILE");
						origin = Paths.get(CLIENT_DIR.concat("\\").concat(cmd_args[1]));
						dest = Paths.get(SERVER_DIR.concat("\\").concat(cmd_args[1]));
						Files.move(origin, dest, StandardCopyOption.REPLACE_EXISTING);
						break;
					case "LS":
						pw.println("LIST FILES");
						File folder = new File(SERVER_DIR);
						File[] listOfFiles = folder.listFiles();
						
						for (File file : listOfFiles) {
							if (file.isFile()) {
								pw.println(file.getName());
							}
						}
						break;
					case "EXIT":
						System.out.println("Connection terminated by user.");
						break;
					default :
						pw.println("Invalid command.");
				}
			} while (!cmd.equals("EXIT"));
			pw.close();
			bf.close();
			service.close();
		} catch (Exception e) {
			System.err.println("Erreur : " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
