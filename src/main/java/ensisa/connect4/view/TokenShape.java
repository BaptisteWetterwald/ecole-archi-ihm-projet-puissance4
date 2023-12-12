package ensisa.connect4.view;

import ensisa.connect4.model.Token;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TokenShape extends Circle {

    public TokenShape(Token token, GridPane parent, int nbRows, int nbColumns) {
        super();

        setFill(getColor(token));
        setStroke(Color.BLACK);
        setStrokeWidth(3);

        radiusProperty().bind(parent.widthProperty().divide(nbColumns).divide(2).subtract(2));

        // center the circle in the parent
        centerXProperty().bind(parent.widthProperty().divide(nbColumns).divide(2));
        centerYProperty().bind(parent.heightProperty().divide(nbRows).divide(2));

        this.setOnMouseClicked(event -> {
            System.out.println("Clicked on token at coordinates " + GridPane.getRowIndex(this) + ", " + GridPane.getColumnIndex(this) + "!");
            ((GameView)(parent.getParent())).tryPlacement(GridPane.getColumnIndex(this));
        });
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
