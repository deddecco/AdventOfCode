public class Piece {
    /**
     * files in chess notation = columns in Java notation.
     * if viewing as White, then a file on left, h file on right.
     * if viewing as Black, then h file on left, a file on right
     */
    public static int convertFileToNumFromLetter(char file) {

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

    public static void populateBlank(char[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 'I';
            }
        }
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

    public static char getFile(String square) {
        return square.charAt(0);
    }

    public static int getRank(String square) {
        return Character.getNumericValue(square.charAt(1));
    }

}