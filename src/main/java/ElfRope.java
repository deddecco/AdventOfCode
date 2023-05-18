import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class ElfRope {
     public static void main(String[] args) throws IOException {


          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;

          int x = 0;
          int y = 0;
          System.out.println("(" + x + "," + y + ") ");

          int headDistance = 0;
          int tailDistance = 0;

          // read the file line by line
          while ((line = reader.readLine()) != null) {
               // if line is not blank
               if (!line.equals("")) {
                    // if line starts with x+
                    if (line.contains("x+")) {
                         // moving head right by the amount of this parse
                         int xOffset = parseInt(line.substring(2));
                         // tail needs to stay within 1 of the head
                         int tailOffset = 0;
                         if (xOffset == 1) {
                              tailOffset = 1;
                         } else {
                              tailOffset = xOffset - 1;
                         }
                         // adjust x position
                         x += xOffset;
                    } // if line starts wth x-
                    else if (line.contains("x-")) {
                         // moving head left by amount of parse
                         int tailOffset = 0;
                         int xOffset = parseInt(line.substring(2));
                         // adjust x position
                         x -= xOffset;
                         headDistance += xOffset;
                         // keep tail within 1 of head
                         if (xOffset == 1) {
                              tailOffset = 1;
                         } else {
                              tailOffset = xOffset - 1;
                         }
                         tailDistance += tailOffset;
                    } // if line starts with y+
                    else if (line.contains("y+")) {
                         // moving up by amount of parse
                         int tailOffset = 0;
                         int yOffset = parseInt(line.substring(2));
                         y += yOffset;
                         headDistance += yOffset;
                         // keep tail within 1 of head
                         if (yOffset == 1) {
                              tailOffset = 1;
                         } else {
                              tailOffset = yOffset + 1;
                         }
                         tailDistance += tailOffset;
                    } else {
                         // only remaining option is to start the line with y-
                         int tailOffset = 0;
                         // in this case, moving down by amount of parse
                         int yOffset = parseInt(line.substring(2));
                         y -= yOffset;
                         headDistance += yOffset;
                         // move tail to stay within 1 of the head
                         if (yOffset == 1) {
                              tailOffset = 1;
                         } else {
                              tailOffset = yOffset + 1;
                         }
                         tailDistance += tailOffset;
                    }
               }
               System.out.println("(" + x + "," + y + ") ");
          }
          System.out.println("headDistance = " + headDistance);
          System.out.println("tailDistance = " + tailDistance);
          System.out.println();
     }
}
