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
public class King {
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

        char startFile = start.charAt(0);
        char endFile = end.charAt(0);

        int startRank = getNumericValue(start.charAt(1));
        int endRank = getNumericValue(end.charAt(1));


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
        boolean kingMove1AsBishop = Math.abs(start.charAt(0) - bestIntermediateAsQueen.charAt(0))
                == Math.abs(start.charAt(1) - bestIntermediateAsQueen.charAt(1));

        boolean kingMove2AsBishop = Math.abs(end.charAt(0) - bestIntermediateAsQueen.charAt(0))
                == Math.abs(end.charAt(1) - bestIntermediateAsQueen.charAt(1));

        // rook moves
        boolean kingMove1AsRook = Math.abs(start.charAt(0) - bestIntermediateAsQueen.charAt(0)) == 0
                || Math.abs(start.charAt(1) - bestIntermediateAsQueen.charAt(1)) == 0;

        boolean kingMove2AsRook = Math.abs(end.charAt(0) - bestIntermediateAsQueen.charAt(0)) == 0
                || Math.abs(end.charAt(1) - bestIntermediateAsQueen.charAt(1)) == 0;

        // for Bishops
        boolean lowRankToHighRank = endRank > startRank;
        boolean highRankToLowRank = endRank < startRank;

        // for Rooks
        boolean furtherIntoQueenside = endFile < startFile;
        boolean furtherIntoKingside = endFile > startFile;
        boolean furtherUpFile = endRank > startRank;
        boolean furtherDownFile = endRank < startRank;

        char file = startFile;
        int rank = startRank;

        char interFile = bestIntermediateAsQueen.charAt(0);
        int interRank = getNumericValue(bestIntermediateAsQueen.charAt(1));
        if (kingMove1AsBishop) {
            letKingMoveAsBishop(interFile,
                    interRank,
                    lowRankToHighRank,
                    highRankToLowRank,
                    file,
                    rank);
        } else if (kingMove2AsBishop) {
            rank = interRank;
            file = interFile;
            letKingMoveAsBishop(endFile,
                    endRank,
                    lowRankToHighRank,
                    highRankToLowRank,
                    file,
                    rank);
        } else if (kingMove1AsRook) {
            letKingMoveAsRook(furtherIntoQueenside,
                    furtherIntoKingside,
                    furtherUpFile,
                    furtherDownFile,
                    file,
                    rank);
        } else if (kingMove2AsRook) {
            rank = interRank;
            file = interFile;
            letKingMoveAsRook(furtherIntoQueenside,
                    furtherIntoKingside,
                    furtherUpFile,
                    furtherDownFile,
                    file,
                    rank);
        }
        moveList.add(end);
        return moveList.size();
    }

    public void letKingMoveAsBishop(char endFile, int endRank,
                                    boolean lowRankToHighRank, boolean highRankToLowRank,
                                    char file, int rank) {
        if (lowRankToHighRank) {
            while (file <= endFile && rank <= endRank) {
                moveList.add(file + valueOf(rank));
                file++;
                rank++;
            }
        } else if (highRankToLowRank) {
            while (file >= endFile && rank >= endRank) {
                moveList.add(file + valueOf(rank));
                file--;
                rank--;
            }
        }
    }

    public void letKingMoveAsRook(boolean furtherIntoQueenside, boolean furtherIntoKingside,
                                  boolean furtherUpFile, boolean furtherDownFile,
                                  char file, int rank) {
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