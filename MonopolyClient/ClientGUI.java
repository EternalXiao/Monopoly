package MonopolyClient;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		Text ip = new Text("Server IP:");
		Text port = new Text("Port:");
		Text prompt = new Text();
		TextField ipField = new TextField();
		TextField portField = new TextField();
		Button connect = new Button("Connect");
		Button exit = new Button("Exit");
		connect.setOnAction(e -> {
			if (isNumeric(portField.getText())) {
				if (client.connect(ipField.getText(), Integer.parseInt(portField.getText()))) {
					this.loginPage();
				} else {
					prompt.setText("Incorrect serverIP or port");
					prompt.setFill(Color.RED);
				}
			} else {
				prompt.setText("Port should be numeric");
				prompt.setFill(Color.RED);
			}
		});
		exit.setOnAction(e -> {
			System.exit(1);
		});

		root.add(prompt, 0, 0, 2, 1);
		root.add(ip, 0, 1);
		root.add(port, 0, 2);
		root.add(ipField, 1, 1);
		root.add(portField, 1, 2);
		root.add(connect, 1, 3);
		root.add(exit, 1, 4);
		this.stage.setScene(new Scene(root));
	}

	public void loginPage() {
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		Button login = new Button("Login");
		Button signUp = new Button("Sign Up");
		Button exit = new Button("Exit");
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		Text usernameText = new Text("Username:");
		Text passwordText = new Text("Password:");
		Text prompt = new Text("hi");
		login.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			client.login(username, password);
		});
		signUp.setOnAction(e -> {
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
		exit.setOnAction(e -> {
			this.close();
			System.exit(1);
		});
		root.add(prompt, 0, 0, 2, 1);
		root.add(usernameText, 0, 1);
		root.add(passwordText, 0, 2);
		root.add(usernameField, 1, 1);
		root.add(passwordField, 1, 2);
		root.add(login, 1, 3);
		root.add(signUp, 1, 4);
		root.add(exit, 1, 5);
		this.stage.setScene(new Scene(root));
	}

	public void mainPage() {
		Button signOut = new Button("Sign out");
		Label title = new Label("Main Page");
		title.setLayoutX(300);
		title.setLayoutY(100);
		title.setStyle("-fx-font-size:50px");
		signOut.setOnAction(e -> {
			loginPage();
		});
		Group root = new Group(signOut, title);
		stage.setScene(new Scene(root));
	}

	public void loginFailed() {
		Text loginFail = (Text) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		loginFail.setText("Incorrect username or password");
		loginFail.setFill(Color.RED);
	}

	public void signUpFail() {
		Text signUpFail = (Text) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		signUpFail.setText("Username has been used");
		signUpFail.setFill(Color.RED);
	}

	public void nickName() {

	}

	public void close() {
		this.client.close();
	}

	public boolean isNumeric(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Node findElement(int row, int col, GridPane root) {
		Node result = null;
		for (Node node : root.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				result = node;
				break;
			}
		}
		return result;
	}
}
