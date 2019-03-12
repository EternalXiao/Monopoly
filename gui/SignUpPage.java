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
	private Image signUpLogo = new Image("file:src/image/monopoly.png");
	private ImageView signUpLogoView = new ImageView(signUpLogo);
	private GridPane gridPane = new GridPane();
	private MainClient client;
	private ClientStage clientStage;
	private Scene scene;

	public SignUpPage(MainClient client, ClientStage clientStage) {
		this.client = client;
		this.clientStage = clientStage;
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		this.login.setUnderline(true);
		GridPane.setHalignment(login, HPos.LEFT);
		this.signUpLogoView.setFitWidth(400);
		this.signUpLogoView.setPreserveRatio(true);
		this.prompt.setPrefHeight(20);

		this.login.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			this.clientStage.setLoginPage();
		});
		this.login.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			this.login.setStyle("-fx-font-weight:bold");
		});
		this.login.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			this.login.setStyle("-fx-font-weight:regular");
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
			} else
				this.client.signUp(usernameField.getText(), passwordField.getText(), nicknameField.getText());
		});
		this.signUp.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
						|| nicknameField.getText().isEmpty()) {
					this.prompt.setText("All field must not be empty");
					this.prompt.setTextFill(Color.RED);
				} else if (usernameField.getText().length() > 20 || passwordField.getText().length() > 20
						|| nicknameField.getText().length() > 20) {
					this.prompt.setText("All field must not be less than 20 characters");
					this.prompt.setTextFill(Color.RED);
				} else
					this.client.signUp(usernameField.getText(), passwordField.getText(), nicknameField.getText());
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
	}

	public Scene getScene() {
		return this.scene;
	}

	public void signUpSuccess() {
		this.prompt.setText("Account created");
		this.prompt.setTextFill(Color.GREEN);
	}

	public void usernameUsed() {
		this.prompt.setText("Username has been used");
		this.prompt.setTextFill(Color.RED);
	}

	public void nicknameUsed() {
		this.prompt.setText("Nickname has been used");
		this.prompt.setTextFill(Color.RED);
	}
}
