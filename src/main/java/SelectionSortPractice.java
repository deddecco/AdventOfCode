import java.util.Arrays;

public class SelectionSortPractice {

     public static void main(String[] args) {
          int[] nums = {6, 5, 12, 10, 9, 1, -2, 3, -7};

          SelectionSortPractice ssp = new SelectionSortPractice();
          display(nums);
          ssp.selectionSort(nums);
          display(nums);
     }


     public static void display(int[] arr) {
          System.out.println(Arrays.toString(arr));
          System.out.println();
     }

     private void selectionSort(int[] arr) {

          for (int i = 0; i < arr.length - 1; i++) {
               int min = i;
               for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[min]) {
                         min = j;
                    }
               }
               if (min != i) {
                    swap(arr, i, min);
               }
          }

     }

     private void swap(int[] arr, int i, int j) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
     }

}