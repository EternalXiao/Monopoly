package MonopolyServer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * This is the main class to run the server
 */
public class Main {
	/**
	 * This is the main method to launch the server
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 0;
		try (FileInputStream in = new FileInputStream(new File("src/MonopolyServer/server.properties"))) { //./server.properties
			Properties pro = new Properties();
			pro.load(in);
			port = Integer.parseInt(pro.getProperty("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainServer MS = new MainServer(port);
		MS.build();
		MS.listenConnection();
	}
}
