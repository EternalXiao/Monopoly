package gui;

import MonopolyClient.MainClient;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainGameDesk {
    /**
     * Test only
     */
    static final int[] money = {9090,12312,435345,56456,123,45645};
    static final String[] name = {"Saber","Rumble","Zed","Jason","Java","Cancer"};
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
	public static final int[] informationY = {0,0,1,1,2,2};
    public static final int[] informationX = {0,1,0,1,0,1};
	public static final int[] chessXAxis = { 0,1,2,0,1,2 };
	public static final int[] chessYAxis = { 1,1,1,2,2,2 };

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
	private static final String[] chessName = { "piece1", "piece2","piece3",
            "piece4","piece5","piece6"};

	/**
	 * 设置骰子的值
	 */
	public static void setDiceValue(int leftV, int rightV) {
		leftDiceValue = leftV;
		rightDiceValue = rightV;
		toggleDice(diceLeft,leftV);
		toggleDice(diceRight,rightV);
		
	}

	private static TextArea informationList;
    private static Label systemMessage;
    private static MainClient client = null;
    private static GridPane playerInformationPane;
    private static Circle[] state = new Circle[6];
    private static BorderPane mainPane;
	Scene scene;
	/**
	 * 底部按钮区
	 */
	private static Button readyButton;
	private static Button rollButton;
	private static Button buyButton;
	private static Button sellButton;
	private static Button endButton;

	/**
	 * 初始化MainGameDesk
	 * 
	 * @param client
	 */
	public MainGameDesk(MainClient client) {
		this.client = client;
		mainPane = new BorderPane();
		GridPane root = new GridPane();
		drawCell(root);
		drawDice(root);

		drawChessPlace(root);

		mainPane.setCenter(root);
		displayPlayersInformation();
        displayChatBox();
        displaySystemMessage();

		scene = new Scene(mainPane, 1400, 850);
	}

	/**
	 * 画棋盘的每一个格子
	 * 
	 * @param root
	 * @param i
	 */
	public static void drawSingleCell(GridPane root, int i) {
		Image cellImage = new Image("file:src/image/" + CELL_NAME[i]);

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


	public static void drawCell(GridPane root) {
		for (int i = 0; i < 40; i++)
			drawSingleCell(root, i);
	}
	/**
     * 制作单个玩家信息表
     * @param gridPane
     * @param number
     */
    public static void drawSinglePlayerInformation(GridPane gridPane,int number){
        HBox hBox = new HBox(10);
        VBox vBox = new VBox(10);

        Image tempImage = (new Image("file:src/image/" + chessName[number] + ".png"));
        
        Circle cir = new Circle(20);
        cir.setFill(new ImagePattern(tempImage));
//        Text nickName = new Text(client.getPlayers().get(number).getName());
//        Text currentMoney = new Text("£" + client.getPlayers().get(number).getMoney());
        state[number] = new Circle(5);
        Text nickName = new Text(client.getPlayers().get(number).getName());
        Text currentMoney = new Text("£ " + client.getPlayers().get(number).getMoney());
        if (!client.getPlayers().get(number).isAlive())
            state[number].setFill(Color.RED);
        else if (client.getPlayers().get(number).isReady()) {
            state[number].setFill(Color.GREEN);
        } else {
            state[number].setFill(Color.YELLOW);
        }
       

        vBox.getChildren().addAll(nickName,currentMoney);
        hBox.getChildren().addAll(cir,vBox,state[number]);

        hBox.setPadding(new Insets(5,5,5,5));
        hBox.setPrefSize(150,225);
        gridPane.add(hBox,informationX[number],informationY[number]);
    }
    /**
     * 制作玩家信息表
     */
    public static void displayPlayersInformation(){
        /**
         * 使用GridPane存储显示玩家信息
         */
        playerInformationPane = new GridPane();
        playerInformationPane.setVgap(5);
        playerInformationPane.setHgap(5);
        playerInformationPane.setPadding(new Insets(10,10,10,10));
        playerInformationPane.setPrefWidth(300);
        playerInformationPane.setPrefHeight(900);

        for (int i = 0; i<client.getPlayers().size(); i++){
            drawSinglePlayerInformation(playerInformationPane,i);
        }
        playerInformationPane.setGridLinesVisible(true);
        mainPane.setLeft(playerInformationPane);
    }

    /**
     * 制作系统信息提示栏
     */
    public static void displayChatBox(){
        Label title = new Label("ChatBox");
        informationList = new TextArea();
        informationList.setEditable(false);
        informationList.setWrapText(true);
        TextField outputField = new TextField();
        VBox chatBox = new VBox(10);

        informationList.setPrefHeight(600);
        outputField.setPrefHeight(50);

        outputField.setOnAction(e ->{
            StringBuffer data= new StringBuffer();
            data.append("ChatMessage "+outputField.getText());
            client.send(data.toString());
            System.out.println(data);
            outputField.clear();
        });

        /**
         * 制作按钮
         */
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0,10,10,10));
        gridPane.setVgap(25);
        gridPane.setHgap(20);
        readyButton = new Button("Ready");
        rollButton = new Button("Roll");
        buyButton = new Button("Buy");
        sellButton = new Button("Sell");
        endButton = new Button ("End round");

       gridPane.add(readyButton,0,0);
       gridPane.add(rollButton,1,0);
       gridPane.add(buyButton,0,1);
       gridPane.add(sellButton,1,1);
       gridPane.add(endButton, 2, 0);


        rollButton.setDisable(true);
        buyButton.setDisable(true);
        sellButton.setDisable(true);
        endButton.setDisable(true);

        final EventHandler<MouseEvent> clickButton = e ->{
            client.send("RollDice");
            rollButton.setDisable(true);
            displayPlayersInformation();
        };

        rollButton.addEventFilter(MouseEvent.MOUSE_CLICKED,clickButton);
        readyButton.setOnAction(e -> {
            client.send("Ready 1");

        });
        buyButton.setOnAction(e->{
        	client.send("Buy 1");
        	buyButton.setDisable(true);
        });
        endButton.setOnAction(e->{
        	client.send("EndRound");
        	endButton.setDisable(true);
        });

        chatBox.setPrefSize(300,800);
        gridPane.setAlignment(Pos.CENTER);
        chatBox.getChildren().addAll(title,informationList,outputField,gridPane);
        mainPane.setRight(chatBox);
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
			playerChess[i] = new ImageView(new Image("file:src/image/" + chessName[i] + ".png"));
			playerChess[i].setFitHeight(30);
			playerChess[i].setFitWidth(30);
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
	public static void displaySystemMessage() {
		HBox systemMessageHB = new HBox();
		systemMessageHB.setAlignment(Pos.CENTER);
		systemMessage = new Label();
		systemMessage.setPrefHeight(50);
		systemMessageHB.getChildren().add(systemMessage);
		mainPane.setTop(systemMessageHB);
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
	public static Button getEndButton() {
		return endButton;
	}
	public static TextArea getInformationList(){
        return informationList;
    }

    public static Label getSystemMessage(){
        return  systemMessage;
    }
}
