package gui;

import java.util.LinkedList;

import MonopolyClient.MainClient;
import MonopolyClient.game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class GuiTest extends Application{
        private MainClient client;

        @Override
        public void start(Stage stage) throws Exception {
            MainClient mainClient = new MainClient();
//            LinkedList<Player> p = mainClient.getPlayers();
//            p = new LinkedList<>();
//            mainClient.getPlayers().add(new Player(0,"blue"));
//            MainGameDesk mainGameDesk = new MainGameDesk(mainClient);
//            Stage stageTest = new Stage();
//            stageTest.setScene(mainGameDesk.scene);
//            stageTest.show();
//            ClientStage clientStage = new ClientStage(mainClient);
//            clientStage.setMapBlockAlert("qjj");
            TilePane tilePane = new TilePane();
            tilePane.getChildren().add(0,new ImageView(new Image(ClientStage.IMAGEURL + "piece1.png")));
            tilePane.getChildren().add(1,new ImageView(new Image(ClientStage.IMAGEURL + "piece3.png")));
            //tilePane.getChildren().add(1,new ImageView(new Image(ClientStage.IMAGEURL + "piece2.png")));
            Scene scene = new Scene(tilePane);
            stage.setScene(scene);
            stage.show();

        }
    public static void main(String[] args) {
        launch(args);
    }
}
