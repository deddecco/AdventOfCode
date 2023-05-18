import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElfVisibleTrees {
     static int[][] ints = new int[5][5];

     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;

          int n = 5;
          int m = 5;


          int[][] ints = new int[n][m];
          int row = 0;
          int col;
          while ((line = reader.readLine()) != null) {
               int[] lineArray = new int[m];

               for (int i = 0; i < line.length(); i++) {
                    lineArray[i] = Integer.parseInt(String.valueOf(line.charAt(i)));
               }
               for (col = 0; col < lineArray.length; col++) {
                    ints[row][col] = lineArray[col];
               }
               row++;
          }


          for (int[] intRow : ints) {
               System.out.println(Arrays.toString(intRow));
          }

          int ans = 0;
          for (int i = 0; i < n; i++) {
               for (int j = 0; j < m; j++) {
                    int height = ints[i][j];

                    List<Integer> leftList = new ArrayList<>();
                    List<Integer> rightList = new ArrayList<>();
                    List<Integer> topList = new ArrayList<>();
                    List<Integer> bottomList = new ArrayList<>();

                    for (int colPos = 0; colPos < j; colPos++) {
                         leftList.add(ints[i][colPos]);
                    }
                    boolean visibleFromLeft = (leftList.size() == 0 || height >= Collections.max(leftList));

                    for (int colPos = j + 1; colPos < m; colPos++) {
                         rightList.add(ints[i][colPos]);
                    }
                    boolean visibleFromRight = (rightList.size() == 0 || height >= Collections.max(rightList));

                    for (int rowPos = 0; rowPos < i; rowPos++) {
                         topList.add(ints[rowPos][j]);
                    }
                    boolean visibleFromTop = (topList.size() == 0 || height >= Collections.max(topList));

                    for (int rowPos = i + 1; rowPos < n; rowPos++) {
                         bottomList.add(ints[rowPos][j]);
                    }
                    boolean visibleFromBottom = (bottomList.size() == 0 || height >= Collections.max(bottomList));


                    boolean visible = visibleFromBottom || visibleFromLeft || visibleFromRight || visibleFromTop;

                    if (!visible) {
                         continue;
                    } else {
                         ans++;
                    }

                    System.out.println("leftList: = " + leftList);
                    System.out.println("rightList = " + rightList);
                    System.out.println("topList = " + topList);
                    System.out.println("bottomList = " + bottomList);
                    System.out.println();
                    System.out.println();
                    System.out.println();
               }
          }

          System.out.println(ans);
     }

}