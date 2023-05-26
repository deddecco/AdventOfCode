import java.util.Stack;

public class Knight extends Piece {


    // class represents one of 64 squares on the board
    static class Square {

        // every square has a name, a file, and a rank
        char file;
        int rank;
        String name;

        public Square(char file, int rank) {
            this.file = file;
            this.rank = rank;
            this.name = file + String.valueOf(rank);
        }

        // the string representation of the square is its name
        @Override
        public String toString() {
            return name;
        }

    }


    // given a name
    // if the file is a b c d e f g h
    // AND
    // if the rank is 1 2 3 4 5 6 7 8
    // the square exists on the board
    // otherwise, the square does not exist
    public static boolean onBoard(String name) {

        boolean squareFileOK = name.charAt(0) >= 'a' && name.charAt(0) <= 'h';
        boolean squareRankOK = Integer.parseInt(name.substring(1)) >= 1 && Integer.parseInt(name.substring(1)) <= 8;

        return squareFileOK && squareRankOK;
    }

    public static void main(String[] args) {
        Knight n1 = new Knight();

        System.out.println("Start: a8");
        System.out.println("End: h1");
        int pathLength = n1.KnightShortestPath(new Square('a', 8), new Square('h', 1));
        System.out.println("Path length: " + pathLength);

    }


    public int KnightShortestPath(Square start, Square end) {

        // set up the arrays that will determine how much the knight moves in the two directions with each move it attempts
        int[] deltaRanks = new int[]{-1, -2, -2, -1, 1, 2, 2, 1};
        int[] deltaFiles = new int[]{-2, -1, 1, 2, -2, -1, 1, 2};


        // create a stack of squares
        Stack<Square> squareStack = new Stack<>();
        // push the start onto the stack
        squareStack.push(start);

        // 8 by 8 board = 8 by 8 array to track if already visited square
        boolean[][] visited = new boolean[8][8];
        // set start to visited, everything else unvisited
        visited[convertRank(start.rank)][convertFileToNumFromLetter(start.file)] = true;

        // path lengths from the start to a certain square get stored in that square's position in another 8by8 array of ints
        int[][] knightMove = new int[8][8];


        while (!squareStack.isEmpty()) {
            Square z = squareStack.pop();
            // if the square that was popped is the destination square, return the distace
            if (z.file == end.file && z.rank == end.rank) {
                return knightMove[convertRank(end.rank)][convertFileToNumFromLetter(end.file)];
            }
            // otherwise

            // make all 8 possible moves even if illegal-- we'll deal with illegal moves in the next if-black 
            for (int i = 0; i < 8; i++) {

                // create new files and ranks
                char newFile = (char) (z.file + deltaFiles[i]);
                int newRank = z.rank + deltaRanks[i];

                // from thsese new coordinates get a new name
                String newName = newFile + String.valueOf(newRank);


                // check if the newly created square can exist, and if so, if it has been visited
                if (onBoard(newName)) if (!visited[convertRank(newRank)][convertFileToNumFromLetter(newFile)]) {
                    // if a square can exist and hasn't been visited, visit it
                    visited[convertRank(newRank)][convertFileToNumFromLetter(newFile)] = true;
                    // the distance to the square you just visited is the distance to get where you came from, plus 1
                    knightMove[convertRank(newRank)][convertFileToNumFromLetter(newFile)] = knightMove[convertRank(z.rank)][convertFileToNumFromLetter(z.file)] + 1;

                    // push the visited square to the stack
                    squareStack.push(new Square(newFile, newRank));

                }
            }
        }

        // if no path exists, return a path length of -1
        return -1;
    }

    public static int convertRank(int rank) {
        return 8 - rank;
    }
}