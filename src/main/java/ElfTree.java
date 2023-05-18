import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ElfTree {

     /* // an ElfTree is made of ElfNodes
      // each ElfNode has a size (0 for directories, a specified size for files)
      // and each ElfNode knows its children
      public static class ElfTreeNode {
           int size;
           String name;
           List<ElfTreeNode> children = new LinkedList<>();

           // create a new ElfNode with a given name and size, and no children at first
           ElfTreeNode(String name, int size) {
                this.name = name;
                this.size = size;
           }
      }

      // given the root of a subtree of an ElfTree
      // create a list of nodes
      // add the root to that list
      // while that list is not empty, go through each node in the list
      // find each of its non-null children,
      // and print out those children's contents
      private static void printElfTree(ElfTreeNode root) {
           // if root is null, nothing more to print
           if (root == null) {
                return;
           }
           LinkedList<ElfTreeNode> list = new LinkedList<>();
           list.add(root);
           while (!list.isEmpty()) {
                int i = 0;
                while (i < list.size()) {
                     ElfTreeNode node = list.poll();

                     if (node != null) {
                          System.out.print(node.name + "        ");
                          list.addAll(node.children);
                     }
                     i++;
                }
                System.out.println("        ");
           }
      }
 */
     public static void main(String[] args) throws IOException {
   /*       ElfTreeNode root = new ElfTreeNode("root", 0);
          ElfTreeNode a = new ElfTreeNode("a", 0);
          ElfTreeNode b = new ElfTreeNode("b.txt", 14848514);
          ElfTreeNode c = new ElfTreeNode("c.dat", 8504156);
          ElfTreeNode d = new ElfTreeNode("d", 0);
          root.children.add(a);
          root.children.add(b);
          root.children.add(c);
          root.children.add(d);
          ElfTreeNode e = new ElfTreeNode("e", 0);
          ElfTreeNode f = new ElfTreeNode("f", 29116);
          ElfTreeNode g = new ElfTreeNode("g", 2557);
          ElfTreeNode h = new ElfTreeNode("h", 62596);
          a.children.add(e);
          a.children.add(f);
          a.children.add(g);
          a.children.add(h);

          ElfTreeNode i = new ElfTreeNode("i", 584);
          e.children.add(i);

          ElfTreeNode j = new ElfTreeNode("j", 4060174);
          ElfTreeNode dlog = new ElfTreeNode("d.log", 8033020);
          ElfTreeNode dext = new ElfTreeNode("d.ext", 5626152);
          ElfTreeNode k = new ElfTreeNode("k", 7214296);
          d.children.add(j);
          d.children.add(dlog);
          d.children.add(dext);
          d.children.add(k);

          printElfTree(root);*/

          String filePath = "C:\\Users\\andre\\IdeaProjects\\AdventOfCode\\elf-input.txt";
          BufferedReader reader = new BufferedReader(new FileReader(filePath));
          String line;


          while ((line = reader.readLine()) != null) {
               // System.out.print(line);
               commandFileOrDirectory(line);
          }
     }

     private static void commandFileOrDirectory(String line) {

          StringBuilder commandBuilder = new StringBuilder();
          if (line.charAt(0) == '$') {

               for (int i = 0; i < line.length(); i++) {
                    commandBuilder.append(line.charAt(i));
               }

               String dirName = "";

               int indexOfCD = line.indexOf("cd");
               if (indexOfCD != -1) {
                    dirName += (line.substring(indexOfCD + 2));
               }

               if (commandBuilder.indexOf("..") != -1) {
                    System.out.println("jump up 1 level");
               } else if (commandBuilder.indexOf("/") != -1) {
                    System.out.println("go to root");
               } else if (commandBuilder.indexOf("ls") != -1) {
                    System.out.println("get list of files and directories");
               } else if (commandBuilder.indexOf("cd") != -1) {
                    System.out.println("going to directory " + dirName);
               } else {
                    System.out.println("unknown command " + commandBuilder);
               }


               return;
          }

          StringBuilder dirName = new StringBuilder();
          if (line.charAt(0) == 'd' && line.charAt(1) == 'i' && line.charAt(2) == 'r') {
               for (int i = 4; i < line.length(); i++) {
                    if (line.charAt(i) != ' ') {
                         dirName.append(line.charAt(i));
                    }

               }
               System.out.println("directory \t" + dirName);
               return;
          }

          StringBuilder intBuilder = new StringBuilder();
          if (Integer.parseInt(String.valueOf(line.charAt(0))) <= 9 || Integer.parseInt(String.valueOf(line.charAt(0))) >= 1) {
               for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) <= 57 && line.charAt(i) >= 48) {
                         intBuilder.append(line.charAt(i));
                    }
               }
               int fileSize = Integer.parseInt(String.valueOf(intBuilder));
               System.out.println("file of size \t" + fileSize);
          }
     }
}