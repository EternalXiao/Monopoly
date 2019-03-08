package gui;

import MonopolyClient.MainClient;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginPage {
    //设置页面图标
    private Button login = new Button("Login");
    private Button signUp = new Button("Sign Up");
    private Button exit = new Button("Exit");
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Text usernameText = new Text("Username:");
    private Text passwordText = new Text("Password:");

    private Scene scene;
    private MainClient client;

    public LoginPage(MainClient client) {
        this.client = client;
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Label prompt = new Label();
        prompt.setMinHeight(35);

        gridPane.add(prompt, 0, 0, 2, 1);
        gridPane.add(usernameText, 0, 1);
        gridPane.add(passwordText, 0, 2);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(login, 1, 3);
        gridPane.add(signUp, 1, 4);
        gridPane.add(exit, 1, 5);

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
            System.exit(1);
        });
        scene = new Scene(gridPane);
    }
    public Scene getScene() {
    	return this.scene;
    }
}
