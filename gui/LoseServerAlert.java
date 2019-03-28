package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoseServerAlert {
    Scene scene;

    /**
     * build the scene of LoseServerAlert
     */
    public LoseServerAlert(){
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        Text text = new Text("Lost connection");
        text.setStyle("-fx-font-size: 24;-fx-font-family: Lucida Bright;-fx-fill:#FFFFFF;");
        Button exitButton = new Button("Exit the game");
        layout.getChildren().addAll(text,exitButton);

        exitButton.setTranslateX(250);

        exitButton.setOnAction(e -> {
            System.exit(1);
        });


        scene = new Scene(layout,400,100);
        exitButton.getStyleClass().add("button-endRound");
        scene.getStylesheets().add("gui/MainGameDesk.css");
    }
}
