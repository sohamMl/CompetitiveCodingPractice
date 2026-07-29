package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Binary_Search_Problems extends TestBase {


    /**
     * BINARY SEARCH (TEMPLATE & CHEAT SHEET)
     * -----------------------------------------------------------------------------
     * Use this guide to determine pointer updates, loop conditions, and midpoint
     * formulas based on whether you are seeking an exact match or a boundary.
     *
     * 1. EXACT MATCH (Searching for a Specific Element)
     * Use Case: "Find if target exists in array/matrix", "Find exact target index"
     * Pattern: Narrow down the search space until the target is found or space is exhausted.
     *
     * Logic:
     * - Loop Condition: while (left <= right) // Must check final element when left == right
     * - mid = left + (right - left) / 2;
     * - if (target == midVal) return true/mid;
     * - else if (target < midVal) right = mid - 1;
     * - else left = mid + 1;
     * - Result: Return false or -1 (target not found, pointers crossed: left > right)
     *
     * -----------------------------------------------------------------------------
     * 2. UPPER BOUND (Finding the LAST True / Binary Search on Answer)
     * Use Case: "Maximize the minimum", "Find highest valid setting" (e.g., Woodcutter)
     * Pattern:  [T, T, T, T, F, F, F] -> Looking for the last 'T'
     *
     * Logic:
     * - Loop Condition: while (left < right) // Converges directly on the boundary
     * - mid = left + (right - left + 1) / 2;  // NOTE: MUST +1 to Right-Bias mid!
     * - if (isValid(mid)) left = mid;         // Answer is mid or to the right
     * - else              right = mid - 1;    // mid is invalid, look left
     * - Result: return left; (or right, as they meet at the answer)
     *
     * -----------------------------------------------------------------------------
     * 3. LOWER BOUND (Finding the FIRST True / Binary Search on Answer)
     * Use Case: "Minimize the maximum", "Find lowest valid cost/speed"
     * Pattern:  [F, F, F, T, T, T, T] -> Looking for the first 'T'
     *
     * Logic:
     * - Loop Condition: while (left < right) // Converges directly on the boundary
     * - mid = left + (right - left) / 2;      // Standard Left-Biased mid
     * - if (isValid(mid)) right = mid;        // Answer is mid or to the left
     * - else              left = mid + 1;     // mid is invalid, look right
     * - Result: return left; (or right, as they meet at the answer)
     *
     * -----------------------------------------------------------------------------
     * GOLDEN RULES TO PREVENT INFINITE LOOPS & BUGS:
     * - EXACT MATCH uses `<=`. BOUNDARY MAPPING uses `<` because convergence is the answer.
     * - If your boundary update step uses `left = mid`, your mid calculation MUST have `+ 1`.
     * - If your boundary update step uses `left = mid + 1`, your mid calculation does NOT have `+ 1`.
     */


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




    /**
     * Problem: Cutting Wood (Binary Search on Answer)
     * -----------------------------------------------------------------------------
     * Given an array of tree heights and a target length of wood 'k', find the
     * highest possible height setting 'H' for a woodcutter such that cutting off
     * the tops of trees taller than 'H' yields at least 'k' meters of wood.
     * * Key Insights:
     * - Monotonic Search Space: Height settings H range from 0 to max(heights).
     * Higher H yields less wood; lower H yields more wood.
     * - Upper-Bound Binary Search: Since we want the MAX valid height setting,
     * we use a right-biased midpoint: mid = left + (right - left + 1) / 2.
     * * Time Complexity:  O(N * log(max_height)) where N is the number of trees.
     * Space Complexity: O(1) auxiliary space.
     */


    @ParameterizedTest
    @CsvSource({
            "'2, 6, 3, 8', 7, 3",
            "'10', 4, 6",
            "'5, 5, 5, 5', 8, 3",
            "'1, 10, 3', 1, 9",
            "'2, 6, 3, 8', 19, 0",
            "'20, 15, 10, 17', 7, 15"
    })
    void testCuttingWood(String heightsStr, int k, int expected) {
        int[] heights = Arrays.stream(heightsStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        assertEquals(expected, cuttingWood(heights, k));
    }

    public int cuttingWood(int[] heights, int target) {
        int left = 0, right = arrayMax(heights);
        while(left < right) {
            int mid = left + (right - left + 1) / 2;
            if (cutWood(heights,mid, target)) {
                left = mid;
            } else {
                right = mid - 1;
            }

        }
        return left;
    }

    public boolean cutWood(int[] heights, int k, int target) {
        int totalWood = 0;
        for (int height : heights) {
            totalWood += Math.max(height - k, 0);
        }
        return totalWood >= target;
    }

    public int arrayMax(int[] ar) {
        int max = 0;
        if(ar.length != 0) {
            max = ar[0];
            for(int i = 1; i < ar.length; i++) {
                if (ar[i] > max) {
                    max = ar[i];
                }
            }
        }
        return max;
    }


    /**
     * Problem Statement: Find the Target in a Rotated Sorted Array
     * * Given a rotated sorted array of unique integers (`nums`), return the index of a `target` value.
     * If the target value is not present, return -1.
     * * An array is "rotated" if a portion of it was moved from the beginning to the end
     * (e.g., [1, 2, 3, 4, 5] becomes [3, 4, 5, 1, 2]).
     * * Expected Time Complexity: O(log n)
     * Expected Space Complexity: O(1)
     */

    @ParameterizedTest
    @CsvSource({
            // format: "comma-separated-array-elements | target | expected"
            "'8,9,1,2,3,4,5,6,7', 1, 2",   // Sample case
            "'4,5,6,7,0,1,2',     4, 0",   // Target at left boundary
            "'4,5,6,7,0,1,2',     2, 6",   // Target at right boundary
            "'5,1,2,3,4',         1, 1",   // Target at inflection point
            "'5,1,2,3,4',         5, 0",   // Target before inflection point
            "'4,5,6,7,0,1,2',     3, -1",  // Target not present
            "'1,2,3,4,5',         3, 2",   // Non-rotated array (target present)
            "'1,2,3,4,5',         6, -1",  // Non-rotated array (target absent)
            "'3,1',               1, 1",   // Two elements (rotated)
            "'3,1',               3, 0",   // Two elements (rotated)
            "'1',                 1, 0",   // Single element (present)
            "'1',                 0, -1"   // Single element (absent)
    })
    void testFindTargetInRotatedSortedArray(String numsStr, int target, int expected) {
        int[] nums = numsStr == null || numsStr.trim().isEmpty()
                ? new int[0]
                : java.util.Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();

        assertEquals(expected, findTheTargetInARotatedSortedArray(nums, target));
    }

    public int findTheTargetInARotatedSortedArray(int[] nums, int target) {
        // Edge case: handle empty arrays safely
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;

        // Standard binary search loop structure
        while (left < right) {
            // Prevent potential integer overflow compared to (left + right) / 2
            int mid = left + (right - left) / 2;

            // Step 1: Immediate victory condition check
            if (nums[mid] == target) {
                return mid;
            }

            // Step 2: Identify which half of the array is strictly sorted.
            // A rotated sorted array split in half will ALWAYS yield at least one sorted side.
            if (nums[left] <= nums[mid]) {
                // CASE 1: The left subarray [left : mid] is monotonically increasing (sorted).

                // Step 3a: Check if the target realistically fits inside this sorted region boundary.
                // Note: target <= nums[mid] works perfectly because nums[mid] == target was ruled out above.
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // Discard right half, target is in the left sorted range.
                } else {
                    left = mid + 1;  // Discard left half, target must be in the right un-sorted range.
                }

            } else {
                // CASE 2: The right subarray [mid : right] must be the sorted portion.

                // Step 3b: Check if the target realistically fits inside this right sorted region boundary.
                // Note: nums[mid] < target because mid was already explicitly ruled out as the target match.
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;  // Discard left half, target is in the right sorted range.
                } else {
                    right = mid - 1; // Discard right half, target must be in the left un-sorted range.
                }
            }
        }

        // Step 4: Post-processing check. When left == right, we verify if the single remaining
        // element is the actual target we were tracking down.
        return nums[left] == target ? left : -1;
    }


    /**
     * PROBLEM STATEMENT:
     * Determine if a target value exists in an m x n matrix.
     * Each row of the matrix is sorted in non-decreasing order, and the first value
     * of each row is greater than or equal to the last value of the previous row.
     *
     * ALGORITHM DESCRIPTION:
     * Since the matrix is fully sorted across rows sequentially, it can be treated
     * as a single, continuous, flattened 1D sorted array of size (rows * cols).
     * We perform a standard binary search on this virtual 1D space, mapping the 1D
     * index back to 2D coordinates on the fly to achieve O(log(m * n)) time complexity
     * and O(1) space complexity.
     */

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            // Format: matrix (row-by-row) ; target ; expected
            "1,3,5,7 | 10,11,16,20 | 23,30,34,60 ; 3 ; true",
            "1,3,5,7 | 10,11,16,20 | 23,30,34,60 ; 13 ; false",
            "5 ; 5 ; true",
            "5 ; 1 ; false",
            "10,20 | 30,40 ; 2 ; false",
            "10,20 | 30,40 ; 50 ; false",
            "1,2,3,4,5 ; 4 ; true",
            "1 | 3 | 5 ; 3 ; true"
    })
    void testMatrixSearch(String matrixStr, int target, boolean expected) {
        String[] rows = matrixStr.split(" \\| ");
        int[][] matrix = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",");
            matrix[i] = new int[cells.length];
            for (int j = 0; j < cells.length; j++) {
                matrix[i][j] = Integer.parseInt(cells[j].trim());
            }
        }
        assertEquals(expected, matrixSearch(matrix, target));
    }


    public boolean matrixSearch(int[][] matrix, int target) {
        // Handle empty matrix edge case
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int rows = matrix.length, cols = matrix[0].length;

        // Initialize pointers for the virtual 1D array space
        int left = 0, right = rows * cols - 1;

        // Execute loop until search space is exhausted (left > right)
        while (left <= right) {
            // Calculate the midpoint 1D index, preventing integer overflow
            int mid = left + (right - left) / 2;

            // Map the 1D index 'mid' back to 2D matrix coordinates (r, c)
            // 'cols' determines the row wrapping boundary
            int r = mid / cols;
            int c = mid % cols;

            // Check if the current element matches the target
            if (target == matrix[r][c]) {
                return true;
            }
            // If target is smaller, disregard the right half of the remaining elements
            else if (target < matrix[r][c]) {
                right = mid - 1;
            }
            // If target is larger, disregard the left half of the remaining elements
            else {
                left = mid + 1;
            }
        }

        // Target not found within the matrix
        return false;
    }

    /**
     * Problem: Local Maxima in Array
     * * A local maxima is a value strictly greater than both its immediate neighbors.
     * Return any local maxima index in an array.
     * You may assume that an element is always strictly greater than a neighbor that is outside the array.
     * * Constraints:
     * - No two adjacent elements in the array are equal.
     * - Time Complexity Target: O(log N)
     * - Space Complexity Target: O(1)
     */
    @ParameterizedTest
    @CsvSource({
            // format: "space-separated-nums | expected-index"
            "'1 4 3 2 3', 1", // Sample example (index 4 is also valid)
            "'5 4 3 2 1', 0", // Peak at the start edge
            "'1 2 3 4 5', 4", // Peak at the end edge
            "'1', 0",         // Single element array
            "'1 2', 1",       // Two elements, ascending
            "'2 1', 0",       // Two elements, descending
            "'1 3 2 4 3', 1"  // Multiple peaks (indices 1 and 3 are valid)
    })
    void testLocalMaximaInArray(String numsStr, int expectedPlaceholder) {
        int[] nums = Arrays.stream(numsStr.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int actualIndex = findLocalMaxima(nums);

        assertTrue(actualIndex >= 0 && actualIndex < nums.length, "Index out of bounds");

        if (actualIndex == 0) {
            if (nums.length > 1) {
                assertTrue(nums[0] > nums[1], "Index 0 is not a local maxima");
            }
        } else if (actualIndex == nums.length - 1) {
            if (nums.length > 1) {
                assertTrue(nums[nums.length - 1] > nums[nums.length - 2], "Last index is not a local maxima");
            }
        } else {
            assertTrue(nums[actualIndex] > nums[actualIndex - 1], "Not greater than left neighbor");
            assertTrue(nums[actualIndex] > nums[actualIndex + 1], "Not greater than right neighbor");
        }
    }

    public int findLocalMaxima(int[] nums) {
        // Define search space across the full array bounds
        int left = 0, right = nums.length - 1;

        // Continue narrowing down until the boundaries converge on a single element
        while (left < right) {
            // Prevent integer overflow when calculating midpoint
            int mid = left + (right - left) / 2;

            // Compare mid with its right neighbor to determine slope direction:
            if (nums[mid] > nums[mid + 1]) {
                // Descending slope: Peak exists at 'mid' or somewhere to the left.
                // Keep 'mid' in the search space since it could be the peak itself.
                right = mid;
            } else {
                // Ascending slope (nums[mid] < nums[mid + 1]): Peak exists strictly to the right.
                // Exclude 'mid' because nums[mid + 1] is already strictly greater than it.
                left = mid + 1;
            }
        }

        // 'left' and 'right' converge at the same index, pointing to a valid local maxima
        return left;
    }



    /**
     * Problem: Find the Median of Two Sorted Arrays
     * * Given two sorted integer arrays `nums1` and `nums2` of size `m` and `n` respectively,
     * return the median of the two sorted arrays.
     * * The overall run time complexity should be O(log (m+n)) or O(log (min(m, n))).
     * * Example 1:
     * Input: nums1 = [0, 2, 5, 6, 8], nums2 = [1, 3, 7]
     * Output: 4.0
     * Explanation: Merged array = [0, 1, 2, 3, 5, 6, 7, 8], median is (3 + 5) / 2 = 4.0
     * * Example 2:
     * Input: nums1 = [0, 2, 5, 6, 8], nums2 = [1, 3, 7, 9]
     * Output: 5.0
     * Explanation: Merged array = [0, 1, 2, 3, 5, 6, 7, 8, 9], median is 5.0
     * * Constraints:
     * - nums1.length == m
     * - nums2.length == n
     * - 0 <= m <= 1000
     * - 0 <= n <= 1000
     * - 1 <= m + n <= 2000
     * - -10^6 <= nums1[i], nums2[i] <= 10^6
     */
    @ParameterizedTest
    @CsvSource(value = {
            "'0,2,5,6,8' | '1,3,7' | 4.0",
            "'0,2,5,6,8' | '1,3,7,9' | 5.0",
            "'' | '1,2,3' | 2.0",
            "'1,2,3,4' | '' | 2.5",
            "'2' | '1' | 1.5",
            "'1,2' | '3,4' | 2.5",
            "'1,1,1' | '1,1,1' | 1.0"
    }, delimiter = '|')
    void testFindMedianSortedArrays(String s1, String s2, double expected) {
        int[] nums1 = (s1 == null || s1.trim().isEmpty()) ? new int[0] : Arrays.stream(s1.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nums2 = (s2 == null || s2.trim().isEmpty()) ? new int[0] : Arrays.stream(s2.split(",")).mapToInt(Integer::parseInt).toArray();

        assertEquals(expected, findMedianSortedArrays(nums1, nums2), 1e-5);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return 0.0;

        // Optimization: Ensure nums1 is the smaller array to minimize the binary search range O(log(min(m, n)))
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int len_n1 = nums1.length;
        int len_n2 = nums2.length;
        int totalLen = len_n1 + len_n2;
        int halfTotalLen = totalLen / 2;

        // Search space represents the COUNT of elements taken from nums1 into the left partition.
        // Range: [0, len_n1] (0 elements up to all elements of nums1)
        int leftCount = 0;
        int rightCount = len_n1;

        while (leftCount <= rightCount) {
            // Step 1: Calculate element counts for left partitions of both arrays
            int count1 = leftCount + (rightCount - leftCount) / 2; // Elements taken from nums1
            int count2 = halfTotalLen - count1;                    // Remaining elements taken from nums2

            // Step 2: Determine partition boundary values based on element counts

            // Left boundary values (last element in left partition, index = count - 1)
            int n1_left_part = (count1 == 0) ? Integer.MIN_VALUE : nums1[count1 - 1];
            int n2_left_part = (count2 == 0) ? Integer.MIN_VALUE : nums2[count2 - 1];

            // Right boundary values (first element in right partition, index = count)
            int n1_right_part = (count1 == len_n1) ? Integer.MAX_VALUE : nums1[count1];
            int n2_right_part = (count2 == len_n2) ? Integer.MAX_VALUE : nums2[count2];

            // Step 3: Validate partition conditions

            if (n1_left_part > n2_right_part) {
                // Picked too many elements from nums1 -> Decrease search count
                rightCount = count1 - 1;
            } else if (n2_left_part > n1_right_part) {
                // Picked too few elements from nums1 -> Increase search count
                leftCount = count1 + 1;
            } else {
                // Step 4: Valid partition found -> Compute Median
                if (totalLen % 2 == 0) {
                    int maxOfLeft = Math.max(n1_left_part, n2_left_part);
                    int minOfRight = Math.min(n1_right_part, n2_right_part);
                    return (maxOfLeft + minOfRight) / 2.0;
                } else {
                    return Math.min(n1_right_part, n2_right_part);
                }
            }
        }

        return 0.0;
    }




}
