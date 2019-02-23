package MonopolyClient;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI {
	private Stage stage;
	private MainClient client;

	public ClientGUI(Stage stage, MainClient client) {
		this.stage = stage;
		this.client = client;
	}

	public void initPage() {

		Text ip = new Text("Server IP");
		Text port = new Text("Port");
		TextField ipField = new TextField();
		TextField portField = new TextField();
		Button connect = new Button("Connect");
		ip.setLayoutX(450);
		ip.setLayoutY(300);
		port.setLayoutX(450);
		port.setLayoutY(350);
		ipField.setLayoutX(500);
		ipField.setLayoutY(300);
		portField.setLayoutX(500);
		portField.setLayoutY(350);
		connect.setLayoutX(600);
		connect.setLayoutY(450);
		connect.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (client.connect(ipField.getText(), Integer.parseInt(portField.getText()))) {
				this.loginPage();
			} else {

			}
		});
		Group root = new Group(ip, port, ipField, portField, connect);
		this.stage.setScene(new Scene(root));
	}

	public void loginPage() {
		Group root = new Group();
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
		// usernameText.setStyle("-fx-font-size:50px");
		passwordText.setX(450);
		passwordText.setY(340);
		prompt.setLayoutX(500);
		prompt.setLayoutY(250);
		prompt.setVisible(false);
		login.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			client.login(username, password);
		});
		signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			if (username.length() < 6 || username.length() > 15) {
				prompt.setText("The length of username should be greater than 5 and less than 16");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			} else if (password.length() < 8 || password.length() > 15) {
				prompt.setText("The length of password should be greater than 7 and less than 16");
				prompt.setFill(Color.RED);
				prompt.setVisible(true);
			} else {
				client.signUp(username, password);
			}
		});
		root.getChildren().add(login);
		root.getChildren().add(signUp);
		root.getChildren().add(usernameField);
		root.getChildren().add(passwordField);
		root.getChildren().add(usernameText);
		root.getChildren().add(passwordText);
		root.getChildren().add(prompt);
		this.stage.setScene(new Scene(root));
	}

	public void mainPage() {
		Group root = new Group();
		stage.setScene(new Scene(root));
	}

	public void loginFailed() {
		Button back = new Button("Login again");
		back.setLayoutX(600);
		back.setLayoutY(400);
		back.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			this.loginPage();
		});
		Group root = new Group(back);
		this.stage.setScene(new Scene(root));
	}
	public void signUpFail() {
		
	}
	
	public void nickName() {
		
	}
}
