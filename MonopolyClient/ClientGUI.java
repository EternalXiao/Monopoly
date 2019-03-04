package MonopolyClient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		Text ip = new Text("Server IP:");
		Text port = new Text("Port:");
		Label prompt = new Label();
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
					prompt.setTextFill(Color.RED);
				}
			} else {
				prompt.setText("Port should be numeric");
				prompt.setTextFill(Color.RED);
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
		Label prompt = new Label();
		prompt.setMinHeight(35);
		login.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			client.login(username, password);
		});
		signUp.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			if (username.length() < 6 || username.length() > 15) {
				prompt.setText("The length of username should be\ngreater than 5 and less than 16");
				prompt.setTextFill(Color.RED);
			} else if (password.length() < 8 || password.length() > 15) {
				prompt.setText("The length of password should be\ngreater than 7 and less than 16");
				prompt.setTextFill(Color.RED);
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
		Label loginFail = (Label) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		loginFail.setText("Incorrect username or password");
		loginFail.setTextFill(Color.RED);
	}

	public void signUpFail() {
		Label signUpFail = (Label) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		signUpFail.setText("Username has been used");
		signUpFail.setTextFill(Color.RED);
	}

	public void signUpSuccess() {
		Label signUpSuccess = (Label) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		signUpSuccess.setText("Account created");
		signUpSuccess.setTextFill(Color.GREEN);
	}

	public void nickName() {
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));
		Label prompt = new Label();
		Label nickName = new Label("Please choose your nickname");
		TextField nickNameField = new TextField();
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			client.send("NickName " + nickNameField.getText());
		});
		root.add(prompt, 0, 0, 2, 1);
		root.add(nickName, 0, 1);
		root.add(nickNameField, 1, 1);
		root.add(submit, 1, 2);
		this.stage.setScene(new Scene(root));
	}
	public void nickNameFail() {
		Label nickNameFail = (Label)this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
		nickNameFail.setText("Nickname has already been used");
		nickNameFail.setTextFill(Color.RED);
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
