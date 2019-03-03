package MonopolyServer.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainPage extends Application{
	private Circle p1,p2;
	private final int P1WIDTH=720;
	private final int HEIGHT=730;
	private final int P2WIDTH=740;
	private final int STEP=62;
	private Game game;
	@Override
	public void start(Stage stage) throws Exception {
		initBoard(stage);
		//game = new Game(this);
//		new Thread(()->{
//			while(true) {
//				game.nextRound();
//				//Platform.runLater(()->update());
//			}
//		} ).start();  
		
	}
	public void initBoard(Stage stage) {
		BorderPane border = new BorderPane();
		HBox header = new HBox();
		HBox foot = new HBox();
		VBox left = new VBox();
		VBox right = new VBox();
		Group root = new Group();
		p1 = new Circle(720,730,10);
		p2 = new Circle(740,730,10);
		p2.setFill(Color.RED);
		header.setPrefHeight(50);
		foot.setPrefHeight(50);
		left.setPrefWidth(100);
		right.setPrefWidth(200);
		border.setCenter(root);
		border.setTop(header);
		border.setBottom(foot);
		border.setLeft(left);
		border.setRight(right);
		Image board = new Image("file:src/image/towerBlack.gif");
		ImageView boardView = new ImageView(board);
		root.getChildren().addAll(boardView,p1,p2);
		stage.setScene(new Scene(border,1100,900));
		stage.show();
	}
	public void update() {
		switch(game.getCurrentPlayer()) {
		case 0:
			int position = game.getPlayers().get(0).getCurrentPosition();
			if(position<=10) {
				p1.setCenterX(P1WIDTH-STEP*position);
				p1.setCenterY(HEIGHT);
			}
			else if(position<=20) {
				p1.setCenterX(P1WIDTH-10*STEP);
				p1.setCenterY(HEIGHT-(position-10)*STEP);
			}
			else if(position<=30) {
				p1.setCenterX(P1WIDTH-(30-position)*STEP);
				p1.setCenterY(HEIGHT-10*STEP);
			}
			else {
				p1.setCenterX(P1WIDTH);
				p1.setCenterY(HEIGHT-(40-position)*STEP);
			}
			break;
		case 1:
			int position2 = game.getPlayers().get(1).getCurrentPosition();
			if(position2<=10) {
				p2.setCenterX(P2WIDTH-STEP*position2);
				p2.setCenterY(HEIGHT);
			}
			else if(position2<=20) {
				p2.setCenterX(P2WIDTH-10*STEP);
				p2.setCenterY(HEIGHT-(position2-10)*STEP);
			}
			else if(position2<=30) {
				p2.setCenterX(P2WIDTH-(30-position2)*STEP);
				p2.setCenterY(HEIGHT-10*STEP);
			}
			else {
				p2.setCenterX(P2WIDTH);
				p2.setCenterY(HEIGHT-(40-position2)*STEP);
			}
			break;
		}
	}
	public static void main(String... args) {
		launch(args);
	}
	
}
