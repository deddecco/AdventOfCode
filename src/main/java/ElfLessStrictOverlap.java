import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ElfLessStrictOverlap {
     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          int overlaps = processFile(reader);
          System.out.println(overlaps);
     }

     // returns the total number of overlapping sets
     public static int processFile(BufferedReader reader) throws IOException {
          String line;
          int totalOverlaps = 0;
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

               Set<Integer> range1 = populateSet(min1, max1);
               Set<Integer> range2 = populateSet(min2, max2);

               totalOverlaps += findAnOverlap(range1, range2);

          }
          return totalOverlaps;

     }

     // if there is an overlap between the 2 sets, return 1, else return 0
     private static int findAnOverlap(Set<Integer> range1, Set<Integer> range2) {
          int result = 0;
          Set<Integer> overlap = new HashSet<>(range1);
          overlap.retainAll(range2);
          if (overlap.size() >= 1) {
               result = 1;
          }
          return result;
     }

     // for every number from min to and including max, add those numbers to a set
     private static Set<Integer> populateSet(int min, int max) {

          Set<Integer> range = new HashSet<>();
          for (int i = min; i <= max; i++) {
               range.add(i);
          }
          return range;
     }

     // convert strings to ints, put them in int array in same order as when they were strings
     private static int[] convertStringsToInts(String[] lineSplit) {
          int[] converted = new int[lineSplit.length];

          for (int i = 0; i < lineSplit.length; i++) {
               converted[i] = Integer.parseInt(lineSplit[i]);
          }
          return converted;
     }


}