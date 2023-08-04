import java.util.Arrays;

public class InsertionSortPractice {
     public static void main(String[] args) {
          int[] arr = {6, 5, 12, 10, 9, 1, -2, 3, -7};

          InsertionSortPractice isp = new InsertionSortPractice();
          display(arr);
          isp.insertionSort(arr);
          display(arr);
     }

     private void insertionSort(int[] arr) {
          int i = 1;
          while (i < arr.length) {
               int j = i;
               // if in wrong order relative to predecessor neighbor, swap
               while (j > 0 && arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                    j--;
               }
               i++;
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