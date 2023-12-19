package ensisa.connect4.controller;

import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;
import ensisa.connect4.utility.AI;
import ensisa.connect4.view.GameView;

import java.util.Random;

public class GameController {

    private final Game game;
    private final GameView view;
    private final Random rdm = new Random();

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        view.setController(this);
        view.setGame(game);
        newGame();
    }

    public void newGame() {
        game.resetBoard();
        game.setCurrentPlayer(rdm.nextInt(1, 3));
        game.setTurn(1);

        view.setupBoard();
        view.updateBoard();
        view.updateCurrentPlayer();

        System.out.println("Player " + game.getCurrentPlayer() + " starts!");

        if (game.getNbHumanPlayers() < game.getCurrentPlayer()) {
            int aiMove = AI.calcBestMove(game, game.getCurrentPlayer(), 0);
            play(aiMove);
        }
    }

    public void play(int column) {
        int row = game.getFirstEmptyRow(column);
        if (row != -1) {
            Token token = Token.values()[game.getCurrentPlayer()];
            game.setToken(row, column, token);
            game.setTurn(game.getTurn() + 1);
            view.updateBoard(row, column);
            if (game.isWinningMove(row, column, token)) {
                System.out.println("Player " + token + " wins!");
                view.notifyEndGame(token);
            } else if (game.isFull()) {
                System.out.println("Draw!");
                view.notifyEndGame(null);
            } else {
                nextPlayer();
                System.out.println("Next player!");
                if (game.getNbHumanPlayers() < game.getCurrentPlayer()) {
                    int aiMove = AI.calcBestMove(game, game.getCurrentPlayer(), 0);
                    play(aiMove);
                }
            }
        } else {
            System.out.println("Column full!");
        }
    }

    private void nextPlayer() {
        game.setCurrentPlayer(3 - game.getCurrentPlayer());
        view.updateCurrentPlayer();
    }

    public void setAI(Boolean newValue) {
        game.setNbHumanPlayers(newValue ? 1 : 2);
        if (game.getNbHumanPlayers() < game.getCurrentPlayer()) {
            int aiMove = AI.calcBestMove(game, game.getCurrentPlayer(), 0);
            play(aiMove);
        }
    }
}