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
		Text prompt = new Text();
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
		prompt.setLayoutX(500);
		prompt.setLayoutY(250);
		prompt.setVisible(false);
		login.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
			String username = usernameField.getText();
			String password = passwordField.getText();
			if(client.login(username, password))
				mainPage();
			else {
				prompt.setText("Incorrect username or password");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			}
		});
		signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
			String username = usernameField.getText();
			String password = passwordField.getText();
			if(username.length()<6 || username.length()>15) {
				prompt.setText("The length of username should be greater than 5 and less than 16");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			}
			else if(password.length()<8 || password.length()>15){
				prompt.setText("The length of password should be greater than 7 and less than 16");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			}
			else if(client.signUp(username, password)) {
				prompt.setText("Account create successful!");
				prompt.setFill(Color.GREEN);
				prompt.setVisible(true);
			}
			else {
				prompt.setText("Username has been used");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			}
		});
		root.getChildren().add(login);
		root.getChildren().add(signUp);
		root.getChildren().add(usernameField);
		root.getChildren().add(passwordField);
		root.getChildren().add(usernameText);
		root.getChildren().add(passwordText);
		root.getChildren().add(prompt);
	}
	public void mainPage() {
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
