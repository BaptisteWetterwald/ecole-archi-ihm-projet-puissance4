package ensisa.connect4.controller;

import ensisa.connect4.view.GameView;
import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;

public class GameController {

    private final Game game;
    private final GameView view;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        view.setController(this);
        view.setGame(game);
        newGame();
    }

    public void newGame(){
        game.resetBoard();
        game.setCurrentPlayer(1);
        view.printBoard();
        view.setupBoard();
        view.updateBoard();
        view.updateCurrentPlayer();
    }

    public void play(int column) {
        int row = game.getFirstEmptyRow(column);
        if (row != -1) {
            Token token = Token.values()[game.getCurrentPlayer()];
            game.setToken(row, column, token);
            view.updateBoard(row, column);
            if (game.isWinningMove(row, column, token)) {
                System.out.println("Player " + token + " wins!");
                view.notifyEndGame(token);
            }
            else if (game.isFull()) {
                System.out.println("Draw!");
                view.notifyEndGame(null);
            }
            else {
                nextPlayer();
                System.out.println("Next player!");
            }
        }
        else {
            System.out.println("Column full!");
        }
    }

    private void nextPlayer() {
        game.setCurrentPlayer(3 - game.getCurrentPlayer());
        view.updateCurrentPlayer();
    }

}