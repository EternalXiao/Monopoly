package gui;
import MonopolyClient.MainClient;
import MonopolyClient.game.Player;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;
public class WinAlert {
    Scene scene;
    private MainClient client;
    static Button exitButton = new Button("Exit");
    static Button playAgainButton = new Button ("Play Again!");

    /**
     * build winAlert pop-up window
     * @param client the client object of the program
     */
    public WinAlert(MainClient client, Stage winAlertStage){
        this.client = client;
        LinkedList<Label> playerInformation = new LinkedList<>();
        ImageView congras = new ImageView(new Image(ClientStage.IMAGEURL + "Congratulations.jpg"));
        congras.setSmooth(true);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10,10,10,10));

        LinkedList<Player> rank = new LinkedList<>(client.getPlayers());
        rank.sort(Comparator.comparing(Player::getMoney).reversed());

        gridPane.add(congras,0,0);
        for (int i = 0 ; i<rank.size(); i++){
            String string = String.format("%d: \t %s \t\t\t\t\t\t\t\t Player Money:%d",i + 1,rank.get(i).getName(),rank.get(i).getMoney());
            Label label = new Label(string);
            gridPane.add(label,0, i + 1);
            playerInformation.add(label);
        }
        gridPane.add(playAgainButton,0,rank.size()+ 1);
        gridPane.add(exitButton,0,rank.size() +1);
        GridPane.setHalignment(playAgainButton,HPos.LEFT);
        GridPane.setHalignment(exitButton, HPos.RIGHT);

        scene = new Scene(gridPane,500,500);

        scene.getStylesheets().add("gui/MainGameDesk.css");
        playAgainButton.getStyleClass().add("button-ready");
        exitButton.getStyleClass().add("button-endRound");
        

        playAgainButton.setOnAction(event -> {
        	client.reset();
        	MainGameDesk.initBoard();
        	MainGameDesk.displayPlayersInformation();
        	MainGameDesk.getReadyButton().setDisable(false);
            winAlertStage.close();
        });
        exitButton.setOnAction(event -> {
            System.exit(1);
        });
        for (Label t: playerInformation)
            t.getStyleClass().add("label-rank");


    }

}
