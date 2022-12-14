import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ElfRockPaperScissors {


     public static final int WIN_SCORE = 6;
     public static final int DRAW_SCORE = 3;
     public static final int LOSS_SCORE = 0;
     public static final int ROCK_SCORE = 1;
     public static final int PAPER_SCORE = 2;
     public static final int SCISSORS_SCORE = 3;


     /**
      * read in file, calculate score from file, print out score
      */
     public static void main(String[] args) throws IOException {
          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          String[][] gameResults = readFile(filePath);
          int myScore = calculateScore(gameResults);
          System.out.println("My score: " + myScore);
     }

     /**
      * given the results of all the games, calculate the score of one of the players
      */
     private static int calculateScore(String[][] gameResults) {
          int myScore = 0;

          for (String[] game : gameResults) {
               int roundScore = 0;
               // opponent plays rock
               if (game[0].equals("A")) {
                    // if I need to win
                    if (game[1].equals("Z")) {
                         roundScore = PAPER_SCORE;
                         roundScore += WIN_SCORE;
                    }
                    // if I need to draw
                    if (game[1].equals("Y")) {
                         roundScore += ROCK_SCORE;
                         roundScore += DRAW_SCORE;
                    }
                    // if I need to lose
                    if (game[1].equals("X")) {
                         roundScore += SCISSORS_SCORE;
                         roundScore += LOSS_SCORE;

                    }
               }

               // opponent plays paper
               if (game[0].equals("B")) {
                    // if I need to draw
                    if (game[1].equals("Y")) {
                         roundScore += DRAW_SCORE;
                         roundScore += PAPER_SCORE;
                    }// if I need to lose
                    if (game[1].equals("X")) {
                         roundScore += LOSS_SCORE;
                         roundScore += ROCK_SCORE;
                    } // if I need to win
                    if (game[1].equals("Z")) {
                         roundScore += WIN_SCORE;
                         roundScore += SCISSORS_SCORE;
                    }
               }

               // opponent plays scissors
               if (game[0].equals("C")) {
                    // if I need to draw
                    if (game[1].equals("Y")) {
                         roundScore += DRAW_SCORE;
                         roundScore += SCISSORS_SCORE;
                    } // if I need to win
                    if (game[1].equals("Z")) {
                         roundScore += WIN_SCORE;
                         roundScore += ROCK_SCORE;
                    }// if I need to lose
                    if (game[1].equals("X")) {
                         roundScore += LOSS_SCORE;
                         roundScore += PAPER_SCORE;
                    }
               }
               myScore += roundScore;
          }
          return myScore;
     }


     /**
      * read file line by line into 2d array containing 1 column per player and 1 row per game between them
      */
     private static String[][] readFile(String filePath) throws IOException {
          BufferedReader reader = new BufferedReader(new FileReader(filePath));

          return populateArray(reader);
     }

     private static String[][] populateArray(BufferedReader reader) throws IOException {
          String[][] gameResults = new String[2500][2];

          int i = 0;
          String line;
          // put the game results into an array
          while ((line = reader.readLine()) != null) {
               String[] game = line.split(" ");
               gameResults[i][0] = game[0];
               gameResults[i][1] = game[1];
               i++;
          }
          return gameResults;
     }

     /**
      * change ABC to "Rock", "Paper", "Scissors"
      * and XYX to "Lose", "Draw", "Win
      * for easier readability
      */
     private static void convertGames(String[] game) {
          switch (game[0]) {
               case "A" -> game[0] = "Rock";
               case "B" -> game[0] = "Paper";
               case "C" -> game[0] = "Scissors";
          }
          switch (game[1]) {
               case "X" -> game[1] = "Lose";
               case "Y" -> game[1] = "Draw";
               case "Z" -> game[1] = "Win";
          }

          System.out.println(Arrays.deepToString(game));
     }
}
