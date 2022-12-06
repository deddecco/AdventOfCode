import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ElfMarkerLocator {

     // a marker is a sequence of 4 characters such that all four are unique
     // find the first such sequence in a string that contains the contents of a file
     public static int findMarker(String fileContents) {
          // looking through the whole string
          for (int i = 0; i < fileContents.length() - 3; i++) {
               // isolate 4 characters at a time
               String s = fileContents.substring(i, i + 4);
               // create a set
               Set<Character> characterSet = new HashSet<>();
               // populate that set with the characters in the string
               for (Character c : s.toCharArray()) {
                    characterSet.add(c);
               }
               // if the size of the set is 4,
               // then there are no duplicate characters,
               // all characters are unique,
               // and we have found a marker
               if (characterSet.size() == 4) {
                    return i + 4;
               }
          }
          return 0;
     }


     // given a path, read in the text file at that path into a single string and return it
     public static String getFile(String filePath) throws IOException {
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          String line;

          StringBuilder fileContents = new StringBuilder();

          while ((line = reader.readLine()) != null) {
               fileContents.append(line);
          }

          return fileContents.toString();
     }

     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          String contents = getFile(filePath);

          // calculate the location of the first marker
          int index = findMarker(contents);
          // print out the answer to the puzzle
          System.out.println(index);
     }
}
