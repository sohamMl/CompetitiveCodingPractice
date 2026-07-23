package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Binary_Search_Problems extends TestBase {

    @ParameterizedTest
    @CsvSource(value = {
            "'1, 2, 4, 5, 7, 8, 9', 4, 2",
            "'1, 2, 4, 5, 7, 8, 9', 6, 4",
            "'2, 4, 6, 8', 1, 0",
            "'2, 4, 6, 8', 10, 4",
            "'2, 4, 6, 8', 2, 0",
            "'2, 4, 6, 8', 8, 3",
            "'5', 5, 0",
            "'5', 3, 0",
            "'5', 7, 1",
            "'', 5, 0"
    })
    void testFindTheInsertionIndex(String numsStr, int target, int expected) {
        int[] nums = numsStr == null || numsStr.isBlank()
                ? new int[0]
                : Arrays.stream(numsStr.split(",\\s*")).mapToInt(Integer::parseInt).toArray();

        int actual = findTheInsertionIndex(nums, target);
        assertEquals(expected, actual);
    }

    /**
     * Finds the insertion index of a target value in a sorted integer array.
     * If the target is already present, it returns the index of the existing target.
     * If the target is not present, it returns the index where the target should be
     * inserted to maintain the sorted order of the array.
     *
     * Algorithm: Binary Search
     * - Search Space: [left, right) - right starts at nums.length.
     * - Time Complexity: O(log N) where N is the length of the array, since we halve the search space at each step.
     * - Space Complexity: O(1) as only a constant amount of extra memory is used.
     *
     * @param nums   A sorted array of integers.
     * @param target The integer value to insert or find.
     * @return The 0-based insertion index.
     */
    public int findTheInsertionIndex(int[] nums, int target) {
        // Initialize boundary indices. The search space is semi-open: [left, right)
        int left = 0, right = nums.length;
        
        while(left < right) {
            // Calculate the midpoint.
            // Using left + (right - left) / 2 instead of (left + right) / 2 prevents potential integer overflow.
            int mid = left + (right - left) / 2;
            
            // If target is found, return its index immediately.
            if(nums[mid] == target) return mid;

            // If target is less than nums[mid], the target belongs in the left half, so narrow the search space to [left, mid).
            if(target < nums[mid]) right = mid;
            // If target is greater than nums[mid], the target belongs in the right half, so narrow the search space to [mid + 1, right).
            else left = mid + 1;
        }
        // At the end of the loop, left and right converge (left == right), representing the exact insertion index.
        return left;
    }



    /**
     * Parameterized test to verify correctness of firstAndLastOccurrencesOfANumber.
     * It tests various cases:
     * - Target present with multiple duplicates (sample case).
     * - Target not present in a multi-element array.
     * - Empty input array.
     * - Single element arrays (match and mismatch cases).
     * - Arrays where all elements match the target.
     * - Target positioned at the start or end boundaries of the array.
     */
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            // Format: nums (comma-separated) | target | expected (comma-separated)
            "'1,2,3,4,4,4,5,6,7,8,9,10,11'; 4; '3,5'",   // Sample example
            "'1,2,3,5,6'; 4; '-1,-1'",                    // Target not found
            "''; 4; '-1,-1'",                             // Empty array
            "'4'; 4; '0,0'",                              // Single element match
            "'5'; 4; '-1,-1'",                            // Single element mismatch
            "'4,4,4,4'; 4; '0,3'",                        // All elements match
            "'4,4,5,6,7'; 4; '0,1'",                      // Target at start boundary
            "'1,2,3,4,4'; 4; '3,4'"                       // Target at end boundary
    })
    void testFirstAndLastOccurrences(String numsStr, int target, String expectedStr) {
        int[] nums = numsStr == null || numsStr.isEmpty() ? new int[0] :
                java.util.Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expected = java.util.Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();

        int[] actual = firstAndLastOccurrencesOfANumber(nums, target);
        assertArrayEquals(expected, actual);
    }

    /**
     * Finds the first and last occurrence indices of a target number in a sorted array.
     *
     * Problem Description:
     * Given a sorted array of integers `nums` containing duplicates, locate the start and
     * end indices of a given `target` value. If the target is not present, return [-1, -1].
     *
     * Solution & Algorithm:
     * The problem is solved in O(log N) time using two distinct binary search phases:
     * 1. Finding the First Occurrence:
     *    - Search space: [left, right] initialized to [0, nums.length - 1].
     *    - When nums[mid] >= target, we know the first occurrence is at or before mid,
     *      so we set right = mid.
     *    - Otherwise (nums[mid] < target), the first occurrence must be to the right,
     *      so we set left = mid + 1.
     *    - This search converges (left == right) to the first candidate index. We verify
     *      if nums[left] == target. If not, the target is absent.
     *
     * 2. Finding the Last Occurrence:
     *    - Search space: [left, right] initialized to [firstOccurrence, nums.length - 1].
     *    - To avoid infinite loops when left and right are adjacent (i.e. right = left + 1),
     *      we bias the midpoint calculation to the right: mid = left + (right - left + 1) / 2.
     *      This ensures that when mid is calculated, it rounds up.
     *    - When nums[mid] <= target, the last occurrence is at or after mid, so we set
     *      left = mid.
     *    - Otherwise (nums[mid] > target), the last occurrence must be to the left, so
     *      we set right = mid - 1.
     *    - This search converges (left == right) to the last occurrence index.
     *
     * Time Complexity: O(log N) - We perform two binary searches, each taking logarithmic time.
     * Space Complexity: O(1) - Only a constant number of integer pointers are used.
     *
     * @param nums   A sorted array of integers.
     * @param target The target value to search for.
     * @return A 2-element array containing the start and end indices of the target,
     *         or [-1, -1] if the target is not found.
     */
    public int[] firstAndLastOccurrencesOfANumber(int[] nums, int target) {
        // Return [-1, -1] immediately if the input array is empty.
        if(nums.length == 0) return new int[] {-1,-1};

        int firstOccurence = -1, lastOccurence = -1;

        // Phase 1: Search for the first occurrence of the target.
        int left = 0, right = nums.length - 1;
        int mid;

        while(left < right) {
            // Standard mid calculation biasing to the left to avoid integer overflow.
            mid = left + (right - left) / 2;
            
            // If mid element is greater than or equal to target, the first occurrence 
            // is in the left half (including mid).
            if(nums[mid] >= target) right = mid;
            // Otherwise, it must be strictly to the right of mid.
            else left = mid + 1;
        }

        // Check if the converged index actually contains the target.
        firstOccurence = nums[left] == target ? left : -1;
        
        // If the target is not found at all, we can skip searching for the last occurrence.
        if(firstOccurence == -1) return new int[] {-1,-1};

        // Phase 2: Search for the last occurrence of the target.
        // We start the search space from the first occurrence index to optimize.
        right = nums.length - 1;
        while(left < right) {
            // Midpoint calculation biased to the right. 
            // This prevents an infinite loop when left and right are adjacent.
            mid = left + (right - left + 1) / 2;
            
            // If mid element is less than or equal to target, the last occurrence
            // is in the right half (including mid).
            if(nums[mid] <= target) left = mid;
            // Otherwise, it must be strictly to the left of mid.
            else right = mid - 1;
        }

        // At convergence (left == right), verify if target matches.
        lastOccurence = nums[right] == target ? right : -1;

        return new int[]{firstOccurence, lastOccurence};
    }

    /*
    * Byte byte go solution
    * import java.util.ArrayList;

    public class Main {
        public ArrayList<Integer> first_and_last_occurrences_of_a_number(ArrayList<Integer> nums, int target) {
            int lower = lower_bound_binary_search(nums, target);
            int upper = upper_bound_binary_search(nums, target);
            ArrayList<Integer> result = new ArrayList<>();
            result.add(lower);
            result.add(upper);
            return result;
        }

        public int lower_bound_binary_search(ArrayList<Integer> nums, int target) {
            int left = 0, right = nums.size() - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (nums.get(mid) > target) {
                    right = mid - 1;
                } else if (nums.get(mid) < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return !nums.isEmpty() && nums.get(left) == target ? left : -1;
        }

        public int upper_bound_binary_search(ArrayList<Integer> nums, int target) {
            int left = 0, right = nums.size() - 1;
            while (left < right) {
                // In upper-bound binary search, bias the midpoint to the right.
                int mid = (left + right) / 2 + 1;
                if (nums.get(mid) > target) {
                    right = mid - 1;
                } else if (nums.get(mid) < target) {
                    left = mid + 1;
                } else {
                    left = mid;
                }
            }
            // If the target doesn’t exist in the array, then it's possible that
            // 'left = mid + 1' places the left pointer outside the array when `mid == n - 1`.
            // So, we use the right pointer in the return statement instead.
            return !nums.isEmpty() && nums.get(right) == target ? right : -1;
        }
    }
    * */
}
