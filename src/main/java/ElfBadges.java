import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElfBadges {
     public static void main(String[] args) throws IOException {

          String[][] groups = processInputFile();

          // list storing badges
          List<String> badges = new ArrayList<>();
          // list storing priorities
          // matches 1:1 with list storing badges
          List<Integer> priorities = new ArrayList<>();
          // for each group, add their badge and its priority to those lists
          findBadgeAndPriority(groups, badges, priorities);

          //the answer to the second part of the puzzle
          System.out.println(calculateBadgeSum(priorities));
     }

     /**
      * calculate the running sum of every element in the priorities list
      */
     private static int calculateBadgeSum(List<Integer> priorities) {
          int runningSum = 0;
          for (int priority : priorities) {
               runningSum += priority;
          }
          return runningSum;
     }

     /**
      * find the badge out of the belongings of a group of elves
      * (the only element a group will all share in common)
      * calculate the priority of that badge
      * add the badge to the list of badges
      * add the priority to the list of priorities
      */
     private static void findBadgeAndPriority(String[][] groups, List<String> badges, List<Integer> priorities) {
          for (String[] groupOfElves : groups) {
               String badge = findElfBadge(groupOfElves);
               badges.add(badge);
               priorities.add(getPriority(badge));
          }
     }


     /**
      * for debugging purposes, print out each triple
      * {elf, elf, elf}
      * using one line per triple
      */
     private static void printGroups(String[][] groups) {
          for (String[] groupOfElves : groups) {
               for (String elf : groupOfElves) {
                    System.out.print(elf + "\t");
               }
               System.out.println();
          }
     }

     /**
      * read the file and write its contents to an array with 3 columns-- each row is one triple of elves
      */
     private static String[][] processInputFile() throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;

          // elves travel in triples
          // we have 300 elves, so 100 triples
          String[][] groups = new String[100][3];

          int count = 0;
          int lineNum = 0;

          // read through whole fil
          while ((line = reader.readLine()) != null) {
               // count counts the number of elves
               switch (count % 3) {
                    // if the number of elves is congruent to 0 mod 3
                    // put that elf in index 0 in each triple
                    case 0 -> {
                         groups[lineNum][0] = line;
                         count++;
                    }
                    // if the number of elves is congruent to 1 mod 3
                    // put that elf in index 1 in each triple
                    case 1 -> {
                         groups[lineNum][1] = line;
                         count++;
                    }
                    // if the number of elves is congruent to 2 mod 3
                    // put that elf in index 2 in each triple
                    // and move on to the next triple, allowing a restart at index 0
                    default -> {
                         groups[lineNum][2] = line;
                         count++;
                         lineNum++;
                    }
               }
          }
          return groups;
     }


     /**
      * follow the same priority rules as the other challenge from today to find the priority of the badge common to a group
      * lowercase letters get priorities 1 to 26
      * uppercase letters get priorities 27 to 52
      */
     private static int getPriority(String badge) {
          int priority = 0;
          int badgeCharNum = badge.charAt(0);
          if (badgeCharNum >= 65 && badgeCharNum <= 90) {
               priority = (int) badgeCharNum - 38;
          } else if (badgeCharNum <= 122 && badgeCharNum >= 97) {
               priority = (int) badgeCharNum - 96;
          }
          return priority;
     }


     /**
      * given a triple of elves, elf0 is the first elf, elf1 is the second elf, and elf2 is the third elf
      * elf0set is the set of characters belonging to elf0,
      * elf1set is the set of characters belonging to elf1,
      * elf2set is the set of characters belonging to elf2,
      * find the commonality between elf0set and elf1set, and put that in its own set
      * the find the commonality between el2set and that set, and that will be the badge
      */
     private static String findElfBadge(String[] groupOfElves) {

          String elf0 = groupOfElves[0];
          String elf1 = groupOfElves[1];
          String elf2 = groupOfElves[2];

          Set<Character> elf0set = new HashSet<>();
          Set<Character> elf1set = new HashSet<>();
          Set<Character> elf2set = new HashSet<>();

          for (int i = 0; i < elf0.length(); i++) {
               elf0set.add(elf0.charAt(i));
          }
          for (int i = 0; i < elf1.length(); i++) {
               elf1set.add(elf1.charAt(i));
          }
          for (int i = 0; i < elf2.length(); i++) {
               elf2set.add(elf2.charAt(i));
          }

          Set<Character> commonElf0Elf1 = getCharsInCommon(elf0set, elf1set);
          Set<Character> commonAllElvesInGroup = getCharsInCommon(commonElf0Elf1, elf2set);
          ArrayList<Character> badgeList = new ArrayList<>(commonAllElvesInGroup);

          return badgeList.get(0).toString();
     }


     /**
      * given 2 sets, return a set that contains the characters in common between the two inputs
      */
     private static Set<Character> getCharsInCommon(Set<Character> elf0set, Set<Character> elf1set) {
          Set<Character> commonElf0Elf1 = new HashSet<>();
          for (Character value : elf0set) {
               for (Character character : elf1set) {
                    if (value.equals(character)) {
                         commonElf0Elf1.add(value);
                         break;
                    }
               }
          }
          return commonElf0Elf1;
     }
}