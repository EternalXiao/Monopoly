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

public class SignUpPage {
	private Label login = new Label("Back to login");
	private Button signUp = new Button("Sign Up");
	private Label prompt = new Label();
	private Button exit = new Button("Exit");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private TextField nicknameField = new TextField();
	private Text usernameText = new Text("Username:");
	private Text passwordText = new Text("Password:");
	private Text nicknameText = new Text("Nickname:");
	private Image signUpLogo = new Image(ClientStage.IMAGEURL + "monopoly.png");
	private ImageView signUpLogoView = new ImageView(signUpLogo);
	private GridPane gridPane = new GridPane();
	private MainClient client;
	private ClientStage clientStage;
	private Scene scene;

	/**
	 * build the scene of the SignUpPage
	 * 
	 * @param client      the client object of the program
	 * @param clientStage main stage of the game
	 */
	public SignUpPage(MainClient client, ClientStage clientStage) {
		this.client = client;
		this.clientStage = clientStage;
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		this.login.setUnderline(true);
		GridPane.setHalignment(prompt, HPos.CENTER);
		GridPane.setHalignment(login, HPos.LEFT);
		this.signUpLogoView.setFitWidth(400);
		this.signUpLogoView.setPreserveRatio(true);
		this.prompt.setPrefHeight(20);

		this.login.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			this.clientStage.setLoginPage();
		});
		this.signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
					|| nicknameField.getText().isEmpty()) {
				this.prompt.setText("All field must not be empty");
				this.prompt.setTextFill(Color.RED);
			} else if (usernameField.getText().length() > 20 || passwordField.getText().length() > 20
					|| nicknameField.getText().length() > 20) {
				this.prompt.setText("All field must not be less than 20 characters");
				this.prompt.setTextFill(Color.RED);
				this.usernameField.clear();
				this.passwordField.clear();
				this.nicknameField.clear();
			} else if (!usernameField.getText().matches("[a-zA-z0-9]+")
					|| !passwordField.getText().matches("[a-zA-Z0-9]+")
					|| !nicknameField.getText().matches("[a-zA-Z0-9]+")) {
				this.prompt.setText("Only number and alphabetic characters are acceptable");
				this.prompt.setTextFill(Color.RED);
				this.usernameField.clear();
				this.passwordField.clear();
				this.nicknameField.clear();
			} else {
				this.client.signUp(usernameField.getText(), passwordField.getText(), nicknameField.getText());
				this.usernameField.clear();
				this.passwordField.clear();
				this.nicknameField.clear();
			}
		});
		this.signUp.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
						|| nicknameField.getText().isEmpty()) {
					this.prompt.setText("All field must not be empty");
					this.prompt.setTextFill(Color.RED);
				} else if (usernameField.getText().length() > 20 || passwordField.getText().length() > 20
						|| nicknameField.getText().length() > 20) {
					this.prompt.setText("All field must not be less than 20 characters");
					this.prompt.setTextFill(Color.RED);
					this.usernameField.clear();
					this.passwordField.clear();
					this.nicknameField.clear();
				} else if (!usernameField.getText().matches("[a-zA-Z0-9]+")
						|| !passwordField.getText().matches("[a-zA-Z0-9]+")
						|| !nicknameField.getText().matches("[a-zA-Z0-9]+")) {
					this.prompt.setText("Only number and alphabetic characters are acceptable");
					this.prompt.setTextFill(Color.RED);
					this.usernameField.clear();
					this.passwordField.clear();
					this.nicknameField.clear();
				} else {
					this.client.signUp(usernameField.getText(), passwordField.getText(), nicknameField.getText());
					this.usernameField.clear();
					this.passwordField.clear();
					this.nicknameField.clear();
				}
			}
		});
		this.exit.setOnAction(e -> {
			System.exit(1);
		});
		gridPane.add(login, 0, 0, 2, 1);
		gridPane.add(signUpLogoView, 0, 1, 2, 1);
		this.gridPane.add(prompt, 0, 2, 2, 1);
		this.gridPane.add(usernameText, 0, 3);
		this.gridPane.add(usernameField, 1, 3);
		this.gridPane.add(passwordText, 0, 4);
		this.gridPane.add(passwordField, 1, 4);
		this.gridPane.add(nicknameText, 0, 5);
		this.gridPane.add(nicknameField, 1, 5);
		this.gridPane.add(signUp, 0, 6);
		this.gridPane.add(exit, 1, 6);

		this.scene = new Scene(this.gridPane);

		signUp.getStyleClass().add("button-SignUp");
		usernameText.getStyleClass().add("text-general");
		passwordText.getStyleClass().add("text-general");
		nicknameText.getStyleClass().add("text-general");

		usernameField.getStyleClass().add("text-field");
		nicknameField.getStyleClass().add("text-field");
		login.getStyleClass().add("label-back");
		scene.getStylesheets().add("gui/SignUpPage.css");

	}

	public Scene getScene() {
		return this.scene;
	}

	/**
	 * inform the player that the account has been signed up
	 */
	public void signUpSuccess() {
		this.prompt.setText("Account created");
		this.prompt.setTextFill(Color.GREEN);
	}

	/**
	 * inform the player that Username has been used
	 */
	public void usernameUsed() {
		this.prompt.setText("Username has been used");
		this.prompt.setTextFill(Color.RED);
	}

	/**
	 * inform the player that Nickname has been used
	 */
	public void nicknameUsed() {
		this.prompt.setText("Nickname has been used");
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
