package gui;

import MonopolyClient.MainClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiTest extends Application{
        private MainClient client;

        @Override
        public void start(Stage stage) throws Exception {
            MainClient mainClient = new MainClient();
            MainGameDesk mainGameDesk = new MainGameDesk(mainClient);
            Stage stageTest = new Stage();
            stageTest.setScene(mainGameDesk.scene);
            stageTest.show();

        }
    public static void main(String[] args) {
        launch(args);
    }
}
