import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

public class ElfStackReverse {

     public static void main(String[] args) throws IOException {
          Stack<Character>[] stacks = createNewStackArray();

          for (Stack<Character> stack : stacks) {
               System.out.println(stack);
          }

          System.out.println();
          System.out.println();
          System.out.println();

          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;

          String[][] temp = new String[503][3];

          int i = 0;
          while ((line = reader.readLine()) != null) {
               temp[i] = line.split("\\D+");
               i++;
          }

          int[][] instructions = new int[503][3];

          i = 0;
          int j = 0;
          for (String[] row : temp) {
               for (String instruction : row) {
                    instructions[i][j] = Integer.parseInt(instruction);
                    j++;
               }
               j = 0;
               i++;
          }

          for (int[] row : instructions) {
               int quantity = row[0];
               int stackFrom = row[1] - 1;
               int stackTo = row[2] - 1;

               modifiedPushPop(quantity, stacks[stackFrom], stacks[stackTo]);
          }


          for (Stack<Character> stack : stacks) {
               System.out.println(stack);
          }
     }

     private static Stack<Character>[] createNewStackArray() {
          Stack<Character>[] stacks;
          stacks = new Stack[9];

          Stack<Character> stack0 = setStack0();
          stacks[0] = stack0;
          Stack<Character> stack1 = setStack1();
          stacks[1] = stack1;
          Stack<Character> stack2 = setStack2();
          stacks[2] = stack2;
          Stack<Character> stack3 = setStack3();
          stacks[3] = stack3;
          Stack<Character> stack4 = setStack4();
          stacks[4] = stack4;
          Stack<Character> stack5 = setStack5();
          stacks[5] = stack5;
          Stack<Character> stack6 = setStack6();
          stacks[6] = stack6;
          Stack<Character> stack7 = setStack7();
          stacks[7] = stack7;
          Stack<Character> stack8 = setStack8();
          stacks[8] = stack8;
          return stacks;
     }

     private static Stack<Character> setStack0() {
          Stack<Character> stack1 = new Stack<>();

          stack1.push('t');
          stack1.push('p');
          stack1.push('z');
          stack1.push('c');
          stack1.push('s');
          stack1.push('l');
          stack1.push('q');
          stack1.push('n');


          return stack1;
     }

     private static Stack<Character> setStack1() {
          Stack<Character> stack2 = new Stack<>();

          stack2.push('l');
          stack2.push('p');
          stack2.push('t');
          stack2.push('v');
          stack2.push('h');
          stack2.push('c');
          stack2.push('g');
          return stack2;
     }

     private static Stack<Character> setStack2() {
          Stack<Character> stack3 = new Stack<>();

          stack3.push('d');
          stack3.push('c');
          stack3.push('z');
          stack3.push('f');
          return stack3;
     }

     private static Stack<Character> setStack3() {
          Stack<Character> stack4 = new Stack<>();
          stack4.push('g');
          stack4.push('w');
          stack4.push('t');
          stack4.push('d');
          stack4.push('l');
          stack4.push('m');
          stack4.push('v');
          stack4.push('c');
          return stack4;
     }


     private static Stack<Character> setStack4() {
          Stack<Character> stack5 = new Stack<>();
          stack5.push('p');
          stack5.push('w');
          stack5.push('c');
          return stack5;

     }

     private static Stack<Character> setStack5() {
          Stack<Character> stack6 = new Stack<>();


          ///////
          stack6.push('p');
          stack6.push('f');
          stack6.push('j');
          stack6.push('d');
          stack6.push('c');
          stack6.push('t');
          stack6.push('s');
          stack6.push('z');


          return stack6;
     }

     private static Stack<Character> setStack6() {
          Stack<Character> stack7 = new Stack<>();

          stack7.push('v');
          stack7.push('w');
          stack7.push('g');
          stack7.push('b');
          stack7.push('d');


          return stack7;
     }

     private static Stack<Character> setStack7() {
          Stack<Character> stack8 = new Stack<>();
          stack8.push('n');
          stack8.push('j');
          stack8.push('s');
          stack8.push('q');
          stack8.push('h');
          stack8.push('w');


          return stack8;
     }

     private static Stack<Character> setStack8() {
          Stack<Character> stack9 = new Stack<>();
          stack9.push('r');
          stack9.push('c');
          stack9.push('q');
          stack9.push('f');
          stack9.push('s');
          stack9.push('l');
          stack9.push('v');


          return stack9;
     }

     private static void modifiedPushPop(int quantity, Stack<Character> from, Stack<Character> to) {

          Stack<Character> temp = new Stack<>();
          for (int i = 0; i < quantity; i++) {
               if (!from.isEmpty()) {
                    temp.push(from.pop());
               } else {
                    System.out.println("Cannot pop from stack " + from);
                    System.exit(-1);
               }
          }
/*
          Collections.reverse(temp);
*/
          for (int j = 0; j < quantity; j++) {
               to.push(temp.pop());
          }
     }
}
