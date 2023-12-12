package ensisa.connect4.view;

import ensisa.connect4.controller.GameController;
import ensisa.connect4.model.Game;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameView extends Pane {

    private Game game;
    private GameController controller;
    private BoardPane boardPane;

    public GameView() {
        super();
        setBackground(Background.fill(Color.PURPLE));
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void updateBoard() {
        boardPane.update();
    }

    public void updateBoard(int row, int column) {
        boardPane.update(row, column);
    }

    public void tryPlacement(int column) {
        controller.play(column);
    }

    public void printBoard() {
        for (int i = 0; i < game.getNbRows(); i++) {
            for (int j = 0; j < game.getNbColumns(); j++) {
                System.out.print(game.getToken(i, j) + " ");
            }
            System.out.println();
        }
    }

    public void setController(GameController gameController) {
        this.controller = gameController;
    }

    public void setupBoard() {
        boardPane = new BoardPane(game, this);
        getChildren().add(boardPane);
    }
}
