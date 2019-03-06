package MonopolyClient;

import MonopolyServer.game.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI {
	private Stage stage;
	private MainClient client;
	private GridPane mainRoot;
	private Button rollButton;
	public Button getRollButton() {
		return this.rollButton;
	}

	public final int[] xAxis = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

	public final int[] yAxis = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public final int CELL_WIDTH = 65;
	public final int[] chessXAxis = { 0, 1 };
	public final int[] chessYAxis = { 0, 0 };

	public final int CELL_HIGHET = 65;
	public final String[] CELL_NAME = { "Go.jpg", "Old Kent Road.jpg", "Chance1.jpg", "Whitechapel.jpg",
			"Income Tax.jpg", "King's Cross Station.jpg", "The Angel Islington.jpg", "Chance2.jpg", "Euston Road.jpg",
			"Pentonville.jpg", "Jail.jpg", "Pall Mall.jpg", "Electric Company.jpg", "Whitehall.jpg",
			"Northumberland.jpg", "Marylebone Station.jpg", "Bow Street.jpg", "Chance3.jpg", "Marlborough Street.jpg",
			"Vine Street.jpg", "Free Parking.jpg", "The Strand.jpg", "Chance4.jpg", "Fleet Street.jpg",
			"Trafalgar Square.jpg", "Fenchurch st Station.jpg", "Leicester Square.jpg", "Coventry Street.jpg",
			"Water Works.jpg", "Piccadilly.jpg", "Go To Jail.jpg", "Regent Street.jpg", "Oxford Street.jpg",
			"Chance5.jpg", "Bond Street.jpg", "Liverpool Street Station.jpg", "Chance6.jpg", "Park Lane.jpg",
			"Super Tax.jpg", "Mayfair.jpg" };
	/**
	 * 设置棋盘大小为11*11
	 */
	private String[][] gameDesk = new String[11][11];

	/**
	 * 图片层大小11*11
	 */
	private ImageView[] imageViews = new ImageView[40];

	/**
	 * 储存着长方形对象的array(棋盘格子)大小为11*11
	 *
	 */
	private Rectangle[][] squares = new Rectangle[11][11];
	private ImageView[] playerChess = new ImageView[6];
	/**
	 * 存储棋子和房子
	 */
	private GridPane[] chess = new GridPane[40];
	/**
	 * 创建两个长方形存放骰子object
	 */
	private Rectangle diceLeft;
	private Rectangle diceRight;

	public Rectangle getDiceLeft() {
		return this.diceLeft;
	}

	public Rectangle getDiceRight() {
		return this.diceRight;
	}

	/**
	 * 设置当前两个骰子的值
	 */
	public int leftDiceValue = 0;
	public int rightDiceValue = 0;
	/**
	 * 存放6张骰子的照片
	 */
	private ImagePattern[] dice = new ImagePattern[7];
	private final String[] diceOrder = { "0", "1", "2", "3", "4", "5", "6" };
	/**
	 * 用于存放默认骰子的照片
	 */
	private final ImagePattern diceDefault = new ImagePattern(new Image("file:src/image/default.jpg"));
	private final String[] chessName = {"towerBlack","queenWhite"};
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
		BorderPane borderPane = new BorderPane();
		GridPane root = new GridPane();
		this.mainRoot = root;
		
		drawCell(root);
		drawDice(root);
		drawChessPlace(root);
		HBox bottomGameDesk;
		bottomGameDesk = drawBottomGameDesk();

		borderPane.setCenter(root);
		borderPane.setBottom(bottomGameDesk);
		stage.setResizable(false);
		Scene scene = new Scene(borderPane, 800, 1000);
		this.stage.setScene(scene);
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
		Label nickNameFail = (Label) this.findElement(0, 0, (GridPane) this.stage.getScene().getRoot());
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

	public void initializeGameDesk() {
		for (int i = 0; i < 40; i++)
			gameDesk[xAxis[i]][yAxis[i]] = "";
	}

	public void setDiceValue(int leftV, int rightV) {
		leftDiceValue = leftV;
		rightDiceValue = rightV;
	}

	public void drawSingleCell(GridPane root, int i) {
		Image cellImage = new Image("file:src/image/" + CELL_NAME[i]);
		System.out.println(cellImage.getHeight() + " " + cellImage.getWidth());

		ImageView tempImage = new ImageView(cellImage);
		if (((xAxis[i] == 0) && (yAxis[i] == 0)) || ((xAxis[i] == 10) && (yAxis[i] == 10))
				|| ((xAxis[i] == 10) && (yAxis[i] == 0)) || ((xAxis[i] == 0) && (yAxis[i] == 10))) {
			tempImage.setFitWidth(106);
			tempImage.setFitHeight(106);
		} else {
			if ((yAxis[i] == 0) || (yAxis[i] == 10)) {
				tempImage.setFitWidth(65);
				tempImage.setFitHeight(106);
			} else {
				tempImage.setFitWidth(106);
				tempImage.setFitHeight(65);
			}
		}

		imageViews[i] = tempImage;

		root.add(imageViews[i], xAxis[i], yAxis[i]);
	}

	public void drawDice(GridPane root) {
		diceLeft = new Rectangle(65, 65);
		diceRight = new Rectangle(65, 65);

		diceLeft.setFill(diceDefault);
		diceRight.setFill(diceDefault);

		root.add(diceLeft, 4, 5);
		root.add(diceRight, 6, 5);
		for (int i = 1; i < 7; i++) {
			dice[i] = new ImagePattern(new Image("file:src/image/" + diceOrder[i] + ".png"));
		}
	}

	public synchronized void toggleDice(Rectangle currentDice, int number) {
		currentDice.setFill(dice[number]);
	}

	public HBox drawBottomGameDesk() {
		HBox tempHBox = new HBox(100);
		Button readyButton = new Button("Ready");
		rollButton = new Button("Roll");
		Button updateButton = new Button("Update");
		Button buyButton = new Button("Buy");
		Button sellButton = new Button("Sell");
		rollButton.setDisable(true);
		tempHBox.getChildren().addAll(readyButton, rollButton, updateButton, buyButton, sellButton);
		tempHBox.setAlignment(Pos.TOP_CENTER);
		tempHBox.setPrefSize(800, 200);

		rollButton.setOnAction(e -> {
			client.send("RollDice");
			rollButton.setDisable(true);
		});
		readyButton.setOnAction(e->{
			client.send("Ready 1");
		});
		return tempHBox;

	}

	public void drawCell(GridPane root) {

		for (int i = 0; i < 40; i++)
			drawSingleCell(root, i);
	}

	public void loadChess() {
		for (int i = 0; i < client.getPlayers().size(); i++) {
			this.playerChess[i] = new ImageView(new Image("file:src/image/" + chessName[i] + ".gif"));
			this.playerChess[i].setFitHeight(20);
			this.playerChess[i].setFitWidth(20);
		}
	}

	public void drawChessPlace(GridPane root) {
		for (int i = 0; i < 40; i++) {
			chess[i] = new GridPane();
			root.add(this.chess[i], xAxis[i], yAxis[i]);
			this.chess[i].setAlignment(Pos.CENTER);
		}
	}

	public void updateAllPlayer() {
		for (int i=0;i<this.client.getPlayers().size();i++) {
			this.chess[this.client.getPlayers().get(i).getPreviousPosition()].getChildren().remove(this.playerChess[i]);
			this.chess[this.client.getPlayers().get(i).getCurrentPosition()].add(this.playerChess[i], chessXAxis[i], chessYAxis[i]);
		}
	}
	public void updatePlayer(int i) {
		this.chess[this.client.getPlayers().get(i).getPreviousPosition()].getChildren().remove(this.playerChess[i]);
		this.chess[this.client.getPlayers().get(i).getCurrentPosition()].add(this.playerChess[i], chessXAxis[i], chessYAxis[i]);
	}
}
