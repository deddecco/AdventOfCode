import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElfNode {

     private int degree;

     private List<ElfNode> shortestPath = new LinkedList<>();

     private Integer distance = Integer.MAX_VALUE;

     Map<ElfNode, Integer> adjacentNodes = new HashMap<>();

     public int getDegree() {
          return degree;
     }

     public void setDegree(int degree) {
          this.degree = degree;
     }

     public List<ElfNode> getShortestPath() {
          return shortestPath;
     }

     public void setShortestPath(List<ElfNode> shortestPath) {
          this.shortestPath = shortestPath;
     }

     public Integer getDistance() {
          return distance;
     }

     public void setDistance(Integer distance) {
          this.distance = distance;
     }

     public Map<ElfNode, Integer> getAdjacentNodes() {
          return adjacentNodes;
     }

     public void setAdjacentNodes(Map<ElfNode, Integer> adjacentNodes) {
          this.adjacentNodes = adjacentNodes;
     }

     public void addDestination(ElfNode destination, int distance) {
          adjacentNodes.put(destination, distance);
     }

     public ElfNode(int degree) {
          this.degree = degree;
     }
}
