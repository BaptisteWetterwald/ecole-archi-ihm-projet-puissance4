package ensisa.connect4.view;

import ensisa.connect4.model.Token;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndPopup extends Pane {

    private final Label winnerLabel;

    public EndPopup(GameView gameView) {
        super();
        this.autosize();
        this.setVisible(false);
        this.setStyle("-fx-background-color: rgba(225, 198, 153, 0.85);");
        prefHeightProperty().bind(gameView.heightProperty().divide(2));
        prefWidthProperty().bind(gameView.widthProperty().divide(2));
        layoutXProperty().bind(gameView.widthProperty().subtract(widthProperty()).divide(2));
        layoutYProperty().bind(gameView.heightProperty().subtract(heightProperty()).divide(2));

        winnerLabel = new Label();
        winnerLabel.setFont(Font.font(80));
        winnerLabel.setStyle("-fx-text-fill: white; -fx-stroke: black; -fx-stroke-width: 3px;");
        winnerLabel.layoutXProperty().bind(this.widthProperty().subtract(winnerLabel.widthProperty()).divide(2));
        winnerLabel.layoutYProperty().bind(this.heightProperty().subtract(winnerLabel.heightProperty()).divide(3.2));
        getChildren().add(winnerLabel);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event -> {
            System.out.println("Restarting game...");
            hide();
            gameView.restart();
        });
        restartButton.setFont(Font.font(30));
        restartButton.prefHeightProperty().bind(this.heightProperty().divide(10));
        restartButton.prefWidthProperty().bind(this.widthProperty().divide(2));
        restartButton.layoutXProperty().bind(this.widthProperty().subtract(restartButton.widthProperty()).divide(2));
        restartButton.layoutYProperty().bind(this.heightProperty().subtract(restartButton.heightProperty()).divide(2).add(50));
        this.getChildren().add(restartButton);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            System.out.println("Quitting game...");
            System.exit(0);
        });
        quitButton.setFont(Font.font(30));
        quitButton.prefHeightProperty().bind(this.heightProperty().divide(10));
        quitButton.prefWidthProperty().bind(this.widthProperty().divide(2));
        quitButton.layoutXProperty().bind(this.widthProperty().subtract(quitButton.widthProperty()).divide(2));
        quitButton.layoutYProperty().bind(restartButton.layoutYProperty().add(quitButton.heightProperty()).add(30));
        this.getChildren().add(quitButton);
    }

    private void hide() {
        // fade out
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(500), this);
        tt.setFromY(0);
        tt.setToY(-this.getHeight());
        tt.setOnFinished(event -> {
            this.setVisible(false);
        });
        tt.play();
    }

    private void update(Token winner) {
        winnerLabel.setText(winner == null ? "Draw!" : winner + " wins!");
        winnerLabel.setTextFill(winner == null ? Color.DARKGREEN : TokenShape.getColor(winner));
    }

    public void show(Token winner) {
        update(winner);
        // fade in
        this.setVisible(true);
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(500), this);
        tt.setFromY(-this.getHeight());
        tt.setToY(0);

        tt.play();
    }

}
