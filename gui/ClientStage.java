package gui;

import MonopolyClient.MainClient;
import MonopolyClient.game.Block;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class use to switch the scene
 */
public class ClientStage extends Stage {

	public static final String IMAGEURL = "file:src/image/";// image/

	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private MainGameDesk mainGameDesk;
	private static MainClient client;

	public ClientStage(MainClient client) {
		this.client = client;

		this.setTitle("Monopoly");
		this.setResizable(false);
		this.setLoginPage();
		this.show();
		this.setOnCloseRequest(e -> {
			if (client.getInContact())
				client.send("Exit");
			this.close();
		});

	}

	/**
	 * get the login page
	 * @return login page
	 */
	public LoginPage getLoginPage() {
		return this.loginPage;
	}

	/**
	 * get the SignUpPage
	 * @return SignUpPage
	 */
	public SignUpPage getSignUpPage() {
		return this.signUpPage;
	}

	/**
	 * get the MainGameDesk
	 * @return MainGameDesk
	 */
	public MainGameDesk getMainGameDesk() {
		return this.mainGameDesk;
	}

	/**
	 * set the login page
	 */
	public void setLoginPage() {
		loginPage = new LoginPage(client, this);
		this.setScene(loginPage.getScene());
	}

	/**
	 * set the SignUpPage
	 */
	public void setSignUpPage() {
		this.signUpPage = new SignUpPage(client, this);
		this.setScene(this.signUpPage.getScene());
	}

	/**
	 * set the MainGameDesk
	 */
	public void setGameDeskPage() {
		mainGameDesk = new MainGameDesk(client);
		this.setResizable(false);
		this.setScene(mainGameDesk.scene);
	}

	/**
	 * set the MapBlockAlert pop-up window
	 * @param pos the location of the specified block
	 */
	public static void setMapBlockAlert(int pos) {
		Stage alertStage = new Stage();

		alertStage.initModality(Modality.APPLICATION_MODAL);
		alertStage.setTitle(MainGameDesk.CELL_INFO[pos]);
		MapBlockAlert mapBlockAlert = new MapBlockAlert(pos, client);
		alertStage.setScene(mapBlockAlert.scene);
		alertStage.showAndWait();

	}

	/**
	 * set the WinAlert pop-up window
	 */
	public void setWinAlert() {
		Stage winAlertStage = new Stage();
		winAlertStage.setTitle("Congratulations!*");
		winAlertStage.initModality(Modality.APPLICATION_MODAL);
		WinAlert winAlert = new WinAlert(client,winAlertStage);
		winAlertStage.setScene(winAlert.scene);
		winAlertStage.showAndWait();
		
	}

	/**
	 * set the LoseServer pop-up window
	 */
	public static void setLoseServer() {
		Stage loseServerStage = new Stage();
		loseServerStage.setTitle("Error");
		loseServerStage.initModality(Modality.APPLICATION_MODAL);
		LoseServerAlert loseServerAlert = new LoseServerAlert();
		loseServerStage.setScene(loseServerAlert.scene);
		loseServerStage.showAndWait();
	}

	public static Node findElement(int col, int row, GridPane root) {
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
