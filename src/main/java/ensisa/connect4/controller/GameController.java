package ensisa.connect4.controller;

import ensisa.connect4.GameView;
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
        manageGame();
    }

    private void manageGame(){
        game.resetBoard();
        game.setCurrentPlayer(1);
        view.printBoard();
        view.setupBoard();
        view.updateBoard();
    }

    public boolean play(int column, Token token) {
        int row = game.getFirstEmptyRow(column);
        if (row != -1) {
            game.setToken(row, column, token);
            view.updateBoard(row, column);
            if (game.isWinningMove(row, column, token)) {
                System.out.println("Player " + token + " wins!");
            }
            else if (game.isFull()) {
                System.out.println("Draw!");
            }
            else {
                System.out.println("Next player!");
            }
            return true;
        }
        else {
            System.out.println("Column full!");
            return false;
        }
    }

}