import java.util.ArrayList;

import static java.lang.Character.getNumericValue;
import static java.lang.Math.abs;

public class Pawn {

    /*
     we will assume that a pawn always takes advantage of
     its option to move two squares forward on its first move.
     we will assume that no captures are made. we will not consider special rules
     like en passant or promotions to other pieces upon reaching the opposite back rank
    */
    public static ArrayList<String> moveList = new ArrayList<>();

    public static void main(String[] args) {
        Pawn p1 = new Pawn();
        setup(p1);
    }

    private static void setup(Pawn p1) {
        String color = "Black";
        String start = "a7";
        String end = "a3";

        System.out.println("color: " + color);
        System.out.println("starting square: " + start);
        System.out.println("ending square: " + end);
        System.out.println("start and end on same file?: " + p1.checkIfSameFile(start, end));
        System.out.println("number of optimal moves: " + p1.getOptimalMoves(color, start, end));
        System.out.println("Optimal list of moves from " + start + " to " + end + ": " + moveList);
    }

    /**
     * no captures will be made and
     * the pawn will always exercise its option to move twice
     * if on the 2nd or 7th rank
     * and as of yet not moved
     */
    private int getOptimalMoves(String color, String start, String end) {
        int startRank = getNumericValue(start.charAt(1));
        int endRank = getNumericValue(end.charAt(1));

        char startFile = start.charAt(0);

        Pawn p1 = new Pawn();

        // pawn doesn't need to move at all
        if (startRank == endRank) {
            return 0;
        }


        // if pawns move only forward
        if (p1.directionLegalForColor(color, start, end)) {
            // and if they are not too far back on the board from their perspective for a legal position
            if (p1.startRankLegalForColor(color, start)) {
                // and if they don't capture
                if (p1.checkIfSameFile(start, end)) {
                    /*
                     if the pawn is moving from its home square, the number of optimal moves is 1 less than
                     the distance between the ranks because the first move will advance 2 ranks.
                     if the pawn is moving from beyond its home square to another square,
                     then it can only move one at a time.
                    */

                    if (color.equalsIgnoreCase("white") && startRank == 2) {
                        moveList.add(start);
                        for (int i = startRank + 2; i < endRank; i++) {
                            moveList.add(startFile + String.valueOf(i));
                        }
                        moveList.add(end);
                        return endRank - startRank - 1;
                    } else if (color.equalsIgnoreCase("white") && startRank > 2) {
                        for (int i = startRank; i <= endRank; i++) {
                            moveList.add(startFile + String.valueOf(i));
                        }
                        return endRank - startRank;
                    } else if (color.equalsIgnoreCase("black") && startRank == 7) {
                        moveList.add(start);
                        for (int i = startRank - 2; i > endRank; i--) {
                            moveList.add(startFile + String.valueOf(i));
                        }
                        moveList.add(end);
                        return abs(startRank - endRank) - 1;
                    } else if (color.equalsIgnoreCase("black") && startRank < 7) {
                        for (int i = startRank; i >= endRank; i--) {
                            moveList.add(startFile + String.valueOf(i));
                        }
                        return abs(startRank - endRank);
                    }
                }
            }
        }

        return -1;
    }

    /**
     * files are listed first in chess notation, so checking the first character of the
     * pair of characters that identifies a square is enough to determine whether
     * the optimal path can ramin on the same file.
     * recall that when moving normally, pawns must only ever move forward, but that
     * pawns are the only pieces that capture differently than their normal moves--
     * making accessible by capture the squares diagonally to the left and right one rank
     * in front of the pawn (from its perspective of "forward")
     */
    public boolean checkIfSameFile(String start, String end) {
        return start.charAt(0) == end.charAt(0);
    }

    /**
     * white pawns move from the 2nd to the 8th rank, so the end rank must be greater than the start rank
     * black pawns move the opposite way-- still forward from their perspective,
     * but from the 7th rank to the first rank
     */
    public boolean directionLegalForColor(String color, String start, String end) {
        if (color.equalsIgnoreCase("white")) {
            return getNumericValue(start.charAt(1)) < getNumericValue(end.charAt(1));
        } else {
            return getNumericValue(start.charAt(1)) > getNumericValue(end.charAt(1));
        }
    }


    /**
     * White pawns can never legally be on the first rank
     * Black pawns can never legally be on the eighth rank
     */
    public boolean startRankLegalForColor(String color, String start) {
        if (color.equalsIgnoreCase("white")) {
            return getNumericValue(start.charAt(1)) >= 2;
        } else {
            return getNumericValue(start.charAt(1)) <= 7;
        }
    }

}