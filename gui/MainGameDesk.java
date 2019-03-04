package gui;

import java.util.LinkedList;
import java.util.Scanner;

import MonopolyClient.MainClient;
import MonopolyServer.game.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainGameDesk extends Application {

	public static LinkedList<Player> players;
	public static Image p = new Image("file:src/image/towerBlack.gif");
	public static ImageView iv = new ImageView(p);
	public static GridPane root = new GridPane();
	/**
	 * 棋盘从开始位置到结束位置坐标 x 和 y
	 */
	public static final int[] xAxis = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5,
			6, 7, 8, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

	public static final int[] yAxis = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

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

	public static void initializeGameDesk() {
		for (int i = 0; i < 40; i++)
			gameDesk[xAxis[i]][yAxis[i]] = "";
	}

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

		// tempImage.setX(X_COORDINATE[i]);
		// tempImage.setY(Y_COORDINATE[i]);

		imageViews[i] = tempImage;
		root.add(imageViews[i], xAxis[i], yAxis[i]);
	}

	public static void drawCell(GridPane root) throws InterruptedException {
//        Rectangle outline = new Rectangle(0,0,800,800);
//        outline.setFill(null);
//        outline.setStroke(Color.BLACK);
//        outline.setStrokeWidth(5);
//        root.getChildren().add(outline);

//        StackPane stackBlock = new StackPane();
//        root.getChildren().add(stackBlock);

		for (int i = 0; i < 40; i++)
			drawSingleCell(root, i);

//        Image cellImage1 = new Image("file:///Users/QWEJKLJKL/Dropbox/mystuff/Uni/Master/" +
//                "Software%20Workshop/Group%20Project/GroupProject/cropImage/towerBlack.gif");
//
//        Image cellImage2 = new Image("file:///Users/QWEJKLJKL/Dropbox/mystuff/Uni/Master/" +
//                "Software%20Workshop/Group%20Project/GroupProject/cropImage/queenWhite.gif");
//
//        GridPane testPane = new GridPane();
//
//        ImageView tempImage1 = new ImageView(cellImage1);
//        ImageView tempImage2 = new ImageView(cellImage2);
//
//        tempImage1.setFitHeight(20);
//        tempImage1.setFitWidth(20);
//        tempImage2.setFitHeight(20);
//        tempImage2.setFitWidth(20);
//
//        new Thread(()->{
//            for (int j = 0; j<1; j++) {
//                final int i = j;
//
//                try {
//                    Platform.runLater(()->{
//
//                        testPane.getChildren().remove(tempImage1);
//                        testPane.add(tempImage1, 0, 0);
//                        testPane.getChildren().remove(tempImage2);
//                        testPane.add(tempImage2, 1, 1);});
//                        root.add(testPane,xAxis[0],yAxis[0]);
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }).start();

	}


	public static void update() {
		Player player = players.get(0);
		final int pos;
		pos = player.getCurrentPosition();

		Platform.runLater(() -> {
			root.getChildren().remove(iv);
			root.add(iv, xAxis[pos], yAxis[pos]);
		});

	}

	@Override
	public void start(Stage stage) throws Exception {
		root = new GridPane();
		// initializeGameDesk();
		drawCell(root);

		stage.setResizable(true);
		Scene scene = new Scene(root, 800, 800);
		stage.setTitle("GameDesk");
		stage.setScene(scene);
		stage.show();

		MainClient client = new MainClient("127.0.0.1", 8888);
		Scanner in = new Scanner(System.in);
		System.out.println("Enter any to ready");
		in.nextLine();
		client.send("Ready 1");
	}

	/**
	 * 图片大小
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
