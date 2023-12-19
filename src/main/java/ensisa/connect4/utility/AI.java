package ensisa.connect4.utility;

import ensisa.connect4.model.Game;
import ensisa.connect4.model.Token;

import java.util.ArrayList;
import java.util.List;

public abstract class AI {

    private static final int MAX_DEPTH = 7;

    /* Returns best column to play for maxPlayer */
    public static int calcBestMove(Game game, int maxPlayer, int currentDepth) {
        int alpha = -10;
        int beta = 10;
        int player = game.getCurrentPlayer();
        int turn = game.getTurn();
        List<Integer> moves = getAvailableMoves(game);
        int bestMove = moves.get(0);
        int bestValue = -10;
        for (int move : moves) {
            Game newGame = new Game(game);
            newGame.setToken(newGame.getFirstEmptyRow(move), move, Token.values()[maxPlayer]);
            int value = minValue(newGame, turn + 1, player % 2 + 1, MAX_DEPTH, alpha, beta, currentDepth + 1);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static int minValue(Game game, int turn, int player, int aiLevel, int alpha, int beta, int currentDepth) {
        if (checkVictory(game)) return 100;
        if (currentDepth >= aiLevel) return evaluate(game, turn % 2 + 1);
        int value = 10;
        List<Integer> moves = getAvailableMoves(game);

        for (int move : moves) {
            Game newGame = new Game(game);
            newGame.setToken(newGame.getFirstEmptyRow(move), move, Token.values()[player]);
            value = Math.min(value, maxValue(newGame, turn, player, aiLevel, alpha, beta, currentDepth + 1));
            if (value <= alpha) return value;
            beta = Math.min(beta, value);
        }

        return value;
    }

    private static int maxValue(Game game, int turn, int player, int aiLevel, int alpha, int beta, int currentDepth) {
        if (checkVictory(game)) return -100;
        if (currentDepth >= aiLevel) return evaluate(game, 2 - turn % 2);
        int value = -10;
        List<Integer> moves = getAvailableMoves(game);

        for (int move : moves) {
            Game newGame = new Game(game);
            newGame.setToken(newGame.getFirstEmptyRow(move), move, Token.values()[player]);
            value = Math.max(value, minValue(newGame, turn, player, aiLevel, alpha, beta, currentDepth + 1));
            if (value >= beta) return value;
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    private static List<Integer> getAvailableMoves(Game game) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < game.getNbColumns(); i++) {
            if (game.getFirstEmptyRow(i) != -1) {
                moves.add(i);
            }
        }
        return moves;
    }

    private static int evaluate(Game game, int player) {
        int score = calculateScore(game, player);
        int opponentScore = calculateScore(game, 3 - player);
        return score - opponentScore;
    }

    private static int calculateScore(Game game, int player) {
        int score = 0;
        Token[][] board = game.cloneBoard();
        Token playerToken = Token.values()[player];

        // Horizontal alignment check
        for (int row = 0; row < 6; row++) {
            for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
                if (
                        playerToken == board[row][horizontalShift] &&
                                playerToken == board[row][horizontalShift + 1] &&
                                playerToken == board[row][horizontalShift + 2] &&
                                playerToken == board[row][horizontalShift + 3]
                ) {
                    score++;
                }
            }
        }

        // Vertical alignment check
        for (int column = 0; column < 7; column++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (
                        playerToken == board[verticalShift][column] &&
                                playerToken == board[verticalShift + 1][column] &&
                                playerToken == board[verticalShift + 2][column] &&
                                playerToken == board[verticalShift + 3][column]
                ) {
                    score++;
                }
            }
        }

        // Diagonal alignment check
        for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (
                        playerToken == board[verticalShift][horizontalShift] &&
                                playerToken == board[verticalShift + 1][horizontalShift + 1] &&
                                playerToken == board[verticalShift + 2][horizontalShift + 2] &&
                                playerToken == board[verticalShift + 3][horizontalShift + 3]
                ) {
                    score++;
                } else if (
                        playerToken == board[5 - verticalShift][horizontalShift] &&
                                playerToken == board[4 - verticalShift][horizontalShift + 1] &&
                                playerToken == board[3 - verticalShift][horizontalShift + 2] &&
                                playerToken == board[2 - verticalShift][horizontalShift + 3]
                ) {
                    score++;
                }
            }
        }

        return score;
    }

    public static boolean checkVictory(Game game) {
        Token[][] board = game.cloneBoard();
        /*// Horizontal alignment check
        for (int line = 0; line < 6; line++) {
            for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
                if (
                    board[horizontalShift][line] != Token.EMPTY &&
                    board[horizontalShift + 1][line] == board[horizontalShift][line] &&
                    board[horizontalShift + 2][line] == board[horizontalShift][line] &&
                    board[horizontalShift + 3][line] == board[horizontalShift][line]
                ) {
                    return true;
                }
            }
        }

        // Vertical alignment check
        for (int column = 0; column < 7; column++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (
                    board[column][verticalShift] != Token.EMPTY &&
                    board[column][verticalShift + 1] == board[column][verticalShift] &&
                    board[column][verticalShift + 2] == board[column][verticalShift] &&
                    board[column][verticalShift + 3] == board[column][verticalShift]
                ) {
                    return true;
                }
            }
        }

        // Diagonal alignment check
        for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (board[horizontalShift][verticalShift] != Token.EMPTY &&
                        board[horizontalShift + 1][verticalShift + 1] == board[horizontalShift][verticalShift] &&
                        board[horizontalShift + 2][verticalShift + 2] == board[horizontalShift][verticalShift] &&
                        board[horizontalShift + 3][verticalShift + 3] == board[horizontalShift][verticalShift]) {
                    return true;
                } else if (board[horizontalShift][5 - verticalShift] != Token.EMPTY &&
                        board[horizontalShift + 1][4 - verticalShift] == board[horizontalShift][5 - verticalShift] &&
                        board[horizontalShift + 2][3 - verticalShift] == board[horizontalShift][5 - verticalShift] &&
                        board[horizontalShift + 3][2 - verticalShift] == board[horizontalShift][5 - verticalShift]) {
                    return true;
                }
            }
        }*/

        // Horizontal alignment check
        for (int row = 0; row < 6; row++) {
            for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
                if (
                        board[row][horizontalShift] != Token.EMPTY &&
                                board[row][horizontalShift + 1] == board[row][horizontalShift] &&
                                board[row][horizontalShift + 2] == board[row][horizontalShift] &&
                                board[row][horizontalShift + 3] == board[row][horizontalShift]
                ) {
                    return true;
                }
            }
        }

        // Vertical alignment check
        for (int column = 0; column < 7; column++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (
                        board[verticalShift][column] != Token.EMPTY &&
                                board[verticalShift + 1][column] == board[verticalShift][column] &&
                                board[verticalShift + 2][column] == board[verticalShift][column] &&
                                board[verticalShift + 3][column] == board[verticalShift][column]
                ) {
                    return true;
                }
            }
        }

        // Diagonal alignment check
        for (int horizontalShift = 0; horizontalShift < 4; horizontalShift++) {
            for (int verticalShift = 0; verticalShift < 3; verticalShift++) {
                if (board[verticalShift][horizontalShift] != Token.EMPTY &&
                        board[verticalShift + 1][horizontalShift + 1] == board[verticalShift][horizontalShift] &&
                        board[verticalShift + 2][horizontalShift + 2] == board[verticalShift][horizontalShift] &&
                        board[verticalShift + 3][horizontalShift + 3] == board[verticalShift][horizontalShift]) {
                    return true;
                } else if (board[5 - verticalShift][horizontalShift] != Token.EMPTY &&
                        board[4 - verticalShift][horizontalShift + 1] == board[5 - verticalShift][horizontalShift] &&
                        board[3 - verticalShift][horizontalShift + 2] == board[5 - verticalShift][horizontalShift] &&
                        board[2 - verticalShift][horizontalShift + 3] == board[5 - verticalShift][horizontalShift]) {
                    return true;
                }
            }
        }

        return false;
    }

}
