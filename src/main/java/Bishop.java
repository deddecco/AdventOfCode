import java.util.ArrayList;

public class Bishop {

    public static ArrayList<String> moveList = new ArrayList<>();

    public static void main(String[] args) {
        Bishop b1 = new Bishop();

        setup(b1);
    }

    private static void setup(Bishop b1) {
        String start = "a1";
        String end = "h8";
        System.out.println("start square: " + start);
        System.out.println("end square: " + end);
        System.out.println("start and end squares are same color?: " + b1.ensureSameColor(start, end));
        System.out.println("number of optimal moves: " + b1.getOptimalMoves(start, end));
        System.out.println("Optimal list of moves from " + start + " to " + end + ": " + moveList);
    }

    public String findCommonSquare(char[][] board1, char[][] board2) {
        Bishop b1 = new Bishop();

        String commonSquare = null;
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                if (board1[i][j] == board2[i][j] && board1[i][j] == '*') {
                    commonSquare = String.valueOf(b1.convertFileToLetterFromNum(j)) + (8 - i);
                    System.out.println("The square in common is " + commonSquare);
                }
            }
        }
        return commonSquare;
    }

    public int getOptimalMoves(String start, String end) {
        /*
         each side's bishop pair has one which is restricted
         to light squares and one restricted to dark squares
         so check if the squares are the same color before anything else
        */
        boolean moveIsPossible = ensureSameColor(start, end);

        if (moveIsPossible) {
            char startFile = start.charAt(0);
            int startRank = Character.getNumericValue(start.charAt(1));
            char endFile = end.charAt(0);
            int endRank = Character.getNumericValue(end.charAt(1));


            // bishop is already on desired square
            if (startFile == endFile && startRank == endRank) {
                return 0;
            }

            // move is possible, already on the right diagonal
            if ((endFile - startFile) == (endRank - startRank)) {
                moveList.add(start);
                moveList.add(end);
                return 1;
            }
            // move is possible, but need to get to the right diagonal first
            moveList.add(start);
            Bishop b1 = new Bishop();
            // create one board where a bishop is on the start square
            // and determine all the squares visible to it from there
            char[][] board1 = b1.createBishopArrayOfVisibleSquares(startFile, startRank);
            for (int i = 0; i < 3; i++) {
                System.out.println();
            }
            //create another board where the same bishop is on the end square this time
            // and find all the squares visible to it from there
            char[][] board2 = b1.createBishopArrayOfVisibleSquares(endFile, endRank);
            // determine which square is visible to the bishop in both instances.
            // there will be one path, or two symmetrical paths. if two paths exist, pick one by default
            String common = b1.findCommonSquare(board1, board2);
            // add that square visible from both positions to the list of moves
            // (go there fromm the start, and go to the end from there)
            moveList.add(common);
            // add the end to the move list
            moveList.add(end);
            return 2;

        }
        // move isn't possible
        return -1;

    }

    /**
     * bishops must stay on the same color square throughout the whole game.
     * each side starts with two bishops, one which only ever moves on light
     * squares and one which only ever moves on dark squares.
     * If the bishop that moves on light squares wants to go to a dark square,
     * such a path does not exist, and neither would a path exist for the
     * dark-square bishop to go to a light square
     */
    public boolean ensureSameColor(String start, String end) {
        char startFile = start.charAt(0);
        int startRank = Character.getNumericValue(start.charAt(1));
        char endFile = end.charAt(0);
        int endRank = Character.getNumericValue(end.charAt(1));

        int startColorCode = 0;
        int endColorCode = 0;


        // even ranks on the a, c, e, g files are white
        // odd ranks are black
        if (startFile == 'a' || startFile == 'c' || startFile == 'e' || startFile == 'g') {
            if (startRank % 2 == 1) {
                startColorCode = 1;
            }
        }

        if (endFile == 'a' || endFile == 'c' || endFile == 'e' || endFile == 'g') {
            if (endRank % 2 == 1) {
                endColorCode = 1;
            }
        }


        // even ranks on the b, d, f, h files are black
        // odd ranks are white
        if (startFile == 'b' || startFile == 'd' || startFile == 'f' || startFile == 'h') {
            if (startRank % 2 == 0) {
                startColorCode = 1;
            }
        }

        if (endFile == 'b' || endFile == 'd' || endFile == 'f' || endFile == 'h') {
            if (endRank % 2 == 0) {
                endColorCode = 1;
            }
        }

        return startColorCode == endColorCode;

    }

    /**
     * given the square a bishop is currently, on, populate an 8x8 array of chars
     * with 'B' where the bishop is
     * with '*' if the bishop can see the square
     * and with 'I' if the bishop cannot see the square
     */
    public char[][] createBishopArrayOfVisibleSquares(char file, int rank) {
        Bishop b1 = new Bishop();
        char[][] board = new char[8][8];
        // computers cannot natively deal with "a7", etc.-- so this converts the file letter to the
        int fileConverted = b1.convertFileToNumFromLetter(file);

        /*
         as White (the perspective assumed here), when looking at a board on a screen, the 8th rank will be on top,
         and the 7th underneath it, etc. until the bottom rank in the visualization is the first rank
         after this conversion process, the 8th rank gets put in the position
         rankConverted = 8 - 8 = 0 = the top row of the array according to Java
        */
        int rankConverted = 8 - rank;

        // populate the board setting all squares as invisible to the bishop
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 'I';
            }
        }


        /*
         the natural ordering for how computers store where an entry is,
         is in the reverse order as on a chessboard.
         ranks are rows and files are columns, but we always list the file first in ches
        */

        // set the bishop on its square on the board
        board[rankConverted][fileConverted] = 'B';


        // set as visible the squares the bishop can see with *
        /*
         A bishop's vision extends as far as the board allows,
         in an X formed by lines at 45 degrees
         that intersect at right angles at the bishop
         a bishop must only ever move on squares that
         are the same color as the square on which it was first places
        */
        setBoardVisibilityArrayBishop(board, fileConverted, rankConverted);
        return board;
    }

    static void setBoardVisibilityArrayBishop(char[][] board, int fileConverted, int rankConverted) {
        int offset = 1;
        while (rankConverted + offset < 8 && fileConverted + offset < 8) {
            board[rankConverted + offset][fileConverted + offset] = '*';
            offset++;
        }

        offset = 1;
        while (rankConverted - offset >= 0 && fileConverted + offset < 8) {
            board[rankConverted - offset][fileConverted + offset] = '*';
            offset++;
        }

        offset = 1;
        while (offset + rankConverted < 8 && fileConverted - offset >= 0) {
            board[offset + rankConverted][fileConverted - offset] = '*';
            offset++;
        }

        offset = 1;
        while (rankConverted - offset >= 0 && fileConverted - offset >= 0) {
            board[rankConverted - offset][fileConverted - offset] = '*';
            offset++;
        }
    }

    /**
     * files in chess notation = columns in Java notation.
     * if viewing as White, then a file on left, h file on right.
     * if viewing as Black, then h file on left, a file on right
     */
    public int convertFileToNumFromLetter(char file) {

        return switch (file) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> -1;
        };
    }

    /**
     * given the index of the file in 0-7 Java terms, convert this back to a-h chess notation
     */
    public char convertFileToLetterFromNum(int fileNum) {

        return switch (fileNum) {
            case 0 -> 'a';
            case 1 -> 'b';
            case 2 -> 'c';
            case 3 -> 'd';
            case 4 -> 'e';
            case 5 -> 'f';
            case 6 -> 'g';
            case 7 -> 'h';
            default -> '\0';
        };
    }
}