package org.example.aoc2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ElfDegreeMatrixGenerator {
     /**
      * inner class represents nodes in a graph
      * nodes have an x position, a y position, and a name
      * the name of a node is the unique string x position, underscore, y position
      * the no-args constructor places a new node by default at (0,0) with name "0_0"
      */
     public static class ElfGraphNode {
          private int x;
          private int y;

          private char letter;

          private String name;

          public char getLetter() {
               return letter;
          }

          public void setLetter(char letter) {
               this.letter = letter;
          }

          public int getX() {
               return x;
          }

          public void setX(int x) {
               this.x = x;
          }

          public int getY() {
               return y;
          }

          public void setY(int y) {
               this.y = y;
          }

          public String getName() {
               return name;
          }

          public void setName(String name) {
               this.name = name;
          }

          public ElfGraphNode(int x, int y, char letter) {
               this.x = x;
               this.y = y;
               this.letter = letter;
               this.name = "%s_%d_%d".formatted(letter, x, y);
          }

          public ElfGraphNode() {
               this.x = 0;
               this.y = 0;
               this.letter = '\u0000';
               this.name = "%s_%d_%d".formatted(letter, x, y);
          }

          @Override
          public String toString() {
               return "ElfGraphNode{" + "x=" + x + ", y=" + y + ", name='" + name + '\'' + '}';
          }
     }

     /**
      * look through the elevation map, and given the puzzle's rules about elevation changes and visiting neighbors, build a graph from the elevations
      */
     public static Map<ElfGraphNode, List<String>> buildAdjMatrix(ArrayList<ArrayList<Character>> eMap) {
          HashMap<ElfGraphNode, List<String>> adjMatrix = new HashMap<>();
          for (int row = 0; row < eMap.size(); row++) {
               for (int col = 0; col < eMap.get(0).size(); col++) {
                    List<String> adjacency;
                    ElfGraphNode node = new ElfGraphNode(row, col, eMap.get(row).get(col));

                    int[][] deltas = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

                    for (int[] neighborDeltas : deltas) {
                         int neighborRow = row + neighborDeltas[1];
                         int neighborColumn = col + neighborDeltas[0];

                         String newName = neighborRow + "_" + neighborColumn;

                         boolean nodeIsNotInBounds = (neighborRow < 0) || (neighborColumn < 0) || (neighborRow >= eMap.size()) || (neighborColumn >= eMap.get(0).size());
                         if (!nodeIsNotInBounds) {
                              boolean nodeHeightIsUnreachable = eMap.get(row).get(col) < eMap.get(neighborRow).get(neighborColumn) - 1;
                              if (!nodeHeightIsUnreachable) {
                                   adjacency = new ArrayList<>();
                                   if (!contains(adjMatrix, node)) {
                                        adjMatrix.put(node, adjacency);
                                   }
                                   adjMatrix.get(node).add(newName);
                              }
                         }
                    }
               }
          }
          return adjMatrix;
     }

     /**
      * check if the map given by the first parameter contains the key given by the second parameter. if yes, return true; if no, return false
      */
     public static boolean contains(Map<ElfGraphNode, List<String>> adjMatrix, ElfGraphNode node) {
          return adjMatrix.containsKey(node);
     }


     /**
      * convert the elevation of the start (to the lowest value) and end (to the highest value).
      * any elevation that is not passed in as "S" or "E" remains unchanged
      */
     public static char convertElevation(char elevation) {
          return switch (elevation) {
               case 'S' -> 'a';
               case 'E' -> 'b';
               default -> elevation;
          };
     }

     // find the coordinates of the start of the path
     public static int[] findCoordinatesOfStart(ArrayList<ArrayList<Character>> elevationMap) {
          int[] startCoordinates = new int[2];
          for (int i = 0; i < elevationMap.size(); i++) {
               for (int j = 0; j < elevationMap.get(i).size(); j++) {
                    if (elevationMap.get(i).get(j) == 'S') {
                         startCoordinates[0] = i;
                         startCoordinates[1] = j;
                    }
               }
          }

          return startCoordinates;
     }


     // find the coordinates of where the end of the path should be
     public static int[] findCoordinatesOfEnd(ArrayList<ArrayList<Character>> elevationMap) {
          int[] endCoordinates = new int[2];
          for (int i = 0; i < elevationMap.size(); i++) {
               for (int j = 0; j < elevationMap.get(i).size(); j++) {
                    if (elevationMap.get(i).get(j) == 'E') {
                         endCoordinates[0] = i;
                         endCoordinates[1] = j;
                         return endCoordinates;
                    }
               }
          }
          throw new IllegalStateException("No end node found");
     }


     public static ArrayList<ArrayList<Character>> readElevationMap(String filePath) throws IOException {


          ArrayList<ArrayList<Character>> elevationMap = new ArrayList<>();
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;


          while ((line = reader.readLine()) != null) {
               char[] lineArray = line.toCharArray();
               ArrayList<Character> lineList = new ArrayList<>();
               for (char c : lineArray) {
                    lineList.add(c);
               }

               elevationMap.add(lineList);
          }
          return elevationMap;
     }


     public static ArrayList<ArrayList<Character>> processMap(ArrayList<ArrayList<Character>> elevationMap) {
          ArrayList<ArrayList<Character>> adjustedList = new ArrayList<>(elevationMap);
          for (int i = 0; i < adjustedList.size(); i++) {
               for (int j = 0; j < adjustedList.get(i).size(); j++) {
                    if (adjustedList.get(i).get(j) == 'S') {
                         char tempChar = 'z';
                         ArrayList<Character> adjustedLine = adjustedList.get(i);
                         adjustedLine.set(j, tempChar);
                         adjustedList.set(i, adjustedLine);
                    }
                    if (adjustedList.get(i).get(j) == 'E') {
                         char tempChar = 'a';
                         ArrayList<Character> adjustedLine = adjustedList.get(i);
                         adjustedLine.set(j, tempChar);
                         adjustedList.set(i, adjustedLine);
                    }
               }
          }
          return adjustedList;
     }

     /**
      * given the matrix, feed it through this method to convert it to notation used by the GraphViz engine
      */
     public static void convertToGraphVizNotation(Map<ElfGraphNode, List<String>> adjMatrix) {
          System.out.println("digraph G {");
          for (ElfGraphNode egn : adjMatrix.keySet()) {
               for (String adjacent : adjMatrix.get(egn)) {
                    System.out.println(egn.getName() + "->" + adjacent + ";");
               }
          }
          System.out.println("}");
     }


     public static void main(String[] args) throws IOException {
          String filePath = args[0];
          ArrayList<ArrayList<Character>> eMap = readElevationMap(filePath);
          Map<ElfGraphNode, List<String>> adjMatrix = buildAdjMatrix(eMap);
     }

     public static void printElevationMap(ArrayList<ArrayList<Character>> eMap) {
          for (ArrayList<Character> ll : eMap) {
               for (char c : ll) {
                    System.out.print(c + " ");
               }
               System.out.println();
          }
     }


}