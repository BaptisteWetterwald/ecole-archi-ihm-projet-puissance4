package ensisa.connect4.view;

import ensisa.connect4.GameApp;
import ensisa.connect4.controller.GameController;
import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;
import javafx.beans.binding.Bindings;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.BLACK;

public class GameView extends Pane {

    private Game game;
    private GameController controller;
    private BoardPane boardPane;
    private EndPopup endPopup;
    private Label currentPlayerLabel;
    private CheckBox toggleAI;

    public GameView() {
        super();

        Rectangle colors = new Rectangle(this.getWidth(), this.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.REPEAT,
                        new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#c0fe56")),
                        new Stop(0.28, Color.web("#5dfbc1")),
                        new Stop(0.43, Color.web("#64c2f8")),
                        new Stop(0.57, Color.web("#be4af7")),
                        new Stop(0.71, Color.web("#ed5fc2")),
                        new Stop(0.85, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f"))
                )
        );
        colors.widthProperty().bind(this.widthProperty());
        colors.heightProperty().bind(this.heightProperty());
        getChildren().add(colors);

        Label title;
        getChildren().add(title = new Label(GameApp.NAME));
        title.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(heightProperty().multiply(0.1).get()), heightProperty()));

        title.layoutXProperty().bind(widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind(heightProperty().multiply(0.1).subtract(title.heightProperty().divide(2)));

        toggleAI = new CheckBox("Play against AI");
        toggleAI.prefHeightProperty().bind(heightProperty().multiply(0.3));
        toggleAI.prefWidthProperty().bind(widthProperty().multiply(0.3));
        toggleAI.layoutXProperty().bind(widthProperty().subtract(toggleAI.widthProperty().divide(1.5)));
        toggleAI.layoutYProperty().bind(heightProperty().multiply(0.1));
        toggleAI.setFont(new Font(30));
        toggleAI.selectedProperty().addListener((observable, oldValue, newValue) -> {
            controller.setAI(newValue);
        });
        getChildren().add(toggleAI);
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
        getChildren().add(endPopup = new EndPopup(this));
        getChildren().add(currentPlayerLabel = new Label());
        // bind the font size to the height of the window and divide it by 20
        currentPlayerLabel.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(heightProperty().multiply(0.05).get()), heightProperty()));
        currentPlayerLabel.layoutXProperty().bind(widthProperty().subtract(currentPlayerLabel.widthProperty()).divide(2));
        currentPlayerLabel.layoutYProperty().bind(boardPane.layoutYProperty().add(boardPane.heightProperty()).add(10));

        toggleAI.setDisable(false);
    }

    public void notifyEndGame(Token winner) {
        endPopup.show(winner);
        currentPlayerLabel.setVisible(false);
        for (int i = 0; i < boardPane.getChildren().size(); i++) {
            boardPane.getChildren().get(i).setDisable(true);
        }
        toggleAI.setDisable(true);
    }

    public void restart() {
        controller.newGame();
    }

    public void updateCurrentPlayer() {
        currentPlayerLabel.setText("It's " + Token.values()[game.getCurrentPlayer()] + "'s turn!");
        currentPlayerLabel.setTextFill(TokenShape.getColor(Token.values()[game.getCurrentPlayer()]));
        currentPlayerLabel.setBackground(new Background(new BackgroundFill(BLACK, null, null)));
    }

    public void setToggleAI(CheckBox toggleAI) {
        this.toggleAI = toggleAI;
    }

    public boolean isToggleAIChecked() {
        return toggleAI.isSelected();
    }

}
