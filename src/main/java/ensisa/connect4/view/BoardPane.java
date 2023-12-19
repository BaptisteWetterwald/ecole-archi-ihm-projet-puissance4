package ensisa.connect4.view;

import ensisa.connect4.model.Game;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BoardPane extends Pane {
    private final Game game;
    private final GridPane grid = new GridPane();
    private Shape background = new Rectangle();

    public BoardPane(Game game, Pane parent) {
        super();
        this.game = game;

        prefWidthProperty().bind(parent.heightProperty().multiply(0.8));
        prefHeightProperty().bind(parent.heightProperty().multiply(0.8).multiply(game.getNbRows()).divide(game.getNbColumns()));

        layoutXProperty().bind(parent.widthProperty().subtract(widthProperty()).divide(2));
        layoutYProperty().bind(parent.heightProperty().multiply(0.2));

        // center the grid in the parent
        grid.layoutXProperty().bind(widthProperty().subtract(grid.widthProperty()).divide(2));
        grid.layoutYProperty().bind(heightProperty().subtract(grid.heightProperty()).divide(2));

        // bind the size of the grid to the size of the window
        grid.prefWidthProperty().bind(widthProperty());
        grid.prefHeightProperty().bind(heightProperty());
        grid.setBackground(Background.EMPTY);

        ((Rectangle) background).heightProperty().bind(grid.heightProperty());
        ((Rectangle) background).widthProperty().bind(grid.widthProperty());
        background.setFill(Color.TRANSPARENT);
        ((Rectangle) background).setArcHeight(30);
        ((Rectangle) background).setArcWidth(30);
        background.layoutXProperty().bind(grid.layoutXProperty());
        background.layoutYProperty().bind(grid.layoutYProperty());

        getChildren().add(background);
        getChildren().add(grid);

        // add columns
        for (int i = 0; i < game.getNbColumns(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / game.getNbColumns());
            grid.getColumnConstraints().add(column);
        }

        // draw circles for each token
        update();
    }

    public void update(int row, int column) {
        TokenShape tokenShape = new TokenShape(game.getToken(row, column), grid, game.getNbRows(), game.getNbColumns());

        TranslateTransition tt = new TranslateTransition();
        tt.setNode(tokenShape);
        tt.setDuration(javafx.util.Duration.millis(500));
        tt.setFromY(tokenShape.getRadius() * 2 * (-game.getFirstEmptyRow(column) - 1));
        tt.setToY(0);
        tt.play();

        grid.add(tokenShape, column, row);
    }

    public void update() {
        grid.getChildren().clear();
        for (int i = 0; i < game.getNbRows(); i++) {
            for (int j = 0; j < game.getNbColumns(); j++) {
                TokenShape tokenShape = new TokenShape(game.getToken(i, j), grid, game.getNbRows(), game.getNbColumns());
                grid.add(tokenShape, j, i);
                Shape newBackground = Rectangle.subtract(background, tokenShape);
                newBackground.setFill(Color.PURPLE);
                newBackground.setOpacity(1);
                background = newBackground;
                getChildren().add(background);
            }
        }
    }

    private void removeAt(int colIndex, int rowIndex) {
        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (GridPane.getColumnIndex(grid.getChildren().get(i)) == colIndex
                    && GridPane.getRowIndex(grid.getChildren().get(i)) == rowIndex) {
                grid.getChildren().remove(i);
                break;
            }
        }
    }
}
