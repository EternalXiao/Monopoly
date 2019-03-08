package gui;

import MonopolyClient.MainClient;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class MainGameDesk {

	/**
	 * 棋盘从开始位置到结束位置坐标 x 和 y
	 */
	public static final int[] xAxis = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5,
			6, 7, 8, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

	public static final int[] yAxis = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	/**
	 * 棋子位置
	 */
	public static final int[] chessXAxis = { 0, 1 };
	public static final int[] chessYAxis = { 0, 0 };

	public static final int CELL_WIDTH = 65;
	public static final int CELL_HIGHET = 65;

	public static final String[] CELL_NAME = { "Go.jpg", "Old Kent Road.jpg", "Chance1.jpg", "Whitechapel.jpg",
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
	private static String[][] gameDesk = new String[11][11];

	/**
	 * 图片层大小11*11
	 */
	private static ImageView[] imageViews = new ImageView[40];

	/**
	 * 储存着长方形对象的array(棋盘格子)大小为11*11
	 *
	 */
	private static Rectangle[][] squares = new Rectangle[11][11];

	/**
	 * 存放棋子logo
	 */
	private static ImageView[] playerChess = new ImageView[6];
	/**
	 * 存储棋子和房子
	 */
	private static GridPane[] chess = new GridPane[40];
	/**
	 * 创建两个长方形存放骰子object
	 */
	private static Rectangle diceLeft;
	private static Rectangle diceRight;
	/**
	 * 设置当前两个骰子的值
	 */
	public static int leftDiceValue = 0;
	public static int rightDiceValue = 0;
	/**
	 * 存放6张骰子的照片
	 */
	private static ImagePattern[] dice = new ImagePattern[7];
	private static final String[] diceOrder = { "0", "1", "2", "3", "4", "5", "6" };
	/**
	 * 用于存放默认骰子的照片
	 */
	private static final ImagePattern diceDefault = new ImagePattern(new Image("file:src/image/default.jpg"));
	private static final String[] chessName = { "towerBlack", "queenWhite" };

	/**
	 * 设置骰子的值
	 */
	public static void setDiceValue(int leftV, int rightV) {
		leftDiceValue = leftV;
		rightDiceValue = rightV;
		toggleDice(diceLeft,leftV);
		toggleDice(diceRight,rightV);
		
	}

	/**
	 * 默认载入一个为null的client
	 */
	private static MainClient client = null;
	Scene scene;
	/**
	 * 底部按钮区
	 */
	private static Button readyButton;
	private static Button rollButton;
	private static Button buyButton;
	private static Button sellButton;

	/**
	 * 初始化MainGameDesk
	 * 
	 * @param client
	 */
	public MainGameDesk(MainClient client) {
		this.client = client;
		BorderPane borderPane = new BorderPane();
		GridPane root = new GridPane();
		// initializeGameDesk();
		drawCell(root);
		drawDice(root);

		drawChessPlace(root);
		HBox bottomGameDesk;
		bottomGameDesk = drawBottomGameDesk();

		borderPane.setCenter(root);
		borderPane.setBottom(bottomGameDesk);

		scene = new Scene(borderPane, 800, 1000);
	}

	/**
	 * 画棋盘的每一个格子
	 * 
	 * @param root
	 * @param i
	 */
	public static void drawSingleCell(GridPane root, int i) {
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

	/**
	 * 制作骰子
	 */
	public static void drawDice(GridPane root) {
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

	/**
	 * 摇动骰子
	 */
	public synchronized static void toggleDice(Rectangle currentDice, int number) {
		currentDice.setFill(dice[number]);
	}

	/**
	 * 制作游戏盘底部
	 * 
	 * @return
	 */
	public static HBox drawBottomGameDesk() {
		HBox tempHBox = new HBox(100);

		readyButton = new Button("Ready");
		rollButton = new Button("Roll");
		buyButton = new Button("Buy");
		sellButton = new Button("Sell");
		rollButton.setDisable(true);
		tempHBox.getChildren().addAll(readyButton, rollButton, buyButton, sellButton);
		tempHBox.setAlignment(Pos.TOP_CENTER);
		tempHBox.setPrefSize(800, 200);
		rollButton.setOnAction(e -> {
			client.send("RollDice");
			rollButton.setDisable(true);
		});
		readyButton.setOnAction(e -> {
			client.send("Ready 1");
		});
		return tempHBox;

	}

	public static void drawCell(GridPane root) {
		for (int i = 0; i < 40; i++)
			drawSingleCell(root, i);
	}

	/**
	 * 制作棋子摆放的位置
	 * 
	 * @param root
	 */
	public static void drawChessPlace(GridPane root) {
		for (int i = 0; i < 40; i++) {
			chess[i] = new GridPane();
			root.add(chess[i], xAxis[i], yAxis[i]);
			chess[i].setAlignment(Pos.CENTER);
		}
	}

	public static void loadChess() {
		for (int i = 0; i < client.getPlayers().size(); i++) {
			playerChess[i] = new ImageView(new Image("file:src/image/" + chessName[i] + ".gif"));
			playerChess[i].setFitHeight(20);
			playerChess[i].setFitWidth(20);
		}
	}

	public static void initialisePlayer() {
		for (int i = 0; i < client.getPlayers().size(); i++) {
			chess[client.getPlayers().get(i).getPreviousPosition()].getChildren().remove(playerChess[i]);
			chess[client.getPlayers().get(i).getCurrentPosition()].add(playerChess[i], chessXAxis[i], chessYAxis[i]);
		}
	}

	public static void updatePlayer(int i) {
		int curPos, prePos;
		prePos = client.getPlayers().get(i).getPreviousPosition();
		curPos = client.getPlayers().get(i).getCurrentPosition();
		for (int x = prePos; x != curPos; x = (x + 1) % 40) {
			final int j = x;
			Platform.runLater(() -> {
				chess[j].getChildren().remove(playerChess[i]);
				chess[(j + 1) % 40].add(playerChess[i], chessXAxis[i], chessYAxis[i]);
			});

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	public static Button getReadyButton() {
		return readyButton;
	}

	public static Button getRollButton() {
		return rollButton;
	}

	public static Button getBuyButton() {
		return buyButton;
	}

	public static Button getSellButton() {
		return sellButton;
	}

}
