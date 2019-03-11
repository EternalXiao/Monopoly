package gui;

import java.util.LinkedList;

import MonopolyClient.MainClient;
import MonopolyClient.game.Player;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiTest extends Application{
        private MainClient client;

        @Override
        public void start(Stage stage) throws Exception {
            MainClient mainClient = new MainClient();
            LinkedList<Player> p = mainClient.getPlayers();
            p = new LinkedList<>();
            mainClient.getPlayers().add(new Player(0,"blue"));
            MainGameDesk mainGameDesk = new MainGameDesk(mainClient);
            Stage stageTest = new Stage();
            stageTest.setScene(mainGameDesk.scene);
            stageTest.show();

        }
    public static void main(String[] args) {
        launch(args);
    }
}
