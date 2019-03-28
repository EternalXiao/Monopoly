package MonopolyClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import gui.*;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * This is the main class to run the client
 */
public class ClientApplication extends Application {
	/**
	 * This method will be executed once the user launch the client.
	 * It will load server properties and show login page to the user
	 */
	@Override
	public void start(Stage stage) throws Exception {
		String ip = "";
		int port = 0;
		try (FileInputStream in = new FileInputStream(new File("src/MonopolyClient/server.properties"))) { //./server.properties
			Properties pro = new Properties();
			pro.load(in);
			ip = pro.getProperty("ip");
			port = Integer.parseInt(pro.getProperty("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainClient client = new MainClient(ip, port);
		ClientStage gui = new ClientStage(client);
		client.setGUI(gui);
		gui.setLoginPage();
	}
	/**
	 * This is the main method for user to launch
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
