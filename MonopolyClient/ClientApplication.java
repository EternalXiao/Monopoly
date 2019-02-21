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
	private Group root;
	@Override
	public void start(Stage stage) throws Exception {
		root = new Group();
		Scene scene = new Scene(root,WIDTH,HEIGHT);
		client = new MainClient(SERVERIP,PORT);
		//client.connect();
		stage.setScene(scene);
		stage.setTitle("Monopoly");
		stage.setResizable(false);
		initPage();
		stage.show();
		
	}
	public void initPage() {
		Button login = new Button();
		Button signUp = new Button();
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		Text usernameText = new Text("Username");
		Text passwordText = new Text("Password");
		Text loginFail = new Text("Incorrect username or password");
		login.setText("Login");
		signUp.setText("Sign Up");
		login.setLayoutX(600);
		login.setLayoutY(360);
		login.setMinWidth(80);
		signUp.setLayoutX(600);
		signUp.setLayoutY(400);
		signUp.setMinWidth(80);
		usernameField.setLayoutX(550);
		usernameField.setLayoutY(280);
		passwordField.setLayoutX(550);
		passwordField.setLayoutY(320);
		usernameText.setLayoutX(450);
		usernameText.setLayoutY(300);
		//usernameText.setStyle("-fx-font-size:50px");
		passwordText.setX(450);
		passwordText.setY(340);
		loginFail.setLayoutX(500);
		loginFail.setLayoutY(250);
		loginFail.setVisible(false);
		loginFail.setFill(Color.RED);
		login.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
			String username = usernameField.getText();
			String password = passwordField.getText();
			if(client.login(username, password))
				mainPage();
			else {
				loginFail.setVisible(true);
			}
		});
		root.getChildren().add(login);
		root.getChildren().add(signUp);
		root.getChildren().add(usernameField);
		root.getChildren().add(passwordField);
		root.getChildren().add(usernameText);
		root.getChildren().add(passwordText);
		root.getChildren().add(loginFail);
	}
	public void mainPage() {
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
