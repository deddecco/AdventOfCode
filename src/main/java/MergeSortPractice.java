import java.util.Arrays;

import static java.lang.System.arraycopy;

public class MergeSortPractice {

     public static void main(String[] args) {
          int[] nums = {6, 5, 12, 10, 9, 1, -2, 3, -7};

          MergeSortPractice msp = new MergeSortPractice();
          display(nums);
          msp.mergeSort(nums, 0, nums.length - 1);
          display(nums);
     }

     public static void display(int[] nums) {
          System.out.println(Arrays.toString(nums));
          System.out.println();
     }

     private void mergeSort(int[] nums, int start, int end) {
          // if pointers are still in natural order
          if (start < end) {
               // mid = average of coordinates
               int mid = (start + end) / 2;
               // sort the left half
               mergeSort(nums, start, mid);
               // sort the right half
               mergeSort(nums, mid + 1, end);
               // merge the two halves
               merge(nums, start, mid, end);
          }
     }

     private void merge(int[] nums, int left, int mid, int right) {
          int lenL = mid - left + 1;
          int lenR = right - mid;

          int[] lHalf = new int[lenL];

          // copy the first half into lHalf
          arraycopy(nums, 0, lHalf, 0, lenL);
          int[] rHalf = new int[lenR];
          // copy the second half into rHalf
          for (int i = 0; i < lenR; i++) {
               rHalf[i] = nums[i + mid + 1];
          }


          int i = 0;
          int j = 0;
          int k = left;

          // while both i and j are still in bounds (while there are still values to consider in both halves)
          while (i < lenL && j < lenR) {
               // pick the one with the smaller value, put it in nums
               // move the pointer for that array
               if (lHalf[i] < rHalf[j]) {
                    nums[k] = lHalf[i];
                    i++;
               } else {
                    nums[k] = rHalf[j];
                    j++;
               }
               // move the pointer for nums
               k++;
          }

          // deal with what will happen if division is not 50/50--
          // right half or left half might have extra element not paired on opposite side
          while (i < lenL) {
               nums[k] = lHalf[i];
               i++;
               k++;
          }
          while (j < lenR) {
               nums[k] = rHalf[j];
               j++;
               k++;
          }

     }
}