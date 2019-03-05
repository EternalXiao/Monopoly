package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class MainGameDesk extends Application {
	


    /**
     * 棋盘从开始位置到结束位置坐标 x 和 y
     */
    public static final int[] xAxis = {10,9,8,7,6,5,4,3,2,1,0,
                                    0,0,0,0,0,0,0,0,0,0,
                                    1,2,3,4,5,6,7,8,9,10,
                                    10,10,10,10,10,10,10,10,10,10};

    public static final int[] yAxis = {10,10,10,10,10,10,10,10,10,10,10,
                                    9,8,7,6,5,4,3,2,1,0,
                                    0,0,0,0,0,0,0,0,0,0,
                                    1,2,3,4,5,6,7,8,9};

    public static final int CELL_WIDTH = 65;

    public static final int CELL_HIGHET = 65;
    public static final String[] CELL_NAME = {
            "Go.jpg",
            "Old Kent Road.jpg",
            "Chance1.jpg",
            "Whitechapel.jpg",
            "Income Tax.jpg",
            "King's Cross Station.jpg",
            "The Angel Islington.jpg",
            "Chance2.jpg",
            "Euston Road.jpg",
            "Pentonville.jpg",
            "Jail.jpg",
            "Pall Mall.jpg",
            "Electric Company.jpg",
            "Whitehall.jpg",
            "Northumberland.jpg",
            "Marylebone Station.jpg",
            "Bow Street.jpg",
            "Chance3.jpg",
            "Marlborough Street.jpg",
            "Vine Street.jpg",
            "Free Parking.jpg",
            "The Strand.jpg",
            "Chance4.jpg",
            "Fleet Street.jpg",
            "Trafalgar Square.jpg",
            "Fenchurch st Station.jpg",
            "Leicester Square.jpg",
            "Coventry Street.jpg",
            "Water Works.jpg",
            "Piccadilly.jpg",
            "Go To Jail.jpg",
            "Regent Street.jpg",
            "Oxford Street.jpg",
            "Chance5.jpg",
            "Bond Street.jpg",
            "Liverpool Street Station.jpg",
            "Chance6.jpg",
            "Park Lane.jpg",
            "Super Tax.jpg",
            "Mayfair.jpg"
    };
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
     *存储棋子和房子
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
    private static final String[] diceOrder = {"0","1","2","3","4","5","6"};
    /**
     * 用于存放默认骰子的照片
     */
    private static final ImagePattern diceDefault = new ImagePattern(new Image(
            "file:./Dice/default.jpg"
    ));
    public static void initializeGameDesk(){
        for(int i = 0; i < 40; i++)
            gameDesk[xAxis[i]][yAxis[i]] = "";
    }
    /**
     * 设置骰子的值
     */
    public static void setDiceValue(int leftV, int rightV){
        leftDiceValue = leftV;
        rightDiceValue = rightV;
    }
    /**
     * 画棋盘的每一个格子
     * @param root
     * @param i
     */
    public static void drawSingleCell(GridPane root,int i){
        Image cellImage = new Image("file:./cropImage/" + CELL_NAME[i]);
        System.out.println(cellImage.getHeight() +" " + cellImage.getWidth() );

        ImageView tempImage = new ImageView(cellImage);
        if (((xAxis[i] ==0)&&(yAxis[i] == 0))||((xAxis[i] ==10)&&(yAxis[i] == 10))||
                ((xAxis[i] ==10)&&(yAxis[i] == 0))||((xAxis[i] ==0)&&(yAxis[i] == 10))){
            tempImage.setFitWidth(106);
            tempImage.setFitHeight(106);
        }else{
            if((yAxis[i] == 0) || (yAxis[i] == 10)) {
                tempImage.setFitWidth(65);
                tempImage.setFitHeight(106);
            }else {
                tempImage.setFitWidth(106);
                tempImage.setFitHeight(65);
            }
        }

        imageViews[i] = tempImage;

        root.add(imageViews[i],xAxis[i],yAxis[i]);
    }

    /**
     * 制作骰子
     */
    public static void drawDice (GridPane root){
         diceLeft = new Rectangle(65,65);
         diceRight = new Rectangle(65,65);

        diceLeft.setFill(diceDefault);
        diceRight.setFill(diceDefault);

        root.add(diceLeft,4,5);
        root.add(diceRight,6,5);
        for (int i = 1; i < 7; i ++){
            dice[i] = new ImagePattern(new Image("file:./Dice/" +
                                                   diceOrder[i] + ".png" ));
        }
    }
    /**
     * 摇动骰子
     */
    public synchronized static void toggleDice(Rectangle currentDice,int number){
        currentDice.setFill(dice[number]);
    }
    /**
     * 制作游戏盘底部
     * @return
     */
    public static HBox drawBottomGameDesk(){
       HBox tempHBox = new HBox(100);
        Button readyButton = new Button("Ready");
        Button rollButton = new Button("Roll");
        Button updateButton = new Button("Update");
        Button buyButton = new Button("Buy");
        Button sellButton = new Button("Sell");
        tempHBox.getChildren().addAll(readyButton,rollButton,updateButton,
                                        buyButton,sellButton);
        tempHBox.setAlignment(Pos.TOP_CENTER);
        tempHBox.setPrefSize(800,200);
        final EventHandler<MouseEvent> clickButton = e ->{
            setDiceValue((int)(6*Math.random() + 1),(int)(6*Math.random() + 1));
            int lValue = leftDiceValue;
            int rValue = rightDiceValue;

            toggleDice(diceLeft,lValue);
            toggleDice(diceRight,rValue);
        };

        rollButton.addEventFilter(MouseEvent.MOUSE_CLICKED,clickButton);
        return tempHBox;


    }

    public static void drawCell(GridPane root) throws InterruptedException {


        for(int i = 0; i<40; i++)
            drawSingleCell(root,i);

        /**
         * 设置棋子
         */
        Image cellImage1 = new Image("file:./cropImage/towerBlack.gif");

        Image cellImage2 = new Image("file:./cropImage/queenWhite.gif");

        GridPane testPane = new GridPane();
        testPane.setAlignment(Pos.CENTER);


        ImageView tempImage1 = new ImageView(cellImage1);
        ImageView tempImage2 = new ImageView(cellImage2);

        tempImage1.setFitHeight(20);
        tempImage1.setFitWidth(20);
        tempImage2.setFitHeight(20);
        tempImage2.setFitWidth(20);

        new Thread(()->{
            for (int j = 0; j<1; j++) {
                final int i = j;

                try {
                    Platform.runLater(()->{

                        testPane.getChildren().remove(tempImage1);
                        testPane.add(tempImage1, 0, 0);
                        testPane.getChildren().remove(tempImage2);
                        testPane.add(tempImage2, 1, 1);});
                        root.add(testPane,xAxis[i],yAxis[i]);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    @Override
    public void start(Stage stage) throws Exception{
        BorderPane borderPane = new BorderPane();
        GridPane root = new GridPane();
        //initializeGameDesk();
        drawCell(root);
        drawDice(root);

        HBox bottomGameDesk;
        bottomGameDesk = drawBottomGameDesk();

        borderPane.setCenter(root);
        borderPane.setBottom(bottomGameDesk);
        stage.setResizable(true);
        Scene scene = new Scene(borderPane, 800,1200);

        stage.setTitle("GameDesk");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * 图片大小
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

}
