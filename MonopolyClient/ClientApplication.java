package MonopolyClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import gui.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		String ip = "";
		int port = 0;
		try (FileInputStream in = new FileInputStream(new File("/Users/QWEJKLJKL/Dropbox/group16/MonopolyClient/server.properties"))) {
			Properties pro = new Properties();
			pro.load(in);
			ip = pro.getProperty("ip");
			port = Integer.parseInt(pro.getProperty("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// client = new MainClient();
		MainClient client = new MainClient(ip, port);
		ClientStage gui = new ClientStage(client);
		client.setGUI(gui);
		gui.setLoginPage();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
