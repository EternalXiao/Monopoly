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

public class LoginPage {
	private Button login = new Button("Login");
	private Label signUp = new Label("Sign Up");
	private Label prompt = new Label();
	private Button exit = new Button("Exit");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private Text usernameText = new Text("Username:");
	private Text passwordText = new Text("Password:");
	private Image loginLogo = new Image(ClientStage.IMAGEURL + "monopoly.png");
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
		GridPane.setHalignment(prompt, HPos.CENTER);
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
			} else if (!usernameField.getText().matches("[a-zA-z0-9]+")
					|| !passwordField.getText().matches("[a-zA-z0-9]+")) {
				this.loginFailed();
			} else {
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
				} else if (!usernameField.getText().matches("[a-zA-z0-9]+")
						|| !passwordField.getText().matches("[a-zA-z0-9]+")) {
					this.loginFailed();
				} else {
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

		exit.setOnAction(e -> {
			System.exit(1);
		});
		scene = new Scene(gridPane);
		/**
		 * craft the scene
		 */
		login.getStyleClass().add("button-login");
		usernameText.getStyleClass().add("text-general");
		passwordText.getStyleClass().add("text-general");
		usernameField.getStyleClass().add("text-field");
		signUp.getStyleClass().add("label-signUp");
		scene.getStylesheets().add("gui/beautifulThing.css");
	}

	public Scene getScene() {
		return this.scene;
	}

	/**
	 * inform the player that username or password is incorrect
	 */
	public void loginFailed() {
		this.prompt.setText("Incorrect username or password");
		this.prompt.setTextFill(Color.RED);
	}

	/**
	 * inform the player that Account is already online
	 */
	public void accountOnline() {
		this.prompt.setText("Account is already online");
		this.prompt.setTextFill(Color.RED);
	}

	/**
	 * inform the player that Game is already start
	 */
	public void gameIsStart() {
		this.prompt.setText("Game is already start");
		this.prompt.setTextFill(Color.RED);
	}

	/**
	 * inform the player that Game desk is already full
	 */
	public void gameDeskFull() {
		this.prompt.setText("Game desk is already full");
		this.prompt.setTextFill(Color.RED);
	}

	/**
	 * inform the player that Server connection timeout
	 */
	public void connectionTimeout() {
		this.prompt.setText("Server connection timeout");
		this.prompt.setTextFill(Color.RED);
	}

}
