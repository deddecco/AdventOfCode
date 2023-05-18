import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Queen {
    public static ArrayList<String> moveList = new ArrayList<>();

    // can move in any direction-- like either a rook or a bishop-- as long as there is space
    // in the center, can see almost half the board at once

    // queen has most possible paths of any piece
    // optimal path is not necessarily the optimum of the best bishop path or the best rook path
    // actually optimal path will probably combine the powers of the pieces
    // to make the king's solution easier, if there are multiple queen paths with the same number of moves, pick the
    // path from among those that passes over the fewest squares
    // knowing this path and its length-- for free-- will give the King's optimal path

    public static void main(String[] args) {

        Queen q1 = new Queen();
        setup(q1);
    }

    private static void setup(Queen q1) {
        String start = "a1";
        String end = "h4";
        System.out.println("start square: " + start);
        System.out.println("end square: " + end);
        System.out.println("number of optimal moves: " + q1.getOptimalMoves(start, end));
        System.out.println("Optimal list of moves from " + start + " to " + end + ": " + moveList);
    }


    /**
     * If the queen is on its ending square already, this case is trivial. If the queen's starting and ending squares are on the same file or rank,
     * then the best sequence of moves is to act purely like a rook and to arrive at the destination in one move.
     * If the queen is placed on its starting square which is on the same 45-degree diagonal as its ending square, then the best sequence of moves is to move purely like a bishop
     * along the diagonal from the start square to the end square. Otherwise, the optimal combination is one which contains one rook-like move and one bishop-like move by the queen, and which traverses the smallest number of squares in the process.
     */
    private int getOptimalMoves(String start, String end) {
        char startFile = start.charAt(0);
        int startRank = Character.getNumericValue(start.charAt(1));
        char endFile = end.charAt(0);
        int endRank = Character.getNumericValue(end.charAt(1));


        // queen in right place already
        if (startFile == endFile && startRank == endRank) {
            return 0;
        }

        // queen can act like pure bishop
        if ((endFile - startFile) == (endRank - startRank)) {
            moveList.add(start);
            moveList.add(end);
            return 1;
        }

        // queen can act like pure rook
        if (startFile == endFile || startRank == endRank) {
            return 1;
        }


        // there are longer combined paths, but there always exists at least one path in just
        // 2 moves combining the movement of the rook and the bishop

        // this optimal path has 2 symmetrical variations. we will take this one:
        //      from the start square, move like a bishop until the same rank as the end square,
        //      then move the rest of the way like a rook
        moveList.add(start);


        // like with the bishop, create a diagram from both endpoints highlighting what the queen can see
        // if on the starting square, and separately if on the ending square, then find the overlap
        char[][] boardVisibleFromStart = createQueenArrayOfVisibleSquares(startFile, startRank);
        char[][] boardVisibleFromEnd = createQueenArrayOfVisibleSquares(endFile, endRank);
        List<String> intermediates = findCommonSquare(boardVisibleFromStart, boardVisibleFromEnd);

        // at this point we have all possible intermediate squares
        // now find the intermediate square with the shortest distance
        String bestIntermediate = findBestIntermediate(start, intermediates, end);
        // and once we have the square-- or one of the squares, in case of a tie-- with the shortest path,
        // we add it to the move list
        // not looking for every path of the length of the shortest path, just one of them, if more than one exists
        moveList.add(bestIntermediate);
        // and finish the move list by then indicating a move from there to the final destination
        moveList.add(end);
        return 2;
    }


    /**
     * Create a hashMap whose key-value pair is the algebraic notation of a legal intermediate square and the length of the path from start to end which goes through that square.
     * Because a Queen combines the mobility of a rook and a bishop (but only one of those in a given move), it can get to any square from any other square in 2 moves or fewer.
     * A square is an intermediate square if it is visible to a queen placed at the start square and to a queen placed at the end square, at the same time.
     * The optimal intermediate square is the square, or one of the squares, for which the length of this path is minimized.
     */
    public String findBestIntermediate(String start, List<String> intermediates, String end) {
        int totalDistance;

        HashMap<String, Integer> squareAndPathLengthThroughSquare = new HashMap<>();
        for (String intermediate : intermediates) {
            totalDistance = 0;


            // a move is a bishop move if the absolute change in the encoding of the ranks is the same as the absolute change in the encoding of the files
            boolean move1IsBishop = Math.abs(start.charAt(0) - intermediate.charAt(0)) == Math.abs(start.charAt(1) - intermediate.charAt(1));
            boolean move2IsBishop = Math.abs(end.charAt(0) - intermediate.charAt(0)) == Math.abs(end.charAt(1) - intermediate.charAt(1));

            // a move is a rook move if the absolute change of either rooks or files is 0 and the absolute change of the other is non-zero
            boolean move1IsRook = Math.abs(start.charAt(0) - intermediate.charAt(0)) == 0 || Math.abs(start.charAt(1) - intermediate.charAt(1)) == 0;
            boolean move2IsRook = Math.abs(end.charAt(0) - intermediate.charAt(0)) == 0 || Math.abs(end.charAt(1) - intermediate.charAt(1)) == 0;

            if (move1IsBishop) {
                totalDistance += Math.abs(start.charAt(0) - intermediate.charAt(0));
            }
            if (move2IsBishop) {
                totalDistance += Math.abs(end.charAt(0) - intermediate.charAt(0));
            }
            if (move1IsRook) {
                totalDistance += Math.max(Math.abs(start.charAt(0) - intermediate.charAt(0)), Math.abs(start.charAt(1) - intermediate.charAt(1)));
            }
            if (move2IsRook) {
                totalDistance += Math.max(Math.abs(end.charAt(0) - intermediate.charAt(0)), Math.abs(end.charAt(1) - intermediate.charAt(1)));
            }

            squareAndPathLengthThroughSquare.put(intermediate, totalDistance);
        }

        int minValue = Integer.MAX_VALUE;
        // for every key-value pair
        for (int distance : squareAndPathLengthThroughSquare.values()) {
            // if the current key is less than the smallest key so far
            if (distance < minValue) {
                // the current key becomes the smallest
                minValue = distance;
            }
        }

        System.out.println("map: " + squareAndPathLengthThroughSquare);

        // looking through every key
        for (Entry<String, Integer> entry : squareAndPathLengthThroughSquare.entrySet()) {
            // if the value of a key-value pair is the minimum (there might be multiple-- 2, 3, 4, etc.- way ties are possible)
            // then return the key of that pair
            if (entry.getValue() == minValue) {
                return entry.getKey();
            }
        }

        return "";
    }

    /**
     * A square is visible to the queen if it is on the same rank, file, or diagonal as where the queen is. In the center, almost half the board is visible to the queen
     */
    public char[][] createQueenArrayOfVisibleSquares(char file, int rank) {
        Queen q1 = new Queen();
        char[][] board = new char[8][8];
        // computers cannot natively deal with "a7", etc.-- so this converts the file letter to the
        int fileConverted = q1.convertFileToNumFromLetter(file);

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
        board[rankConverted][fileConverted] = 'Q';


        // set as visible the squares the bishop can see with *
        /*
         Like a bishop, the queen's vision extends as far as the board allows,
         in an X formed by lines at 45 degrees
         that intersect at right angles at the bishop
         a bishop must only ever move on squares that
         are the same color as the square on which it was first places
        */
        Bishop.setBoardVisibilityArrayBishop(board, fileConverted, rankConverted);

        // Like  a rook, the queen's vision also extends along the entire length of the file on which the queen is
        for (int i = rankConverted; i < rankConverted + 1; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[rankConverted][j] != 'Q') {
                    board[rankConverted][j] = '*';
                }
            }
        }
        // and along the entire length of the rank on which the queen is
        for (int i = 0; i < 8; i++) {
            for (int j = fileConverted; j < fileConverted + 1; j++) {
                if (board[i][fileConverted] != 'Q') {
                    board[i][fileConverted] = '*';
                    break;
                }
            }
        }


        return board;
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
     * Given the index of the file in 0-7 Java index terms (of the column of a 2d array), convert this back to a-h chess file notation
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


    /**
     * A square is common to two boards if it is visible by the queen placed as is on both boards provided as inputs.
     * If a square is visible by the queen on a particular board, that square will be notated by * in its position on that board.
     * If the same position has * on both boards, then the square is common to those boards and the
     * coordinates of the square in chess notation should be added to a list of common squares to be returned by the method
     */
    public List<String> findCommonSquare(char[][] board1, char[][] board2) {
        Queen q1 = new Queen();

        // the currennt common square
        String commonSquare;

        // a gathering place for all common squares
        List<String> commonSquares = new ArrayList<>();


        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                if (board1[i][j] == board2[i][j] && board1[i][j] == '*') {
                    commonSquare = String.valueOf(q1.convertFileToLetterFromNum(j)) + (8 - i);
                    commonSquares.add(commonSquare);
                }
            }
        }
        return commonSquares;
    }


    /**
     * helpful for debugging
     */
    public static void printBoard(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

}