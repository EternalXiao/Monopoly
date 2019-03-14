package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class MapBlockAlert {

    Scene scene;
    static Button upgradeButton = new Button("Upgrade");
    static Button degradationButton = new Button("Degradation");
    static Rectangle infomationImage = new Rectangle(400,400);

    public MapBlockAlert(String name){
        HBox layout = new HBox();

        Label title = new Label("Block Infomation:" + name);
        Rectangle informationImage = new Rectangle(400,400);
        VBox informationList = new VBox(5);
        informationList.getChildren().addAll(title,informationImage);

        VBox hButton = new VBox(20);
        hButton.getChildren().addAll(upgradeButton,degradationButton);

        informationList.setAlignment(Pos.CENTER);
        hButton.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(informationList,hButton);
        scene = new Scene(layout,600,500);

    }
}
