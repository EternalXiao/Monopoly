package gui;

import MonopolyClient.MainClient;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;

public class MainGameDesk {

	/**
	 * x coordinate and y coordinate of the chessboard block
	 */
	public static final int[] xAxis = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5,
			6, 7, 8, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

	public static final int[] yAxis = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	/**
	 * Postion of the chess
	 */
	public static final int[] informationY = {0,0,1,1,2,2};
    public static final int[] informationX = {0,1,0,1,0,1};


	public static final int[] chessXAxisTop = {0,1,0,1,0,1};
	public static final int[] chessYAxisTop = { 0,0,1,1,2,2 };

	public static final int[] chessXAxisRight = { 1,1,2,2,3,3 };
	public static final int[] chessYAxisRight = { 0,1,0,1,0,1};

	public static final int[] chessXAxisBottom = { 0,1,0,1,0,1 };
	public static final int[] chessYAxisBottom = { 1,1,2,2,3,3 };

	public static final int[] chessXAxisLeft = { 0,0,1,1,2,2 };
	public static final int[] chessYAxisLeft = { 0,1,0,1,0,1 };


	/**
	 * location of users identifier
	 */
	public static final int userXAxisTop = 0;
	public static final int userYAxisTop = 3;

	public static final int userXAxisBottom = 0;
	public static final int userYAxisBottom = 0;

	public static final int userXAxisLeft = 3;
	public static final int userYAxisLeft = 0;

	public static final int userXAxisRight = 0;
	public static final int userYAxisRight = 0;

	/**
	 * house location on the each block
	 */
	public static final int houseXAxisTop = 1;
	public static final int houseYAxisTop = 3;

	public static final int houseXAxisBottom = 1;
	public static final int houseYAxisBottom = 0;

	public static final int houseXAxisLeft = 3;
	public static final int houseYAxisLeft = 1;

	public static final int houseXAxisRight = 0;
	public static final int houseYAxisRight = 1;


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
	 * 存放28个可购买地块的信息
	 */
	public static final String[] CELL_INFO = { "", "Old Kent Road", "", "Whitechapel Road",
			"", "King's Cross Station", "The Angel Islington", "", "Euston Road",
			"Pentonville Road", "", "Pall Mall", "Electric Company", "Whitehall",
			"Northumberland Avenue", "Marylebone Station", "Bow Street", "", "Marlborough Street",
			"Vine Street", "", "The Strand", "", "Fleet Street",
			"Trafalgar Square", "Fenchurch st Station", "Leicester Square", "Coventry Street",
			"Water Works", "Piccadilly", "", "Regent Street", "Oxford Street",
			"", "Bond Street", "Liverpool Street Station", "", "Park Lane",
			"", "Mayfair" };



	/**
	 * store block images
	 */
	private static ImageView[] imageViews = new ImageView[40];

	/**
	 * store chess images
	 */
	private static ImageView[] playerChess = new ImageView[6];

	private static GridPane[] chess = new GridPane[40];

	private static Rectangle diceLeft;
	private static Rectangle diceRight;
	/**
	 * store two dice values
	 */
	public static int leftDiceValue = 0;
	public static int rightDiceValue = 0;
	/**
	 * store the dice image for each face
	 */
	private static ImagePattern[] dice = new ImagePattern[7];
	private static final String[] diceOrder = { "0", "1", "2", "3", "4", "5", "6" };

	/**
	 * store the default image of dices
	 */
	private static ImagePattern diceDefault = new ImagePattern(new Image(ClientStage.IMAGEURL + "default.png"));
	private static final String[] chessName = { "null","piece1", "piece2","piece3",
            "piece4","piece5","piece6"};

	private static final String[] houseName = {"null.png","house1.png","house2.png","house3.png","house4.png","house5.png"};
	//private static final Rectangle[] houseLevel = new Rectangle[6];
	//private static final ImageView[] houseLevel = new ImageView[6];

	/**
	 *clickEffect
	 */
	static Rectangle[] clickEffectList = new Rectangle[40];

	/**
	 * set the corresponding image for dices
	 * @param leftV
	 * @param rightV
	 */
	public static void setDiceValue(int leftV, int rightV) {
		leftDiceValue = leftV;
		rightDiceValue = rightV;
		toggleDice(diceLeft,leftV);
		toggleDice(diceRight,rightV);

	}

	private static TextArea informationList = new TextArea();
    private static Label systemMessage;
    private static MainClient client = null;
    private static GridPane playerInformationPane;
    private static Circle[] state = new Circle[6];
    private static ImageView[] userIcon = new ImageView[7];
    private static BorderPane mainPane;
	Scene scene;
	/**
	 * set button area
	 */
	private static Button skipButton;
	private static Button readyButton;
	private static Button rollButton;
	private static Button buyButton;
	private static Button endButton;

	private static TextField outputField;
	private static Text nickName;
	private static Text currentMoney;

	/**
	 * initialise the images in different parts
	 */
	public static void initialiseImage(){

		for (int i = 0; i < 7; i++) {
			Image tempImage = (new Image(ClientStage.IMAGEURL + chessName[i] + ".png"));
			userIcon[i]  = new ImageView(tempImage);
			userIcon[i].setFitWidth(25);
			userIcon[i].setFitHeight(25);
		}


		for(int i = 0; i<40; i++){
			clickEffectList[i] = new Rectangle();
			clickEffectList[i].setStroke(null);
			clickEffectList[i].setFill(new ImagePattern(new Image(ClientStage.IMAGEURL + "null.png")));
		}
	}

	/**
	 * set clickEffect
	 * @param root
	 */
	public static void setClickEffect(GridPane root){
		for (int i =0; i < 40; i++){
			if(!CELL_INFO[i].equals("")) {
				root.add(clickEffectList[i], xAxis[i], yAxis[i]);
				final int num = i;
				clickEffectList[i].setOnMouseClicked(e -> {
					if(client.getMap() != null){
						Platform.runLater(() -> ClientStage.setMapBlockAlert(num));
					}

				});
			}
		}
	}
	/**
	 * initialise MainGameDesk
	 * 
	 * @param client
	 */
	public MainGameDesk(MainClient client) {
		MainGameDesk.client = client;
		mainPane = new BorderPane();
		initBoard();
		displayPlayersInformation();
        displayChatBox();
        displaySystemMessage();

        
		scene = new Scene(mainPane, 1450, 850);
		/**
		 * craft scene using css file
		 */
		rollButton.getStyleClass().add("button-roll");
		readyButton.getStyleClass().add("button-ready");
		endButton.getStyleClass().add("button-endRound");
		//skipButton.getStyleClass().add("");
		outputField.getStyleClass().add("text-field");
		informationList.getStyleClass().add("text-field");
		systemMessage.getStyleClass().add("label-SystemMessage");
		scene.getStylesheets().add("gui/MainGameDesk.css");
	}
	/**
	 * initialise the board
	 */
	public static void initBoard() {
		GridPane root = new GridPane();
		mainPane.setCenter(root);
		initialiseImage();
		drawCell(root);
		drawDice(root);
		drawChessPlace(root);
		setClickEffect(root);
	}

	/**
	 * draw blocks of the map
	 * 
	 * @param root
	 * @param i the order of the block
	 */
	public static void drawSingleCell(GridPane root, int i) {

		Image cellImage = new Image(ClientStage.IMAGEURL + CELL_NAME[i]);
		chess[i] = new GridPane();

		chess[i].setAlignment(Pos.CENTER);


		ImageView tempImage = new ImageView(cellImage);
		if (((xAxis[i] == 0) && (yAxis[i] == 0)) || ((xAxis[i] == 10) && (yAxis[i] == 10))
				|| ((xAxis[i] == 10) && (yAxis[i] == 0)) || ((xAxis[i] == 0) && (yAxis[i] == 10))) {
			tempImage.setFitWidth(106);
			tempImage.setFitHeight(106);
			clickEffectList[i].setWidth(106);
			clickEffectList[i].setHeight(106);

		} else {
			if ((yAxis[i] == 0) || (yAxis[i] == 10)) {
				tempImage.setFitWidth(65);
				tempImage.setFitHeight(106);

				clickEffectList[i].setWidth(65);
				clickEffectList[i].setHeight(106);
			} else {
				tempImage.setFitWidth(106);
				tempImage.setFitHeight(65);

				clickEffectList[i].setWidth(106);
				clickEffectList[i].setHeight(65);
			}
		}


		imageViews[i] = tempImage;
		root.add(chess[i], xAxis[i], yAxis[i]);
		root.add(imageViews[i], xAxis[i], yAxis[i]);


	}

	/**
	 * make the dice
	 * @param root add dice to the root
	 */
	public static void drawDice(GridPane root) {
		diceLeft = new Rectangle(65, 65);
		diceRight = new Rectangle(65, 65);

		diceLeft.setFill(diceDefault);
		diceRight.setFill(diceDefault);

		root.add(diceLeft, 4, 5);
		root.add(diceRight, 6, 5);

		for (int i = 1; i < 7; i++) {
			dice[i] = new ImagePattern(new Image(ClientStage.IMAGEURL + "" + diceOrder[i] + ".png"));
		}
	}

	/**
	 * roll dices
	 */
	public synchronized static void toggleDice(Rectangle currentDice, int number) {
		currentDice.setFill(dice[number]);
	}

	/**
	 * draw celles for each block
	 * @param root the board layout
	 */
	public static void drawCell(GridPane root) {
		for (int i = 0; i < 40; i++)
			drawSingleCell(root, i);
	}
	/**
     * make the list of every player
     * @param gridPane
     * @param number
     */
    public static void drawSinglePlayerInformation(GridPane gridPane,int number){
        HBox hBox = new HBox(10);
        VBox vBox = new VBox(10);
		StackPane stackPane = new StackPane();

        Image tempImage = (new Image(ClientStage.IMAGEURL + chessName[number + 1] + ".png"));
		/**
		 * set background of the user
		 */
		ImageView userBackgroundImage = new ImageView(new Image(ClientStage.IMAGEURL + "userBackground.jpg"));
		userBackgroundImage.setFitWidth(150);
		userBackgroundImage.setFitHeight(225);


        Circle cir = new Circle(20);
		cir.setStrokeWidth(3);
		cir.setStroke(Color.BISQUE);
        cir.setFill(new ImagePattern(tempImage));

//        Text nickName = new Text(client.getPlayers().get(number).getName());
//        Text currentMoney = new Text("£" + client.getPlayers().get(number).getMoney());
        state[number] = new Circle(5);
        nickName = new Text(client.getPlayers().get(number).getName());
        currentMoney = new Text("£ " + client.getPlayers().get(number).getMoney());

		nickName.getStyleClass().add("text-nickName");
		currentMoney.getStyleClass().add("text-money");

		/**
		 * set user status
		 */
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
		stackPane.getChildren().addAll(userBackgroundImage,hBox);
        gridPane.add(stackPane,informationX[number],informationY[number]);
    }

	/**
	 * draw playerlist
	 */
	public static void displayPlayersInformation(){

        playerInformationPane = new GridPane();
        playerInformationPane.setVgap(5);
        playerInformationPane.setHgap(5);
        playerInformationPane.setPadding(new Insets(10,10,10,10));
        playerInformationPane.setPrefWidth(350);
        playerInformationPane.setPrefHeight(900);

        for (int i = 0; i<client.getPlayers().size(); i++){
            drawSinglePlayerInformation(playerInformationPane,i);
        }
        mainPane.setLeft(playerInformationPane);
    }

	/**
	 * draw chatBox
	 */
	public static void displayChatBox(){

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10,10,10,10));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(10);

        Label title = new Label("ChatBox");

        informationList.setEditable(false);
        informationList.setWrapText(true);
        outputField = new TextField();
       // VBox chatBox = new VBox(10);

        informationList.setPrefHeight(600);
        informationList.setPrefSize(280,600);
        outputField.setPrefSize(280,50);

        outputField.setOnAction(e ->{
            StringBuffer data= new StringBuffer();
            data.append("ChatMessage "+outputField.getText());
            client.send(data.toString());
            System.out.println(data);
            outputField.clear();
        });

        /**
         * create the button area under the chatbox
         */
        readyButton = new Button("Ready");
        rollButton = new Button("Roll");
        buyButton = new Button("Buy");
        skipButton = new Button("skip");
        endButton = new Button ("End round");

        GridPane.setHalignment(readyButton,HPos.LEFT);
        GridPane.setHalignment(rollButton,HPos.LEFT);
		GridPane.setHalignment(buyButton,HPos.RIGHT);
		GridPane.setHalignment(skipButton, HPos.RIGHT);
        GridPane.setHalignment(endButton,HPos.RIGHT);
        
        skipButton.setTranslateX(-50);
//        readyButton.setTranslateX(20);


		gridPane.add(title,0,0);
		gridPane.add(informationList,0,1,2,1);
		gridPane.add(outputField,0,3,2,1);
		gridPane.add(readyButton,0,4);
		gridPane.add(skipButton, 1, 4);
		gridPane.add(rollButton,0,5);
		gridPane.add(endButton, 1, 5);
		gridPane.add(buyButton,1,4);


        rollButton.setDisable(true);
        buyButton.setDisable(true);
        endButton.setDisable(true);

        final EventHandler<MouseEvent> clickButton = e ->{
            client.send("RollDice");
            rollButton.setDisable(true);
            displayPlayersInformation();
        };

        rollButton.addEventFilter(MouseEvent.MOUSE_CLICKED,clickButton);
        readyButton.setOnAction(e -> {
        	//ClientStage.setWinAlert(client);
			//ClientStage.setLoseServer();
            client.send("Ready 1");
            readyButton.setDisable(true);
        });
        buyButton.setOnAction(e->{
        	client.send("Buy 1");
        	buyButton.setDisable(true);
        	skipButton.setDisable(true);
        });
        endButton.setOnAction(e->{
        	client.send("EndRound");
        	client.setFreeAction(false);
        	endButton.setDisable(true);
        });
        
        skipButton.setOnAction(e ->{
        	client.send("Buy 0");
        	skipButton.setDisable(true);
        	buyButton.setDisable(true);
        	
        });
        skipButton.setDisable(true);

//        chatBox.setPadding(new Insets(10,10,10,10));
//        chatBox.setPrefSize(300,800);
        //gridPane.setPrefSize(300,800);
        gridPane.setAlignment(Pos.CENTER);
        //chatBox.getChildren().addAll(title,informationList,outputField,gridPane);
        mainPane.setRight(gridPane);

		title.getStyleClass().add("label-chatbox");
    }

	/**
	 * create a Gridpane to place each chess on every block
	 * 
	 * @param root main desk
	 */
	public static void drawChessPlace(GridPane root) {
		for (int i = 0; i < 40; i++) {
			chess[i] = new GridPane();
			root.add(chess[i], xAxis[i], yAxis[i]);
			chess[i].setAlignment(Pos.CENTER);
		}
	}

	/**
	 * load the chess
	 */
	public static void loadChess() {
		for (int i = 0; i < client.getPlayers().size(); i++) {
			playerChess[i] = new ImageView(new Image(ClientStage.IMAGEURL + chessName[i + 1] + ".png"));
			playerChess[i].setFitHeight(25);
			playerChess[i].setFitWidth(25);
		}
	}

	/**
	 * load the player
	 */
	public static void initialisePlayer() {
		for (int i = 0; i < client.getPlayers().size(); i++) {
			chess[client.getPlayers().get(i).getPreviousPosition()].getChildren().remove(playerChess[i]);
			chess[client.getPlayers().get(i).getCurrentPosition()].add(playerChess[i], chessXAxisBottom[i], chessYAxisBottom[i]);
		}
	}

	/**
	 * update the player information shown in the player list
	 * @param i order of the player
	 */
	public static void updatePlayer(int i) {
		int curPos, prePos;
		prePos = client.getPlayers().get(i).getPreviousPosition();
		curPos = client.getPlayers().get(i).getCurrentPosition();
		for (int x = prePos; x != curPos; x = (x + 1) % 40) {
			final int j = x;
			Platform.runLater(() -> {
				removePlayer(i,j);

				if (((j + 1)>=0) && ((j + 1)<=10))		//bottom
					chess[(j + 1) % 40].add(playerChess[i], chessXAxisBottom[i], chessYAxisBottom[i]);

				else if (((j + 1)> 10) && ((j + 1)<20))	//left
					chess[(j + 1) % 40].add(playerChess[i], chessXAxisLeft[i], chessYAxisLeft[i]);

				else if(((j + 1)>= 20) && ((j + 1) <= 30))//top
					chess[(j + 1) % 40].add(playerChess[i], chessXAxisTop[i], chessYAxisTop[i]);
				else									//right
					chess[(j + 1) % 40].add(playerChess[i], chessXAxisRight[i], chessYAxisRight[i]);
			});

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * remove the chess of the corrsponding player
	 * @param id Id of the chess
	 * @param pos the order of the block
	 */
	public static void removePlayer(int id,int pos) {
		chess[pos].getChildren().remove(playerChess[id]);
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
	 * buy the block and set the player identifier
	 * @param currentPlayer
	 * @param position the location of the block
	 */
	public static void setBlock(int currentPlayer, int position){

		Image tempImage = (new Image(ClientStage.IMAGEURL + chessName[currentPlayer] + ".png"));
		ImageView userOwner  = new ImageView(tempImage);
		Pane pane = new Pane();
		pane.getChildren().add(userOwner);
		if (currentPlayer == 0)
			pane.setVisible(false);
		else
			pane.setVisible(true);
		pane.setStyle("-fx-border-color: #AB4642");//sold
		userOwner.setFitWidth(25);
		userOwner.setFitHeight(25);


		if (((position)>=0) && ((position)<=10)) {    //bottom
			chess[position].getChildren().remove(ClientStage.findElement(userXAxisBottom,userYAxisBottom,chess[position]));
			chess[position].add(pane,userXAxisBottom,userYAxisBottom);

		}else if (((position  )> 10) && ((position  )<20)) {    //left
			chess[position].getChildren().remove(ClientStage.findElement(userXAxisLeft,userYAxisLeft,chess[position]));
			chess[position].add(pane,userXAxisLeft,userYAxisLeft);

		}else if(((position  )>= 20) && ((position  ) <= 30)) {//top
			chess[position].getChildren().remove(ClientStage.findElement(userXAxisTop,userYAxisTop,chess[position]));
			chess[position].add(pane,userXAxisTop,userYAxisTop);
		}else {                                    //right
			chess[position].getChildren().remove(ClientStage.findElement(userXAxisRight,userYAxisRight,chess[position]));
			chess[position].add(pane,userXAxisRight,userYAxisRight);
		}

	}

	/**
	 * build/destroy house
	 */
	public static void setdHouse(int level,int position){
		Image tempImage = (new Image(ClientStage.IMAGEURL + houseName[level]));
		ImageView houseBuilder  = new ImageView(tempImage);
		houseBuilder.setFitWidth(25);
		houseBuilder.setFitHeight(25);

		if (((position)>=0) && ((position)<=10)) {        //bottom
			chess[position].getChildren().remove(ClientStage.findElement(houseXAxisBottom,houseYAxisBottom,chess[position]));
			chess[position].add(houseBuilder,houseXAxisBottom,houseYAxisBottom);

		}else if (((position  )> 10) && ((position  )<20)) {    //left
			chess[position].getChildren().remove(ClientStage.findElement(houseXAxisLeft,houseYAxisLeft,chess[position]));
			chess[position].add(houseBuilder,houseXAxisLeft,houseYAxisLeft);

		}else if(((position  )>= 20) && ((position  ) <= 30)) {//top
			chess[position].getChildren().remove(ClientStage.findElement(houseXAxisTop,houseYAxisTop,chess[position]));
			chess[position].add(houseBuilder,houseXAxisTop,houseYAxisTop);
		}else {                                    //right

			chess[position].getChildren().remove(ClientStage.findElement(houseXAxisRight,houseYAxisRight,chess[position]));
			chess[position].add(houseBuilder,houseXAxisRight,houseYAxisRight);
		}

	}


	public static Button getReadyButton() {
		return readyButton;
	}
	public static Button getRollButton() {
		return rollButton;
	}
	public static Button getBuyButton() {
		return buyButton;
	}
	public static Button getSkipButton() {
		return skipButton;
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
