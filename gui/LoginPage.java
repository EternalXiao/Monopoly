package gui;

import MonopolyClient.MainClient;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage {
	private Button login = new Button("Login");
	private Label signUp = new Label("Sign Up");
	private Label prompt = new Label();
	private Button exit = new Button("Exit");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private Text usernameText = new Text("Username:");
	private Text passwordText = new Text("Password:");
	private Image loginLogo = new Image("file:src/image/monopoly.png");
	private ImageView loginLogoView = new ImageView(loginLogo);
	private GridPane gridPane;
	private Scene scene;
	private MainClient client;
	private ClientStage clientStage;

	public LoginPage(MainClient client, ClientStage clientStage) {
		this.client = client;
		this.clientStage = clientStage;
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(100);
		gridPane.setVgap(10);
		prompt.setPrefHeight(20);
		prompt.setWrapText(true);
		loginLogoView.setFitWidth(400);
		loginLogoView.setPreserveRatio(true);
		usernameText.setTranslateX(50);
		passwordText.setTranslateX(50);
		usernameField.setMaxWidth(200);
		usernameField.setTranslateX(10);
		passwordField.setMaxWidth(200);
		passwordField.setTranslateX(10);
		login.setTranslateX(50);
		GridPane.setHalignment(loginLogoView, HPos.CENTER);
		signUp.setUnderline(true);
		GridPane.setHalignment(signUp, HPos.RIGHT);
		GridPane.setHalignment(exit, HPos.CENTER);

		gridPane.add(loginLogoView, 0, 1, 2, 1);
		gridPane.add(prompt, 0, 2, 2, 1);
		gridPane.add(usernameText, 0, 3);
		gridPane.add(passwordText, 0, 4);
		gridPane.add(usernameField, 1, 3);
		gridPane.add(passwordField, 1, 4);
		gridPane.add(login, 0, 5);
		gridPane.add(signUp, 1, 0);
		gridPane.add(exit, 1, 5);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		login.setOnAction(e -> {
			if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				this.prompt.setText("Please enter username and password");
				this.prompt.setTextFill(Color.RED);
				this.passwordField.clear();
			}
			else {
				String username = usernameField.getText();
				String password = passwordField.getText();
				client.login(username, password);
				this.passwordField.clear();
			}
		});
		login.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
					this.prompt.setText("Please enter username and password");
					this.prompt.setTextFill(Color.RED);
					this.passwordField.clear();
				}
				else {
					String username = usernameField.getText();
					String password = passwordField.getText();
					client.login(username, password);
					this.passwordField.clear();
				}
			}
		});
		this.signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			this.clientStage.setSignUpPage();
		});
		this.signUp.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			this.signUp.setStyle("-fx-font-weight: bold");
		});
		this.signUp.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			this.signUp.setStyle("-fx-font-weight:regular");
		});
		exit.setOnAction(e -> {
			System.exit(1);
		});
		scene = new Scene(gridPane);
	}

	public Scene getScene() {
		return this.scene;
	}

	public void loginFailed() {
		this.prompt.setText("Incorrect username or password");
		this.prompt.setTextFill(Color.RED);
	}
	public void accountOnline() {
		this.prompt.setText("Account is already online");
		this.prompt.setTextFill(Color.RED);
	}
}
