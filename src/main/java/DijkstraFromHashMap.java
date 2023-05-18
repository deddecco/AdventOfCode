import java.io.IOException;
import java.util.*;

import static org.example.aoc2022.ElfDegreeMatrixGenerator.*;


public class DijkstraFromHashMap {
     public static HashMap<ElfGraphNode, Integer> findPathLength(Map<ElfGraphNode, List<String>> graph, int[] source) {

          HashMap<ElfGraphNode, Integer> distances = new HashMap<>();
          HashMap<ElfGraphNode, ElfGraphNode> previous = new HashMap<>();


          ArrayDeque<ElfGraphNode> queue = new ArrayDeque<>();


          for (ElfGraphNode v : graph.keySet()) {
               if (v.getX() == source[0] && v.getY() == source[1]) {
                    distances.put(v, 0);
               } else {
                    distances.put(v, Integer.MAX_VALUE);
               }
               previous.put(v, null);
               queue.add(v);
          }

          while (!queue.isEmpty()) {
               ElfGraphNode u = findSmallestDistanceNode(distances, queue);
               // System.out.println("smallest: " + u.toString());
               queue.remove(u);
               for (ElfGraphNode neighbor : graph.keySet()) {
                    if (isNeighbor(graph, u, neighbor)) {
                         if (queue.contains(neighbor)) {
                              int alt = distances.get(u) + 1;
                              if (alt < distances.get(neighbor)) {
                                   distances.put(neighbor, alt);
                                   previous.put(neighbor, u);
                              }
                         }
                    }

               }
          }
          return distances;
     }


     private static boolean isNeighbor(Map<ElfGraphNode, List<String>> adjmatrix, ElfGraphNode u, ElfGraphNode n) {
          List<String> neighbors = adjmatrix.get(u);
          return neighbors.contains(n.getName());
     }

     private static void getAnswer(String[] args) throws IOException {
          // read in the elevation map
          ArrayList<ArrayList<Character>> elevationMap = readElevationMap(args[0]);
         /* System.out.println("before changes:");
          printElevationMap(elevationMap);
          System.out.println();
          System.out.println();
          System.out.println();
          System.out.println();
          System.out.println();
          System.out.println();
          // find the source*/
          int[] sourceLocation = findCoordinatesOfStart(elevationMap);
          int[] destinationLocation = findCoordinatesOfEnd(elevationMap);
          elevationMap = processMap(elevationMap);
          System.out.println("after changes: ");
          //printElevationMap(elevationMap);
          // build the adjacency map
          Map<ElfGraphNode, List<String>> map = buildAdjMatrix(elevationMap);
          // calculate the distance
          HashMap<ElfGraphNode, Integer> answer = findPathLength(map, sourceLocation);
          // print the answer
          System.out.println(answer.values());
          System.out.println("distance to end Node: " + distanceToEndNode(answer, destinationLocation));
     }


     public static int distanceToEndNode(HashMap<ElfGraphNode, Integer> answer, int[] destinationLocation) {

          for (ElfGraphNode node : answer.keySet()) {
               if (node.getX() == destinationLocation[0] && node.getY() == destinationLocation[1]) {
                    System.out.println(node);
                    return answer.get(node);
               }
          }

          return -1;
     }


     /**
      * look through a queue and a map of distances, and find the element in the queue with the smallest distance
      */
     public static ElfGraphNode findSmallestDistanceNode(HashMap<ElfGraphNode, Integer> distances, ArrayDeque<ElfGraphNode> queue) {
          ElfGraphNode smallestDistanceNode = queue.peek();
          for (ElfGraphNode egn : queue) {
               if (distances.get(egn) < distances.get(smallestDistanceNode)) {
                    smallestDistanceNode = egn;
               }
          }

          return smallestDistanceNode;
     }

     public static void main(String[] args) throws IOException {

          getAnswer(args);
     }
}
