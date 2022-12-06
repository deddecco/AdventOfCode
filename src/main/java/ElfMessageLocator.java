import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ElfMessageLocator {


     // exactly the same logic as the first problem, only looking for strings of length 14 rather than length 4
     public static int findMarker(String fileContents) {
          for (int i = 0; i < fileContents.length() - 13; i++) {
               String s = fileContents.substring(i, i + 14);
               Set<Character> characterSet = new HashSet<>();
               for (Character c : s.toCharArray()) {
                    characterSet.add(c);
               }
               if (characterSet.size() == 14) return i + 14;
          }
          return 0;
     }


     // read the whole file found at the path given as a parameter into a single string
     public static String getFile(String filePath) throws IOException {
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          String line;

          StringBuilder fileContents = new StringBuilder();

          while ((line = reader.readLine()) != null) {
               fileContents.append(line);
          }

          return fileContents.toString();
     }

     // find the index of the first message in the file given looking for messages of length 14
     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          String contents = getFile(filePath);
/*
          System.out.println(contents);*/
          int index = findMarker(contents);
          System.out.println(index);
     }
}