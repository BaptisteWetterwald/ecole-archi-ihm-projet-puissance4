package ensisa.connect4.view;

import ensisa.connect4.model.Token;
import javafx.css.converter.PaintConverter;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TokenShape extends Circle {

    private final Circle circle;

    public TokenShape(Token token, GridPane parent, int nbRows, int nbColumns) {
        super();

        circle = new Circle();
        circle.setFill(getColor(token));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        // bind height and width to the height and width of the parent divided by the number of rows and columns

        // center the circle in the parent
        centerXProperty().bind(parent.widthProperty().divide(nbColumns).divide(2));
        centerYProperty().bind(parent.heightProperty().divide(nbRows).divide(2));

    }

    private Color getColor(Token token) {
        switch (token) {
            case EMPTY -> {
                return Color.WHITE;
            }
            case PLAYER1 -> {
                return Color.RED;
            }
            case PLAYER2 -> {
                return Color.YELLOW;
            }
        }
        throw new IllegalArgumentException("Unknown token: " + token);
    }

}
