import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class ElfClimber {

     static char[][] heightGrid = new char[41][101];

     public static void main(String[] args) throws IOException {

          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          populateHeightGrid(reader);

          char[][] visitedGrid = new char[heightGrid.length][heightGrid[0].length];

          // find and save the starting coordinates, and change 'S' to 'a'
          int[] startingCoordinates = findStartingCoordinates();
          int row = startingCoordinates[0];
          int col = startingCoordinates[1];
          heightGrid[row][col] = 'a';

          // mark the starting point as having been visited
          visitedGrid[row][col] = 'O';


          // find and save the ending coordinates, and change 'E' to 'z'
          int[] endingCoordinates = findEndingCoordinates();
          int endRow = endingCoordinates[0];
          int endCol = endingCoordinates[1];
          heightGrid[endRow][endCol] = 'z';


          long start = System.currentTimeMillis();
          // what we want = the number of steps in the optimaL path from the start position to the end position
          int result = pathFinder(visitedGrid, row, col, endRow, endCol);
          long end = System.currentTimeMillis();

          long elapsed = end - start;

          System.out.println("result: " + result);
          System.out.println("elapsed = " + elapsed);
     }

     private static void populateHeightGrid(BufferedReader reader) throws IOException {
          String line;
          int i = 0;
          while ((line = reader.readLine()) != null) {
               char[] lineArray = line.toCharArray();
               heightGrid[i] = lineArray;
               i++;
          }
     }

     private static int pathFinder(char[][] visitedGrid, int row, int col, int rowE, int colE) {
          int left = MAX_VALUE;
          int right = MAX_VALUE;
          int up = MAX_VALUE;
          int down = MAX_VALUE;


          // base case: current position = end position
          // no remaining distance, return 0
          if ((row == rowE) && (col == colE)) {
               System.out.println("Path in visited grid: ");
               printGrid(visitedGrid);
               return 0;
          } else {
               // the path from the left is 1 + the length of the path from the square 1 to left
               // only take this path if the destination is unvisited--
               // the path is obviously not optimal if it revisits a visited square
               if (isValidMove(row, col - 1, heightGrid[row][col], visitedGrid)) {
                    System.out.println("calling pathfinder left to [" + row + "][" + (col - 1) + "]");
                    char[][] leftVisited = copyGrid(visitedGrid);
                    int newCol = col - 1;
                    leftVisited[row][newCol] = '<';
                    printGrid(leftVisited);
                    int pathLengthLeft = pathFinder(leftVisited, row, newCol, rowE, colE);
                    if (pathExists(pathLengthLeft)) {
                         left = 1 + pathLengthLeft;
                    }
               }
               // the path from the right is 1 + the length of the path from the square 1 to right
               // only take this path if the destination is unvisited--
               // the path is obviously not optimal if it revisits a visited square
               if (isValidMove(row, col + 1, heightGrid[row][col], visitedGrid)) {
                    System.out.println("calling pathfinder right to [" + row + "][" + (col + 1) + "]");
                    char[][] rightVisited = copyGrid(visitedGrid);
                    int newCol = col + 1;
                    rightVisited[row][newCol] = '>';
                    printGrid(rightVisited);
                    int pathLengthRight = pathFinder(rightVisited, row, newCol, rowE, colE);
                    if (pathExists(pathLengthRight)) {
                         right = 1 + pathLengthRight;
                    }
               }
               // the path from the top down is 1 + the length of the path from the square 1 row below
               // only take this path if the destination is unvisited--
               // the path is obviously not optimal if it revisits a visited square
               if (isValidMove(row + 1, col, heightGrid[row][col], visitedGrid)) {
                    System.out.println("calling pathfinder right to [" + (row + 1) + "][" + col + "]");
                    char[][] downVisited = copyGrid(visitedGrid);
                    int newRow = row + 1;
                    downVisited[newRow][col] = 'v';
                    printGrid(downVisited);
                    int pathLengthDown = pathFinder(downVisited, newRow, col, rowE, colE);
                    if (pathExists(pathLengthDown)) {
                         down = 1 + pathLengthDown;
                    }
               }
               // the path from the bottom up is 1 + the length of the path from the square 1 row above
               // only take this path if the destination is unvisited--
               // the path is obviously not optimal if it revisits a visited square
               if (isValidMove(row - 1, col, heightGrid[row][col], visitedGrid)) {
                    System.out.println("calling pathfinder right to [" + (row - 1) + "][" + col + "]");
                    char[][] upVisited = copyGrid(visitedGrid);
                    int newRow = row - 1;
                    upVisited[newRow][col] = '^';
                    printGrid(upVisited);
                    int pathLengthUp = pathFinder(upVisited, newRow, col, rowE, colE);
                    if (pathExists(pathLengthUp)) {
                         up = 1 + pathLengthUp;
                    }
               }

               // the minimum optimal path is the shortest of the 4 paths
               return min(min(up, down), min(left, right));
          }
     }

     private static void printGrid(char[][] grid) {
          for (char[] rowarray : grid) {
               for (char cell : rowarray) {
                    System.out.print(cell + " ");
               }
               System.out.println();
          }
     }

     private static char[][] copyGrid(char[][] inputGrid) {
          char[][] gridCopy = new char[inputGrid.length][inputGrid[0].length];
          int i = 0;
          for (char[] chars : inputGrid) {
               System.arraycopy(chars, 0, gridCopy[i], 0, chars.length);
               i++;
          }
          return gridCopy;
     }

     public static int[] findStartingCoordinates() {
          int[] rowCol = new int[2];
          for (int i = 0; i < heightGrid.length; i++) {
               for (int j = 0; j < heightGrid[i].length; j++) {
                    if (heightGrid[i][j] == 'S') {
                         rowCol[0] = i;
                         rowCol[1] = j;
                         return rowCol;
                    }
               }
          }

          return new int[]{-1, -1};
     }

     public static int[] findEndingCoordinates() {
          int[] result = null;
          int[] rowCol = new int[2];
          for (int i = 0; i < heightGrid.length; i++) {
               for (int j = 0; j < heightGrid[i].length; j++) {
                    if (heightGrid[i][j] == 'E') {
                         rowCol[0] = i;
                         rowCol[1] = j;
                         result = rowCol;
                         break;
                    }
               }
               if (result != null) {
                    break;
               }
          }
          if (result == null) {
               result = new int[]{-1, -1};
          }

          return result;
     }


     /**
      * determine if a position is in bounds, and only if so, check if it is univisited and complies with height requirements; if all checks passed, move is valid, otherwise not
      */
     public static boolean isValidMove(int row, int col, char height, char[][] visitedGrid) {
          boolean result = false;
          boolean colInBounds = (col >= 0) && (col < heightGrid[0].length);
          boolean rowInBounds = (row >= 0) && (row < heightGrid.length);
          boolean squareInBounds = rowInBounds && colInBounds;



          /* only run subsequent checks if square is in bounds-- if not in bounds, not valid move */
          if (squareInBounds) {
               boolean isUnvisited = (visitedGrid[row][col] == '\u0000');
               boolean isValidHeight = heightGrid[row][col] <= height + 1;
               result = isUnvisited && isValidHeight;
          }

          return result;
     }


     /**
      * true if path length is not infinite, false otherwise
      */
     private static boolean pathExists(int pathLength) {
          return pathLength < MAX_VALUE;
     }
}
