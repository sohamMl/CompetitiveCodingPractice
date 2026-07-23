package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

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
}
