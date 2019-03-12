package gui;

import MonopolyClient.MainClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientStage extends Stage {

	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private MainGameDesk mainGameDesk;
	private MainClient client;

	public ClientStage(MainClient client) {
		this.client = client;

		this.setTitle("Monopoly");
		this.setResizable(false);
		this.setLoginPage();
		this.show();
		this.setOnCloseRequest(e->{
			client.send("Exit");
			this.close();
		});
	}
	public LoginPage getLoginPage() {
		return this.loginPage;
	}
	public SignUpPage getSignUpPage() {
		return this.signUpPage;
	}
	public MainGameDesk getMainGameDesk() {
		return this.mainGameDesk;
	}
	public void setLoginPage() {
		loginPage = new LoginPage(client, this);
		this.setScene(loginPage.getScene());
	}

	public void setSignUpPage() {
		this.signUpPage = new SignUpPage(client, this);
		this.setScene(this.signUpPage.getScene());	
	}

	public void setGameDeskPage() {
		mainGameDesk = new MainGameDesk(client);
		this.setResizable(false);
		this.setScene(mainGameDesk.scene);
	}

	/***********************************************************************************************************************/
	/** Used by current class **/
	/***********************************************************************************************************************/
	public static Node findElement(int row, int col, GridPane root) {
		Node result = null;
		for (Node node : root.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				result = node;
				break;
			}
		}
		return result;
	}

	/***********************************************************************************************************************/
	/** Used by MainClient class **/
	/***********************************************************************************************************************/

}
