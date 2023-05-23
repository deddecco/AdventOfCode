import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.getNumericValue;
import static java.lang.String.valueOf;

/**
 * can move in any direction one square at a time
 * no other pieces on the board, so no forced moves
 * due to evading check by capturing the checking piece, running away, or interposing the check
 * no castling
 * optimal length for king is number of squares of shortest optimal path for queen
 */
public class King extends Piece {
    public static ArrayList<String> moveList = new ArrayList<>();

    public static void main(String[] args) {
        King k1 = new King();
        setup(k1);
    }

    public static void setup(King k1) {
        String start = "a1";
        String end = "h4";
        System.out.println("start square: " + start);
        System.out.println("end square: " + end);
        System.out.println("number of optimal moves: " + k1.getOptimalMoves(start, end));
        System.out.println("Optimal list of moves from " + start + " to " + end + ": " + moveList);
    }

    public int getOptimalMoves(String start, String end) {

        char startFile = getFile(start);
        char endFile = getFile(end);

        int startRank = getRank(start);
        int endRank = getRank(end);


        // treat the King as a Queen for the purposes of finding the optimal route--
        // the optimal routes will be the same, only the Queen can do it in 2 moves,
        // but the King needs to go one square at a time along the length of the route,
        // so the shortest 2-move Queen route is the optimal king route
        Queen q1 = new Queen();

        char[][] visibleFromStartAsQueen = q1.createQueenArrayOfVisibleSquares(startFile, startRank);
        char[][] visibleFromEndAsQueen = q1.createQueenArrayOfVisibleSquares(endFile, endRank);
        List<String> intermediates = q1.findCommonSquare(visibleFromStartAsQueen, visibleFromEndAsQueen);
        String bestIntermediateAsQueen = q1.findBestIntermediate(start, intermediates, end);


        // bishop moves
        boolean kingMove1AsBishop = Math.abs(start.charAt(0) - bestIntermediateAsQueen.charAt(0)) == Math.abs(start.charAt(1) - bestIntermediateAsQueen.charAt(1));
        boolean kingMove2AsBishop = Math.abs(end.charAt(0) - bestIntermediateAsQueen.charAt(0)) == Math.abs(end.charAt(1) - bestIntermediateAsQueen.charAt(1));

        // rook moves
        boolean kingMove1AsRook = Math.abs(start.charAt(0) - bestIntermediateAsQueen.charAt(0)) == 0 || Math.abs(start.charAt(1) - bestIntermediateAsQueen.charAt(1)) == 0;
        boolean kingMove2AsRook = Math.abs(end.charAt(0) - bestIntermediateAsQueen.charAt(0)) == 0 || Math.abs(end.charAt(1) - bestIntermediateAsQueen.charAt(1)) == 0;


        // for Bishops
        char interFile = bestIntermediateAsQueen.charAt(0);
        int interRank = getNumericValue(bestIntermediateAsQueen.charAt(1));

        boolean lowRankToHighRank = startRank <= interRank;
        boolean lowFileToHighFile = startFile <= interFile;

        // for Rooks
        // as White, moving right to left
        boolean furtherIntoQueenside = endFile < startFile;
        // as White, moving left to right
        boolean furtherIntoKingside = endFile > startFile;


        // as White, moving away from where the pieces would be set up for a normal game
        boolean furtherUpFile = endRank > startRank;
        // as White, moving toward where the pieces would be set up for a normal game
        boolean furtherDownFile = endRank < startRank;

        char file = startFile;
        int rank = startRank;

        // the intermediate files and ranks come from the calculated best intermediate square if the king were a queen

        if (kingMove1AsBishop) {
            letKingMoveAsBishop(interFile, interRank, lowRankToHighRank, lowFileToHighFile, file, rank);
        }
        if (kingMove1AsRook) {
            letKingMoveAsRook(furtherIntoQueenside, furtherIntoKingside, furtherUpFile, furtherDownFile, file, rank);
        }
        if (kingMove2AsBishop) {
            rank = interRank;
            file = interFile;

            lowRankToHighRank = interRank <= endRank;
            lowFileToHighFile = interFile <= endFile;

            letKingMoveAsBishop(endFile, endRank, lowRankToHighRank, lowFileToHighFile, file, rank);
        }
        if (kingMove2AsRook) {
            rank = interRank;
            file = interFile;

            letKingMoveAsRook(furtherIntoQueenside, furtherIntoKingside, furtherUpFile, furtherDownFile, file, rank);
        }
        moveList.add(end);
        return moveList.size();
    }

    public void letKingMoveAsBishop(char endFile, int endRank, boolean lowRankToHighRank, boolean lowFileToHighFile, char file, int rank) {
        while (file != endFile || rank != endRank) {
            String current = file + valueOf(rank);
            moveList.add(current);
            if (rank != endRank) {
                if (lowRankToHighRank) {
                    rank++;
                } else {
                    rank--;
                }
            }
            if (file != endFile) {
                if (lowFileToHighFile) {
                    file++;
                } else {
                    file--;
                }
            }
        }
    }

    public void letKingMoveAsRook(boolean furtherIntoQueenside, boolean furtherIntoKingside, boolean furtherUpFile, boolean furtherDownFile, char file, int rank) {
        if (furtherIntoQueenside) {
            while (file >= 'a') {
                moveList.add(file + valueOf(rank));
                file--;
            }
        } else if (furtherIntoKingside) {
            while (file <= 'h') {
                moveList.add(file + valueOf(rank));
                file++;
            }
        } else if (furtherDownFile) {
            while (rank >= 1) {
                moveList.add(file + valueOf(rank));
                rank--;
            }
        } else if (furtherUpFile) {
            while (rank <= 8) {
                moveList.add(file + valueOf(rank));
                rank++;
            }
        }
    }
}