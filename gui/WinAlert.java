package gui;
import MonopolyClient.MainClient;
import MonopolyClient.game.Player;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.util.LinkedList;
import java.util.stream.Collectors;
public class WinAlert {
    Scene scene;
    private MainClient client;
    static Button exitButton = new Button("exit");




    public WinAlert(MainClient client){
        this.client = client;
        LinkedList<Label> playerInformation = new LinkedList();
        ImageView congras = new ImageView(new Image(ClientStage.IMAGEURL + "Congratulations.jpg"));
        congras.setSmooth(true);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10,10,10,10));

        LinkedList<Player> rank = client.getPlayers().stream()
                                                    .sorted((p1,p2) -> Integer.compare(p1.getMoney(),p2.getMoney()))
                                                    .collect(Collectors.toCollection(LinkedList::new));

        gridPane.add(congras,0,0);
        for (int i = 0 ; i<rank.size(); i++){
            String string = String.format("%d: \t %s \t\t\t\t\t\t\t\t Player Money:%d",i + 1,client.getPlayers().get(i).getName(),client.getPlayers().get(i).getMoney());
            Label label = new Label(string);
            gridPane.add(label,0, i + 1);
            playerInformation.add(label);
        }

        gridPane.add(exitButton,0,rank.size() +1);
        gridPane.setHalignment(exitButton, HPos.RIGHT);

        scene = new Scene(gridPane,500,500);

        scene.getStylesheets().add("gui/MainGameDesk.css");
        exitButton.getStyleClass().add("button-endRound");

        exitButton.setOnAction(event -> {
            System.exit(1);
        });
        for (Label t: playerInformation)
            t.getStyleClass().add("label-rank");


    }

}
