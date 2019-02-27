package MonopolyClient;

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

public class ClientApplication extends Application{
	private static final int HEIGHT = 720;
	private static final int WIDTH = 1280;
	private static final String SERVERIP = "127.0.0.1";
	private static final int PORT = 8888;
	private MainClient client;
	@Override
	public void start(Stage stage) throws Exception {
		//client = new MainClient();
		client = new MainClient(SERVERIP,PORT);
		ClientGUI gui = new ClientGUI(stage,client);
		client.setGUI(gui);
		//gui.initPage();
		gui.loginPage();
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.setTitle("Monopoly");
		stage.setResizable(false);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
