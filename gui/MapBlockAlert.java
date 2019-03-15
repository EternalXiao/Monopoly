package gui;

import MonopolyClient.MainClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;



public class MapBlockAlert {

    Scene scene;
    static Button upgradeButton = new Button("Upgrade");
    static Button degradeButton = new Button("Degrade");
    static Rectangle infomationImage = new Rectangle(400,400);


    public MapBlockAlert(int pos,MainClient client){
    	String name = MainGameDesk.CELL_INFO[pos];
        HBox layout = new HBox();

        ImageView cellInfo = new ImageView(new Image(ClientStage.IMAGEURL + name + " info.PNG"));
        cellInfo.setFitWidth(400);
        cellInfo.setFitHeight(500);
        Label title = new Label("Block Infomation:" + name);
//        Rectangle informationImage = new Rectangle(400,400);
        VBox informationList = new VBox(5);
        informationList.getChildren().addAll(title,cellInfo);
        informationList.setPadding(new Insets(10,10,10,10));

        VBox hButton = new VBox(20);
        hButton.getChildren().addAll(upgradeButton,degradeButton);


        upgradeButton.setTranslateX(50);
        degradeButton.setTranslateX(50);
        informationList.setAlignment(Pos.CENTER);
        hButton.setAlignment(Pos.BOTTOM_RIGHT);
        hButton.setPadding(new Insets(0,0,20,0));
        if(client.getFreeAction()) {
        	upgradeButton.setDisable(false);
        	degradeButton.setDisable(false);
        }
        else {
        	upgradeButton.setDisable(true);
        	degradeButton.setDisable(true);
        }
        upgradeButton.setOnAction(e->{
        	client.send("Build "+ pos);
        });
        degradeButton.setOnAction(e->{
        	client.send("Sell "+pos);
        });



        layout.getChildren().addAll(informationList,hButton);
        scene = new Scene(layout,550,550);
        scene.getStylesheets().add("gui/MapBlockAlert.css");
        upgradeButton.getStyleClass().add("button-upgradeButton");
        degradeButton.getStyleClass().add("button-degradeButton");
    }
}
