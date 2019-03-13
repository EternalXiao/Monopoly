package gui;

import java.util.LinkedList;

import MonopolyClient.MainClient;
import MonopolyClient.game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GuiTest extends Application{
        private MainClient client;

        @Override
        public void start(Stage stage) throws Exception {
            GridPane gridPane = new GridPane();
            ImageView piece = new ImageView(new Image("file:/Users/QWEJKLJKL/Dropbox/group16/image/1.png"));
            ImageView background = new ImageView(new Image("file:/Users/QWEJKLJKL/Dropbox/group16/image/ClickEffect.png"));
            ImageView background1 = new ImageView(new Image("file:/Users/QWEJKLJKL/Dropbox/group16/image/ClickEffect.png"));
            Rectangle rectangle = new Rectangle(25,25);

            background1.setFitWidth(25);
            background1.setFitHeight(25);

            piece.setFitWidth(25);
            piece.setFitHeight(25);
            background.setFitHeight(25);
            background.setFitWidth(25);
            gridPane.add(piece,0,0);
            gridPane.add(background,0,0);

            gridPane.add(rectangle,1,1);
            gridPane.add(background1,1,1);


            background.setVisible(false);
            background1.setVisible(false);
            piece.setOnMouseClicked(event -> background.setVisible(true));


            rectangle.setOnMouseClicked(e -> background1.setVisible(true));

            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            stage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}
