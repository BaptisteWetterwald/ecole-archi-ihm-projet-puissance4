package ensisa.connect4.model;

public class Game {

    private final Token[][] board;

    private final int nbRows;
    private final int nbColumns;
    private final int nbTokensToWin;
    private final int nbPlayers;
	private int currentPlayer;

    public Game(){
        this.nbRows = 6;
        this.nbColumns = 7;
        this.nbTokensToWin = 4;
        this.nbPlayers = 2;
		this.currentPlayer = 1;
        board = new Token[nbRows][nbColumns];
    }

    public Game(int nbRows, int nbColumns, int nbTokensToWin, int nbPlayers){
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.nbTokensToWin = nbTokensToWin;
        this.nbPlayers = nbPlayers;
        board = new Token[nbRows][nbColumns];
    }

    public int getNbRows() {
        return nbRows;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public int getNbTokensToWin() {
        return nbTokensToWin;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public Token getToken(int row, int column){
        return board[row][column];
    }

    public void setToken(int row, int column, Token token){
        board[row][column] = token;
    }

    public void resetBoard(){
        for(int i = 0; i < nbRows; i++){
            for(int j = 0; j < nbColumns; j++){
                board[i][j] = Token.EMPTY;
            }
        }
    }

    public boolean isFull(){
        for (Token[] row : board){
            for (Token token : row){
                if (token == Token.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public int getFirstEmptyRow(int column) {
    	for(int i = nbRows - 1; i >= 0; i--) {
    		if(board[i][column] == Token.EMPTY) {
    			return i;
    		}
    	}
    	return -1;
    }

    public boolean isWinningMove(int row, int column, Token token){
		if (token == Token.EMPTY) {
			throw new IllegalArgumentException("Token cannot be empty!");
		}
        return checkForWinHorizontal(row, token) || checkForWinVertical(column, token) || checkForWinDiagonalULBR(row, column, token) || checkForWinDiagonalBLUR(row, column, token);
    }

    public boolean checkForWinHorizontal(int row, Token token) {
    	int count = 0;
    	for(int i = 0; i < nbColumns; i++) {
    		if(board[row][i] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    	}
    	return false;
    }

    public boolean checkForWinVertical(int column, Token token) {
    	int count = 0;
    	for(int i = 0; i < nbRows; i++) {
    		if(board[i][column] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    	}
    	return false;
    }

public boolean checkForWinDiagonalULBR(int row, int column, Token token) {
    	int count = 0;
    	int i = row;
    	int j = column;
    	while(i >= 0 && j >= 0) {
    		if(board[i][j] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    		i--;
    		j--;
    	}
    	i = row + 1;
    	j = column + 1;
    	while(i < nbRows && j < nbColumns) {
    		if(board[i][j] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    		i++;
    		j++;
    	}
    	return false;
    }

    public boolean checkForWinDiagonalBLUR(int row, int column, Token token) {
    	int count = 0;
    	int i = row;
    	int j = column;
    	while(i < nbRows && j >= 0) {
    		if(board[i][j] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    		i++;
    		j--;
    	}
    	i = row - 1;
    	j = column + 1;
    	while(i >= 0 && j < nbColumns) {
    		if(board[i][j] == token) {
    			count++;
    			if(count == nbTokensToWin) {
    				return true;
    			}
    		}
    		else {
    			count = 0;
    		}
    		i--;
    		j++;
    	}
    	return false;
    }

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
