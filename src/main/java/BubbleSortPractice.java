import java.util.Arrays;

public class BubbleSortPractice {

     public static void main(String[] args) {
          int[] arr = {6, 5, 12, 10, 9, 1, -2, 3, -7};

          BubbleSortPractice bsp = new BubbleSortPractice();
          display(arr);
          bsp.bubbleSort(arr);
          display(arr);
     }

     private void bubbleSort(int[] arr) {
          for (int i = 0; i < arr.length; i++) {
               for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] > arr[j]) {
                         swap(arr, i, j);
                    }
               }
          }
     }

     private void swap(int[] arr, int i, int j) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
     }

     public static void display(int[] arr) {
          System.out.println(Arrays.toString(arr));
          System.out.println();
     }
}