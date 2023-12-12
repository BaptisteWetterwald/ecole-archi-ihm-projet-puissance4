package ensisa.connect4;

import ensisa.connect4.controller.GameController;
import ensisa.connect4.model.Game;
import ensisa.connect4.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameApp extends Application {

    public static final String NAME = "Connect 4-tiche";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Game game = new Game();
        GameView view = new GameView();
        new GameController(game, view);
        Scene scene = new Scene(view, Screen.getPrimary().getBounds().getWidth() * 0.6, Screen.getPrimary().getBounds().getWidth() * 0.6 * 9 / 16);
        stage.setTitle(NAME);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.centerOnScreen();
        stage.show();
    }
}
