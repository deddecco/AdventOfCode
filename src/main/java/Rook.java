import java.util.ArrayList;

import static java.lang.Character.getNumericValue;

public class Rook extends Piece {

    public static ArrayList<String> moveList = new ArrayList<>();

    public static void main(String[] args) {
        Rook r1 = new Rook();
        setup(r1);
    }

    private static void setup(Rook r1) {
        String start = "a5";
        String end = "d7";
        r1.addToMoveList(start, end);
        System.out.println("optimal move list: " + moveList);
        System.out.println("number of optimal moves: " + r1.getOptimalMoves(start, end));
    }

    public int getOptimalMoves(String start, String end) {

        // separate the strings giving the coordinates on the board of the desired
        // starting and ending positions of the rook into the rank and the file of each coordinate
        // ranks from 1 to 8 inclusive on both ends
        // files from a to h inclusive on both ends
        // file always comes first, followed by rank
        char startFile = start.charAt(0);
        int startRank = getNumericValue(start.charAt(1));
        char endFile = end.charAt(0);
        int endRank = getNumericValue(end.charAt(1));


        // rooks are not, like pawns, restricted from moving in certain directions
        // and the optimal path from one square to another is exactly as long as the path back to the original square
        // so use Math.abs to allow the bi-directionality of the movement of the rook to show up as valid path lengths
        // int squaresTraversed = Math.abs(endFile - startFile) + Math.abs(endRank - startRank);

        // trivial case: want to move from a given square to that same square
        // optimal number of moves is 0; don't do anything, you're already in the right place
        // e.g., e7 to e7
        // optimal course of action:
        // stay there, making no moves
        if (startFile == endFile && startRank == endRank) {
            return 0;
        }

        // if one of the coordinates matches between the two squares,
        // move along that coordinate until the other matches as well
        // optimal number of moves is 1
        // e.g., e4 to a4
        // optimal course of action:
        // move along the 4th rank, from the e file across the board to the a file
        if (startFile == endFile || startRank == endRank) {
            return 1;
        }

        // if none of the coordinates match, make one move to make the files match, and one move to make the ranks match
        // optimal number of moves is 2
        // e.g., d7 to a4
        // optimal course of action:
        // move along the 7th rank from the d file to the a file to arrive at a7
        // from a7, move along the a file from a7 to a4
        return 2;
    }

    public void addToMoveList(String start, String end) {


        char startFile = getFile(start);
        int endRank = getRank(end);


        // trivial case: do nothing
        if (getOptimalMoves(start, end) == 0) {
            moveList.add(start);
            return;
        }

        // move along ranks OR files
        if (getOptimalMoves(start, end) == 1) {
            moveList.add(start);
            moveList.add(end);
            return;
        }

        // move along ranks AND files
        if (getOptimalMoves(start, end) == 2) {
            moveList.add(start);
            moveList.add(startFile + String.valueOf(endRank));
            moveList.add(end);
        }

    }
}