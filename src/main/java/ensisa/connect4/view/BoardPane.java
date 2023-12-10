package ensisa.connect4.view;

import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;
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

        // bind the size of the board to the size of the window
        prefHeightProperty().bind(parent.heightProperty().multiply(0.7));
        prefWidthProperty().bind(heightProperty());

        // display it at the bottom of the window
        layoutYProperty().bind(parent.heightProperty().multiply(0.3));
        // center it horizontally
        layoutXProperty().bind(parent.widthProperty().subtract(widthProperty()).divide(2));

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
    }

    public void update() {
        getChildren().clear();
        for (int i = 0; i < game.getNbRows(); i++) {
            for (int j = 0; j < game.getNbColumns(); j++) {
                TokenShape tokenShape = new TokenShape(Token.EMPTY, this, game.getNbRows(), game.getNbColumns());
                tokenShape.prefHeightProperty().bind(heightProperty().divide(game.getNbRows()));
                tokenShape.prefWidthProperty().bind(widthProperty().divide(game.getNbColumns()));
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
