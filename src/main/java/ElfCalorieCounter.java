import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElfCalorieCounter {

     public static void main(String[] args) throws IOException {

          // create a reader object to read through the input file
          BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt"));

          // calculate all the calorie counts of the elves
          List<Integer> elfCalories = calculateCalories(reader);
          reader.close();


          // sort, then reverse the list
          prepareList(elfCalories);

          // find the sum of the top 3 best-performing elves
          int top3Cals = getSumOfTopNElements(elfCalories, 3);

          // display the calorie counts of all elves
          System.out.println("Calories per elf: " + elfCalories);

          // display the calorie count of the 3 most productive, summed together
          System.out.println("The calories collected by the top 3 most productive elves: " + top3Cals);
     }

     private static int getSumOfTopNElements(List<Integer> elfCalories, int maxIndex) {
          int topNElfCals = 0;
          // given a sorted list, take the running sum of the elements up to maxIndex
          for (int i = 0; i < maxIndex; i++) {
               topNElfCals += elfCalories.get(i);
          }
          // return that sum
          return topNElfCals;
     }

     private static void prepareList(List<Integer> elfCalories) {
          Collections.sort(elfCalories);
          Collections.reverse(elfCalories);
     }

     /**
      * @param reader BufferedReader object reads file line by line
      * @return a list containing the calorie counts of various elves
      */
     private static List<Integer> calculateCalories(BufferedReader reader) throws IOException {
          String line;
          List<Integer> elfCalories = new ArrayList<>();
          int totalCalories = 0;

          // while there are still lines of calories
          while ((line = reader.readLine()) != null) {
               int calories;
               // when you hit a blank line, a given elf is done collecting calories
               // add its total to the list and zero out the total to get ready for the next elf
               if (line.equals("")) {
                    elfCalories.add(totalCalories);
                    totalCalories = 0;
               }
               // but if the same elf is still collecting calories, then the elf's running total of calories
               // is how many calories they've collected up until now, plus the calories they just collected on this line
               else {
                    calories = Integer.parseInt(line);
                    totalCalories += calories;
               }
          }
          // return a list of calorie counts where 1 number = the production of 1 elf
          return elfCalories;
     }
}
