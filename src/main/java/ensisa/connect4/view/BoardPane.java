package ensisa.connect4.view;

import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardPane extends GridPane {
    private final Game game;

    public BoardPane(Game game, Pane parent) {
        super();
        this.game = game;

        prefWidthProperty().bind(parent.heightProperty().multiply(0.8));

        // center it horizontally
        layoutXProperty().bind(parent.widthProperty().subtract(widthProperty()).divide(2));
        // display it at the bottom of the window
        layoutYProperty().bind(parent.heightProperty().multiply(0.2));

        // set the background color
        setBackground(Background.fill(Color.BLUE));

        // add columns
        for (int i = 0; i < game.getNbColumns(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / game.getNbColumns());
            getColumnConstraints().add(column);
        }

        // draw circles for each token
        update();
    }

    public void update(int row, int column){
        TokenShape tokenShape = new TokenShape(game.getToken(row, column), this, game.getNbRows(), game.getNbColumns());

        TranslateTransition tt = new TranslateTransition();
        tt.setNode(tokenShape);
        tt.setDuration(javafx.util.Duration.millis(500));
        tt.setFromY(tokenShape.getRadius()*2*(-game.getFirstEmptyRow(column) - 1));
        tt.setToY(0);
        tt.play();

        //removeAt(column, row);
        add(tokenShape, column, row);
        /*tokenShape.toBack();

        tt.onFinishedProperty().set(event -> {
            tokenShape.toFront();
        });*/
    }

    public void update() {
        getChildren().clear();
        for (int i = 0; i < game.getNbRows(); i++) {
            for (int j = 0; j < game.getNbColumns(); j++) {
                TokenShape tokenShape = new TokenShape(game.getToken(i, j), this, game.getNbRows(), game.getNbColumns());
                add(tokenShape, j, i);
            }
        }
    }

    private void removeAt(int colIndex, int rowIndex) {
        for (int i = 0; i < getChildren().size(); i++) {
            if (GridPane.getColumnIndex(getChildren().get(i)) == colIndex
                    && GridPane.getRowIndex(getChildren().get(i)) == rowIndex) {
                getChildren().remove(i);
                break;
            }
        }
    }
}
