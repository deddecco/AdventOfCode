import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ElfSectionOverlapPairsCounter {

     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          int surroundedRanges = 0;
          surroundedRanges = processFile(reader, surroundedRanges);
          System.out.println(surroundedRanges);

     }

     private static int processFile(BufferedReader reader, int surroundedRanges) throws IOException {
          String line;
          while ((line = reader.readLine()) != null) {
               // split line by non-digits into array
               String[] lineSplit = line.split("\\D+");
               // initialize array to store ints once we parse
               int[] converted = convertStringsToInts(lineSplit);

               // first and second numbers define first range
               int min1 = converted[0];
               int max1 = converted[1];
               // third and fourth numbers define second range
               int min2 = converted[2];
               int max2 = converted[3];

               boolean oneRangeContainsTheOther = checkRanges(min1, max1, min2, max2);
               surroundedRanges = count(surroundedRanges, oneRangeContainsTheOther);
          }
          return surroundedRanges;
     }

     private static int count(int surroundedRanges, boolean oneRangeContainsTheOther) {
          if (oneRangeContainsTheOther) {
               surroundedRanges++;
          }
          return surroundedRanges;
     }

     // convert strings to ints, put them in array in same order as when they were strings
     private static int[] convertStringsToInts(String[] lineSplit) {
          int[] converted = new int[lineSplit.length];

          for (int i = 0; i < lineSplit.length; i++) {
               converted[i] = Integer.parseInt(lineSplit[i]);
          }
          return converted;
     }

     private static boolean checkRanges(int min1, int max1, int min2, int max2) {
          // this is true if min1 min2 max2 max1
          boolean range1ContainsRange2 = (min1 <= min2) && (min2 <= max2) && (max2 <= max1);
          // this is true if min2 min1 max1 max2
          boolean range2ContainsRange1 = (min2 <= min1) && (min1 <= max1) && (max1 <= max2);
          // this is true if only one of those previous booleans is true
          return range1ContainsRange2 || range2ContainsRange1;
     }
}