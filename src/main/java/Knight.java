public class Knight extends Piece {

    // knights are the only pieces that do not move in straight lines
    // moves 2 squares in one direction, turns 90 degrees, and moves one square
    public static void main(String[] args) {
        Knight n1 = new Knight();

        char[][] knightInTheCenterArray = n1.createKnightArrayOfVisibleSquares('d', 5);

        printBoard(knightInTheCenterArray);
    }

    public char[][] createKnightArrayOfVisibleSquares(char file, int rank) {

        Knight n1 = new Knight();

        char[][] board = new char[8][8];

        int fileConverted = n1.convertFileToNumFromLetter(file);
        int rankConverted = 8 - rank;

        board[rankConverted][fileConverted] = 'N';

        if (rankConverted + 2 < 8 && fileConverted + 1 < 8) {
            board[rankConverted + 2][fileConverted + 1] = '*';
        }
        if (rankConverted + 2 < 8 && fileConverted - 1 > 0) {
            board[rankConverted + 2][fileConverted - 1] = '*';
        }
        if (rankConverted - 2 > 0 && fileConverted + 1 < 8) {
            board[rankConverted - 2][fileConverted + 1] = '*';
        }
        if (rankConverted - 2 > 0 && fileConverted - 1 > 0) {
            board[rankConverted - 2][fileConverted - 1] = '*';
        }
        if (rankConverted + 1 < 8 && fileConverted + 2 < 8) {
            board[rankConverted + 1][fileConverted + 2] = '*';
        }
        if (rankConverted + 1 < 8 && fileConverted - 2 > 0) {
            board[rankConverted + 1][fileConverted - 2] = '*';
        }
        if (rankConverted - 1 > 0 && fileConverted + 2 < 8) {
            board[rankConverted - 1][fileConverted + 2] = '*';
        }
        if (rankConverted - 1 > 0 && fileConverted - 2 > 0) {
            board[rankConverted - 1][fileConverted - 2] = '*';
        }

        return board;
    }


}