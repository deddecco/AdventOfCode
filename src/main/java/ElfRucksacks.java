import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElfRucksacks {

     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";

          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          String line;

          int i = 0;

          String[][] splitLineArray = new String[300][2];
          int totalPriority = 0;

          // while there are still lines to read
          while ((line = reader.readLine()) != null) {

               // split each line in half
               // put the first half in splitLineArray[0]
               // and the second half in splitLineArray[1]
               createSplitArray(line, splitLineArray[i]);

               List<Character> charSetLeft = new ArrayList<>();
               List<Character> charSetRight = new ArrayList<>();

               // create a set of characters for each half
               populateCharSets(charSetLeft, charSetRight, splitLineArray[i]);

               // create a set to store common characters between the two half-sets
               Set<Character> commonSet = new HashSet<>();

               // if a character appears in both half-sets, add it to the common set
               populateCommonSet(charSetLeft, charSetRight, commonSet);

               // the elves need the total priorities of all the rows of text
               totalPriority = calculateTotalPriority(totalPriority, commonSet);
               i++;
          }

          System.out.println(totalPriority);
     }

     private static int calculateTotalPriority(int totalPriority,
                                               Set<Character> commonSet) {
          int priority = 0;
          for (Character character : commonSet) {
               // lowercase letters have priorities 1-26 and uppercase letters have priorities 27-52
               if (character >= 65 && character <= 90) {
                    priority = (int) character - 38;
               } else if (character <= 122 && character >= 97) {
                    priority = (int) character - 96;
               }
               // add the priority to the running total
               totalPriority += priority;
          }
          return totalPriority;
     }

     private static void populateCommonSet(List<Character> charSetLeft,
                                           List<Character> charSetRight,
                                           Set<Character> commonSet) {
          for (Character value : charSetLeft) {
               for (Character character : charSetRight) {
                    if (value.equals(character)) {
                         commonSet.add(value);
                         break;
                    }
               }
          }
     }

     private static void populateCharSets(List<Character> charSetLeft,
                                          List<Character> charSetRight,
                                          String[] splitLineArray) {
          for (int j = 0; j < splitLineArray[0].length(); j++) {
               charSetLeft.add(splitLineArray[0].charAt(j));
          }
          for (int j = 0; j < splitLineArray[1].length(); j++) {
               charSetRight.add(splitLineArray[1].charAt(j));
          }
     }

     private static void createSplitArray(String line,
                                          String[] splitLineArray) {
          // the first half of the line goes to the first cell in the row
          splitLineArray[0] = line.substring(0, line.length() / 2);
          // and the second half goes to the second cell
          splitLineArray[1] = line.substring(line.length() / 2);
     }
}