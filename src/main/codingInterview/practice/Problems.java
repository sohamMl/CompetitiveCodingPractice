package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Main practice class containing solutions and test suites for various coding interview problems.
 * Inherits setup and teardown logic from TestBase. Each problem typically consists of:
 * 1. A Javadoc/block comment containing the problem statement, constraints, and examples.
 * 2. A JUnit 5 parameterized test to execute assertions with multiple edge cases.
 * 3. The core algorithm/logic method.
 */
public class Problems extends TestBase {


    // this is for finding the first pair only. We have to use brute force to find all pairs
    @ParameterizedTest
    @ValueSource(strings = {
            "-5, -2, 3, 4, 6:7", // Expects: [2, 3] (values 3 + 4 = 7)
            "1, 1, 1:2",         // Expects: [0, 2] (values 1 + 1 = 2)
            "2, 4:5",            // Expects: [] (No pair equals 5)
            " :5"                // Expects: [] (Empty array edge case)
    })
    void pairSumOfSortedArray(String data) {
        int target = Integer.parseInt(data.split(":")[1]);

        // Handle empty input string boundary cleanly
        int[] nums;
        if (data.split(":")[0].trim().isEmpty()) {
            nums = new int[0];
        } else {
            nums = Arrays.stream(data.split(":")[0].split(","))
                    .mapToInt(s -> Integer.parseInt(s.trim()))
                    .toArray();
        }

        List<Integer> pair = pairSumOfSortedArray(target, nums);

        // Assertions that safely handle cases returning nothing
        if (!pair.isEmpty()) {
            System.out.printf("pair: %s - %d,%d %n", pair, nums[pair.get(0)], nums[pair.get(1)]);
            assertEquals(target, nums[pair.get(0)] + nums[pair.get(1)]);
        } else {
            System.out.printf("No matching pair found for target %d %n", target);
            Assertions.assertTrue(pair.isEmpty());
        }
    }

    /**
     * Finds the first pair in a sorted array that sums to the target.
     * 
     * Algorithm: Two-Pointer Approach
     * - We initialize two pointers: 'start' at index 0 and 'end' at the last index.
     * - In a loop, we calculate the sum of elements at both pointers.
     * - If sum < target, we move 'start' rightward to increase the sum (since the array is sorted).
     * - If sum > target, we move 'end' leftward to decrease the sum.
     * - If sum == target, we return the pair of indices.
     * Time Complexity: O(N) since we traverse the array at most once.
     * Space Complexity: O(1) auxiliary space.
     */
    List<Integer> pairSumOfSortedArray(int target, int[] nums) {
        int start = 0, end = nums.length - 1;

        // Take two pointers start and end - loop till start < end
        while (start < end) {
            int sum = nums[start] + nums[end];

            // If sum is < target then start++
            if (sum < target) {
                start++;
            }
            // If sum is > target then end--
            else if (sum > target) {
                end--;
            }
            // If sum == target then return the first occurrence immediately
            else {
                return List.of(start, end);
            }
        }

        // Return an empty list if no pair matches the target
        return List.of();
    }


    /*
    ## Problem Statement: Triplet Sum (3Sum)

    Given an integer array `nums`, return all unique triplets `[a, b, c]` such that they satisfy the following conditions:

    * $a + b + c = 0$
    * The solution set must not contain duplicate triplets (e.g., `[1, 2, -3]` and `[-3, 1, 2]` are considered duplicates).

    The elements within each triplet and the final output list can be returned in any order.

    ### Constraints & Edge Cases to Consider

    * The input array may contain fewer than 3 elements (should return an empty list).
    * The input array may contain duplicates or consist entirely of zeros.

    ### Examples

    * **Input:** `nums = [-1, 0, 1, 2, -1, -4]`
    **Output:** `[[-1, -1, 2], [-1, 0, 1]]`
    * **Input:** `nums = [0, 0, 0]`
    **Output:** `[[0, 0, 0]]`
    * **Input:** `nums = [1, 2]`
    **Output:** `[]`
     */

    @ParameterizedTest
    @ValueSource(strings = {"-1,2,-2,1,-1,2", "", "0", "1,-1", "0,0,0", "1,0,1", "0, 0, 1, -1, 1, -1"})
    void tripletSumTest(String data) {
        // Handle empty/blank strings upfront to avoid split() edge cases
        int[] nums = data.isBlank() ? new int[0] :
                Arrays.stream(data.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .mapToInt(Integer::parseInt)
                        .toArray();

        List<List<Integer>> tripletList = tripletSum(nums);

        // Optional: Print to console to visually inspect during development
        System.out.println("Input: " + Arrays.toString(nums) + " -> Output: " + tripletList);

        // 1. Verify that if input length is < 3, output MUST be empty
        if (nums.length < 3) {
            Assertions.assertTrue(tripletList.isEmpty(), "Expected empty list for input size < 3");
        }

        // 2. Verify all found triplets sum to 0
        tripletList.forEach(triplet -> {
            assertEquals(3, triplet.size(), "Triplet must contain exactly 3 elements");
            assertEquals(0, triplet.get(0) + triplet.get(1) + triplet.get(2),
                    "Elements " + triplet + " do not sum to 0");
        });
    }

    /**
     * Finds all unique triplets in an array that sum up to 0.
     * 
     * Algorithm: Sort and Two-Pointer Approach
     * - We first sort the input array.
     * - We then iterate through the array, using each element nums[i] as the first element of the triplet.
     * - To avoid duplicate triplets, we skip elements that are identical to their predecessor.
     * - For each nums[i], we find pairs in the remaining right subarray (from i+1 to end) that sum to -nums[i]
     *   using a two-pointer approach (pair_sum_sorted_all_pairs).
     * Time Complexity: O(N^2) due to nested loop and two-pointer traversal.
     * Space Complexity: O(log N) or O(N) depending on the library sort implementation.
     */
    List<List<Integer>> tripletSum(int[] nums) {
        if(nums.length < 3) { return List.of(); }

        Arrays.sort(nums);
        List<List<Integer>> tripletList = new ArrayList<>();
        for(int i=0; i<nums.length-2; i++) {
            if(nums[i]>0) break;

            if(i > 0 && nums[i] == nums[i-1]) continue;

            int target = nums[i];
            List<List<Integer>> pairs = pair_sum_sorted_all_pairs(nums, i+1, -target);
            pairs.forEach(pair -> tripletList.add(List.of(target, pair.get(0), pair.get(1))));
        }
        return tripletList;
    }

    List<List<Integer>> pair_sum_sorted_all_pairs(int[] nums, int start, int target) {
        List<List<Integer>> pairs = new ArrayList<>();
        int end = nums.length - 1;

        while(start<end) {
            int sum = nums[start] + nums[end];

            if(sum == target) {
                pairs.add(List.of(nums[start], nums[end]));
                start++;

                //duplicate removal
                while(start<end && nums[start] == nums[start-1]) start++;

            } else if(sum < target) {
                start++;
            } else {
                end--;
            }
        }

        return pairs;
    }


    @ParameterizedTest
    @CsvSource({
            "'', true",
            "'a', true",
            "'aa', true",
            "'ab', false",
            "'!, (?)', true",
            "'12.02.2021', true",
            "'21.02.2021', false",
            "'hello, world!', false"
    })
    void testIsPalindromeValid(String input, boolean expected) {
        assertEquals(expected, isPalindromeValid(input));
    }

    /**
     * Checks if a string is a valid palindrome, ignoring non-alphanumeric characters and case.
     * 
     * Algorithm: Two-Pointer Approach
     * - Initialize 'left' at start and 'right' at end.
     * - Skip any non-alphanumeric characters by advancing 'left' or decrementing 'right'.
     * - Compare the lowercased characters at both pointers. If they differ, return false.
     * - Repeat until pointers meet.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    boolean isPalindromeValid(String input) {
        int left = 0, right = input.length() - 1;
        while (left < right) {
            while (left < right && !isAlphanumeric(input.charAt(left))) { left++; }
            while (left < right && !isAlphanumeric(input.charAt(right))) { right--; }

            // Normalize characters to lowercase before comparing
            char leftChar = Character.toLowerCase(input.charAt(left));
            char rightChar = Character.toLowerCase(input.charAt(right));

            if (leftChar != rightChar) return false;

            left++;
            right--;
        }
        return true;
    }

    boolean isAlphanumeric(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }



    @ParameterizedTest(name = "{2}")
    @CsvSource(value = {
            "'', '', 'Empty array'",
            "'0', '0', 'One 0'",
            "'1', '1', 'One 1'",
            "'0,0,0', '0,0,0', 'All 0s'",
            "'1,3,2', '1,3,2', 'All non-zeros'",
            "'1,1,1,0,0', '1,1,1,0,0', 'All zeros already at the end'",
            "'0,0,1,1,1', '1,1,1,0,0', 'All zeros at the start'",
            "'1, 0, 3, 0, 2', '1,3,2,0,0', 'All zeros at the end'"
    })
    void testShiftZerosToTheEnd(String inputStr, String expectedStr, String description) {
        // Parse CSV string into int[]
        int[] nums = parseCsvLine(inputStr);
        int[] expected = parseCsvLine(expectedStr);

        // Invoke the actual method
        shiftZerosToTheEnd(nums);

        System.out.println(expectedStr + " - " + Arrays.toString(nums));

        // Assert the modified array matches the expected output
        assertArrayEquals(expected, nums, "Failed: " + description);
    }

    private int[] parseCsvLine(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return new int[0];
        }
        return Arrays.stream(csvLine.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    /**
     * Shifts all zeros in the array to the end in-place while maintaining relative order of non-zeros.
     * 
     * Algorithm: Two-Pointer Write-Pointer Swap
     * - 'left' pointer points to the boundary where the next non-zero element should be written.
     * - 'right' pointer scans the array from left to right.
     * - When nums[right] != 0, if it is different from left, we swap nums[left] and nums[right],
     *   then increment 'left'.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    void shiftZerosToTheEnd(int[] nums) {
        if (nums.length > 1) {
            int left = 0;
            while(left<nums.length && nums[left]!=0) left++;

            int right = left+1;
            while(right<nums.length) {
                if(nums[right]!=0) {
                    nums[left] = nums[right];
                    nums[right] = 0;
                    left++;
                }
                right++;
            }

        }

    }

    //byte byte go solution
    void shiftZerosToTheEnd2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        // The 'left' pointer is used to position non-zero elements.
        int left = 0;

        // Iterate through the array using a 'right' pointer to locate non-zero elements.
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != 0) {
                if (right != left) {
                    // Swap nums[left] and nums[right]
                    int temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;
                }
                // Increment 'left' since it now points to a position already occupied
                // by a non-zero element.
                left++;
            }
        }
    }



    @ParameterizedTest(name = "Heights: {0} -> Expected: {1}")
    @CsvSource(delimiter = ':', value = {
            "2,7,8,3,7,6 : 24",
            "3,3,3,3     : 9",
            "1,2,3       : 2",
            "3,2,1       : 2",
            "0,1,0       : 0",
            "1           : 0"
    })
    public void testLargestContainer(String inputStr, int expectedOutput) {
        int[] heights = Arrays.stream(inputStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int actualOutput = computeLargestContainer(heights);
        assertEquals(expectedOutput, actualOutput, "Failed for input: " + inputStr);
    }

    /**
     * Computes the maximum area of water a container can hold between two vertical lines.
     * 
     * Algorithm: Two-Pointer Greedy Approach
     * - Initialize 'left' pointer at start and 'right' pointer at end.
     * - Calculate the area: width (right - left) multiplied by the shorter of the two heights.
     * - Update 'maxWater' with the maximum area seen.
     * - Move the pointer pointing to the shorter line inward, as keeping it cannot yield a larger area
     *   (since the width decreases and the height will be limited by this shorter or an even shorter line).
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    private int computeLargestContainer(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int maxWater = 0;
        int left = 0;
        int right = heights.length - 1;

        while (left < right) {
            int width = right - left;
            int currentWater = Math.min(heights[left], heights[right]) * width;
            maxWater = Math.max(maxWater, currentWater);

            if (heights[left] < heights[right]) {
                left++;
            } else if (heights[left] > heights[right]) {
                right--;
            } else {
                left++;
                right--;
            }
        }

        return maxWater;
    }



    @ParameterizedTest(name = "Heights: {0} -> Expected: {1}")
    @CsvSource(delimiter = ':', value = {
            "0,1,0,2,1,0,1,3,2,1,2,1 : 6",
            "4,2,0,3,2,5             : 9",
            "3,3,3,3                 : 0",
            "1,2,3,4                 : 0",
            "1                       : 0"
    })
    public void testTrappingRainWater(String inputStr, int expectedOutput) {
        int[] heights = Arrays.stream(inputStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int actualOutput = computeTrappedWater(heights);
        assertEquals(expectedOutput, actualOutput, "Failed for input: " + inputStr);
    }

    /**
     * Computes the total amount of water trapped after raining, given an elevation map.
     * 
     * Algorithm: Two-Pointer Boundary Tracking
     * - Initialize 'left' at 0, 'right' at heights.length - 1, and 'leftMax' & 'rightMax' boundaries to 0.
     * - Compare heights[left] and heights[right].
     * - If heights[left] is smaller, water height at 'left' is bounded by leftMax.
     *   Update leftMax, calculate trapped water at 'left' (leftMax - heights[left]), and increment left.
     * - Otherwise, water height is bounded by rightMax. Update rightMax, calculate water (rightMax - heights[right]),
     *   and decrement right.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    private int computeTrappedWater(int[] heights) {
        if (heights == null || heights.length < 3) {
            return 0;
        }

        int left = 0;
        int right = heights.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (heights[left] < heights[right]) {
                if (heights[left] >= leftMax) {
                    leftMax = heights[left];
                } else {
                    totalWater += leftMax - heights[left];
                }
                left++;
            } else {
                if (heights[right] >= rightMax) {
                    rightMax = heights[right];
                } else {
                    totalWater += rightMax - heights[right];
                }
                right--;
            }
        }

        return totalWater;
    }


    @ParameterizedTest
    @CsvSource({
            "'abcd', 'abdc'",
            "'dcba', 'abcd'",
            "'a', 'a'",
            "'aaaa', 'aaaa'",
            "'ynitsed', 'ynsdeit'",
            "'ab', 'ba'",
            "'ba', 'ab'",
            "'fedcb', 'bcdef'"
    })
    void testNextLexicographicalSequence(String input, String expected) {
        assertEquals(expected, nextLexicographicalSequence(input));
    }

    /**
     * Finds the next lexicographical permutation of the string sequence.
     * 
     * Algorithm: Pivot-Swap-Reverse (Single Pass from Right)
     * - 1. Scan from right to left to find the first decreasing character (the pivot), where s[pivot] < s[pivot+1].
     * - 2. If no pivot is found, the sequence is in descending order; reverse the entire array.
     * - 3. If pivot exists, scan from right to left to find the first character strictly greater than the pivot.
     * - 4. Swap the pivot character and this character.
     * - 5. Reverse the subarray to the right of the pivot to make it lexicographically smallest.
     * Time Complexity: O(N)
     * Space Complexity: O(N) to store the modified character array.
     */
    public String nextLexicographicalSequence(String s) {
        char[] chars = s.toCharArray();

        if (chars.length < 2) return s;

        int pivot = s.length() - 2;

        while(pivot>=0 && chars[pivot]>=chars[pivot+1]) pivot--;

        if(pivot<0) return new String(reverseArray(chars, 0, chars.length-1));

        int nextBiggestNumber = chars.length - 1;

        while(chars[nextBiggestNumber] <= chars[pivot]) nextBiggestNumber--;

        char temp = chars[nextBiggestNumber];
        chars[nextBiggestNumber] = chars[pivot];
        chars[pivot] = temp;

        return new String(reverseArray(chars, pivot + 1, chars.length-1));
    }

    public char[] reverseArray(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;end--;
        }
        return chars;
    }



    //only for one valid pair
    @ParameterizedTest
    @CsvSource({
            "'-1,3,4,2', 3, '0,2'",    // Sample Example
            "'2,7,11,15', 9, '0,1'",   // Basic case
            "'1,2,3', 7, ''",          // No pair found
            "'3,4,3', 6, '0,2'",       // Duplicate values forming target
            "'3,3,3,5', 8, '2,3'",     // Multiple of the same digits
            "'-5,-2,-3,1', -7, '0,1'", // Negative numbers
            "'5,-5,2,3', 0, '0,1'",    // Target is zero
            "'', 5, ''"                // Empty array
    })
    void testPairSumUnsorted(String numsStr, int target, String expectedStr) {
        int[] nums = numsStr == null || numsStr.isEmpty() ? new int[0] :
                java.util.Arrays.stream(numsStr.split(",")).mapToInt(Integer::parseInt).toArray();

        int[] expected = expectedStr == null || expectedStr.isEmpty() ? new int[0] :
                java.util.Arrays.stream(expectedStr.split(",")).mapToInt(Integer::parseInt).toArray();

        int[] actual = pairSumUnsorted(nums, target);

        java.util.Arrays.sort(actual);
        java.util.Arrays.sort(expected);

        assertArrayEquals(expected, actual);
    }

    /**
     * Finds indices of two numbers in an unsorted array that sum to the target.
     * 
     * Algorithm: Hash Map Lookup
     * - Create a HashMap mapping value to its index.
     * - For each element nums[i], check if (target - nums[i]) exists in the map.
     * - If it does, we found the pair; return its indices.
     * - Otherwise, put nums[i] and its index in the map.
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public int[] pairSumUnsorted(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }

        return new int[0];
    }


    @ParameterizedTest
    @MethodSource("main.codingInterview.practice.ProblemData#sudokuTestData")
    void verifySudokuBoard_ShouldReturnExpectedValidity(int[][] board, boolean expected) {
        // Act & Assert
        assertEquals(expected, verifySudokuBoard(board));
    }

    /**
     * Verifies if a given 9x9 Sudoku board is valid (no duplicates in rows, columns, or 3x3 subgrids).
     * 
     * Algorithm: Hash Sets validation
     * - Create 9 HashSets for rows, 9 HashSets for columns, and a 3x3 array of HashSets for boxes.
     * - Iterate through every cell. If a cell contains a non-zero digit, check if it has already been seen
     *   in its corresponding row, column, or 3x3 subgrid box (boxIndex = rowIndex / 3, colIndex / 3).
     * - If duplicate is found, return false. Otherwise, add the digit to the sets.
     * Time Complexity: O(1) since the board is always a fixed size of 81 cells.
     * Space Complexity: O(1) for a fixed size grid.
     */
    public boolean verifySudokuBoard(int[][] board) {
        HashSet<Integer>[] rows = (HashSet<Integer>[]) new HashSet[9];
        HashSet<Integer>[] cols = (HashSet<Integer>[]) new HashSet[9];
        HashSet<Integer>[][] boxes = (HashSet<Integer>[][]) new HashSet[3][3];

        for(int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                boxes[i][j] = new HashSet<>();
            }
        }

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] != 0) {
                    if(rows[i].contains(board[i][j])) return false;
                    else rows[i].add(board[i][j]);

                    if(cols[j].contains(board[i][j])) return false;
                    else cols[j].add(board[i][j]);

                    if(boxes[i/3][j/3].contains(board[i][j])) return false;
                    else boxes[i/3][j/3].add(board[i][j]);
                }

            }
        }

        return true;
    }




    @ParameterizedTest
    @MethodSource("main.codingInterview.practice.ProblemData#zeroStripingData")
    void testZeroStriping(int[][] matrix, int[][] expected) {
        zeroStriping(matrix);
        assertArrayEquals(expected, matrix);
    }

    /**
     * Modifies a matrix such that if an element is 0, its entire row and column are set to 0.
     * 
     * Algorithm: In-Place Markers (O(1) Auxiliary Space)
     * - Use the first row and first column of the matrix as state markers.
     * - First, determine if the first row or first column themselves should be zeroed initially.
     * - Iterate through the rest of the matrix (1..rows, 1..cols). If matrix[i][j] == 0,
     *   set matrix[i][0] = 0 and matrix[0][j] = 0 as indicators.
     * - Iterate again through the submatrix (1..rows, 1..cols) and set matrix[i][j] to 0 if its row/col marker is 0.
     * - Finally, zero out the first row and/or first column if the initial flags were true.
     * Time Complexity: O(M * N)
     * Space Complexity: O(1) auxiliary space.
     */
    public void zeroStriping(int[][] matrix) {
        boolean row_contains_zeroes = false;
        boolean col_contains_zeroes = false;

        int rows = matrix.length;
        int cols = matrix[0].length;

        for(int i = 0; i < cols; i++) {
            if(matrix[0][i] == 0) row_contains_zeroes = true;
        }

        for(int i = 0; i < rows; i++) {
            if(matrix[i][0] == 0) col_contains_zeroes = true;
        }

        //create markers
        for(int i = 1; i < rows; i++) {
            for(int j = 1; j < cols; j++) {
                if(matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        //use markers to fill zeros
        for(int i = 1; i < rows; i++) {
            for(int j = 1; j < cols; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
            }
        }

        if (row_contains_zeroes) {
            for(int i = 0; i < cols; i++) matrix[0][i] = 0;
        }

        if (col_contains_zeroes) {
            for(int i = 0; i < rows; i++) matrix[i][0] = 0;
        }

    }



    @ParameterizedTest
    @CsvSource({
            // format: "arrayElements separated by space | expectedOutput"
            "'1 6 2 5 8 7 10 3', 4",  // Sample example
            "'', 0",                 // Empty array
            "'5', 1",                // Single element
            "'1 2 3 4 5', 5",        // All consecutive
            "'10 20 30 40', 1",      // No consecutive elements
            "'1 2 2 3 4', 4",        // Duplicates within the chain
            "'0 -1 -2 -3 1 2', 6",   // Negative numbers
            "'2147483646 2147483647', 2" // Integer boundaries
    })
    void testLongestChainOfConsecutiveNumbers(String numsInput, int expected) {
        int[] nums = numsInput == null || numsInput.trim().isEmpty()
                ? new int[0]
                : Arrays.stream(numsInput.split(" ")).mapToInt(Integer::parseInt).toArray();

        assertEquals(expected, longestChainOfConsecutiveNumbers(nums));
    }

    /**
     * Finds the length of the longest consecutive elements sequence in an unsorted array.
     * 
     * Algorithm: Hash Set Boundary Scan
     * - Insert all array elements into a HashSet for O(1) lookups.
     * - Iterate through the set. For each number, check if it is the start of a sequence by verifying
     *   that 'num - 1' is not present in the set.
     * - If it is the start, traverse upward checking for 'num + 1', 'num + 2', etc., counting the length.
     * - Track the maximum length found.
     * Time Complexity: O(N) because each element is visited at most twice.
     * Space Complexity: O(N) to store elements in the set.
     */
    public int longestChainOfConsecutiveNumbers(int[] nums) {
        int longest_chain = 0;

        HashSet<Integer> numSet = new HashSet<>();
        Arrays.stream(nums).forEach(numSet::add);

        for (int num : numSet) {
            if(!numSet.contains(num-1)) {
                int current_chain_length = 1;

                int current_num = num;
                while(numSet.contains(current_num + 1)) {
                    current_chain_length++;
                    current_num++;
                }

                longest_chain = Math.max(longest_chain, current_chain_length);
            }
        }

        return longest_chain;
    }


    @ParameterizedTest
    @CsvSource({
            // format: "comma-separated-array-elements", ratio, expected_output
            "'2,1,2,4,8,8', 2, 5",
            "'2,3,4', 2, 0",
            "'', 2, 0",
            "'1,2', 2, 0",
            "'5,5,5,5', 1, 4",
            "'1,3,9,2,4,8', 3, 1",
            "'4,2,1', 2, 0",
            "'1,2,4,8,16', 2, 3"
    })
    void testGeometricSequenceTriplets(String numsStr, int r, int expected) {
        int[] nums;
        if (numsStr == null || numsStr.trim().isEmpty()) {
            nums = new int[0];
        } else {
            nums = java.util.Arrays.stream(numsStr.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        assertEquals(expected, geometric_sequence_triplets(nums, r));
    }

    /**
     * Counts geometric triplets of indices (i, j, k) such that i < j < k and nums[j]/nums[i] = nums[k]/nums[j] = r.
     * 
     * Algorithm: Left-Right Hash Map Frequency Tracking
     * - Create a 'rightMap' storing frequency counts of all numbers in the array.
     * - Create a 'leftMap' to store frequency counts of numbers processed so far.
     * - Iterate through each element 'x', treating it as the middle element of the triplet (x / r, x, x * r).
     * - Decrement x's count in rightMap (since it's now the center, not on the right).
     * - If x is divisible by r, the number of valid triplets with center x is:
     *   leftMap.getOrDefault(x / r) * rightMap.getOrDefault(x * r).
     * - Increment x's count in leftMap.
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public int geometric_sequence_triplets(int[] nums, int r) {
        HashMap<Long, Long> leftMap = new HashMap<>();
        HashMap<Long, Long> rightMap = new HashMap<>();
        long count = 0;
        // Populate 'rightMap' with the frequency of each element in the array.
        for (int num : nums) {
            long x = (long) num;
            rightMap.put(x, rightMap.getOrDefault(x, 0L) + 1);
        }
        // Search for geometric triplets that have x as the center.
        for (int num : nums) {
            long x = (long) num;
            // Decrement the frequency of x in 'rightMap' since x is now being
            // processed and is no longer to the right.
            rightMap.put(x, rightMap.get(x) - 1);
            if (x % r == 0) {
                count += leftMap.getOrDefault(x / r, 0L) * rightMap.getOrDefault(x * r, 0L);
            }
            // Increment the frequency of x in 'leftMap' since it'll be a part of the
            // left side of the array once we iterate to the next value of `x`.
            leftMap.put(x, leftMap.getOrDefault(x, 0L) + 1);
        }
        return (int) count;
    }


    /*
    ## Problem Statement: Reverse a Linked List

    Given the head of a singly linked list, reverse the list, and return the reversed list.

    ### Constraints & Edge Cases to Consider

    * The list can be empty (return null).
    * The list can have only one element (return the element).
    * The list can have multiple elements.

    ### Examples

    * **Input:** `head = [1, 2, 3, 4, 5]`
      **Output:** `[5, 4, 3, 2, 1]`
     */

    @ParameterizedTest(name = "Original: {0} -> Expected: {1}")
    @CsvSource(value = {
            "'', ''",
            "'1', '1'",
            "'1,2', '2,1'",
            "'1,2,3,4,5', '5,4,3,2,1'"
    })
    void testReverseLinkedList(String inputStr, String expectedStr) {
        ListNode head = LinkedListUtils.createLinkedList(inputStr);
        ListNode reversed = reverseLinkedList(head);
        String actualStr = LinkedListUtils.linkedListToString(reversed);
        assertEquals(expectedStr == null ? "" : expectedStr.trim(), actualStr);
    }

    /**
     * Reverses a singly linked list in-place.
     * 
     * Algorithm: Iterative Pointer Redirection
     * - Maintain three pointers: 'prev' (starts as null), 'curr' (starts as head), and a temporary 'nextTemp'.
     * - In a loop, store curr.next in nextTemp, redirect curr.next to point to prev,
     *   move prev to curr, and advance curr to nextTemp.
     * - Return 'prev' as the new head of the reversed list.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public ListNode reverseLinkedList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /*
    ## Problem Statement: Remove the Kth Last Node From a Linked List

    Given a singly linked list and an integer `k`, remove the `k`-th node from the end of the list and return its head.

    ### Constraints & Edge Cases to Consider

    * `k` is guaranteed to be a valid size (between 1 and the length of the list).
    * The head of the list could be removed (e.g. removing the size-th node from end).
    * The list can have only one element.

    ### Examples

    * **Input:** `head = [1, 2, 3, 4, 5]`, `k = 2`
      **Output:** `[1, 2, 3, 5]`
    * **Input:** `head = [1]`, `k = 1`
      **Output:** `[]`
    * **Input:** `head = [1, 2]`, `k = 1`
      **Output:** `[1]`
     */

    @ParameterizedTest(name = "Original: {0}, k: {1} -> Expected: {2}")
    @CsvSource(value = {
            "'1,2,3,4,5': 2 : '1,2,3,5'",
            "'1'         : 1 : ''",
            "'1,2'       : 1 : '1'",
            "'1,2'       : 2 : '2'"
    }, delimiter = ':')
    void testRemoveKthLastNode(String inputStr, int k, String expectedStr) {
        ListNode head = LinkedListUtils.createLinkedList(inputStr);
        ListNode result = removeKthLastNode(head, k);
        String actualStr = LinkedListUtils.linkedListToString(result);
        assertEquals(expectedStr == null ? "" : expectedStr.trim(), actualStr);
    }

    /**
     * Removes the K-th node from the end of the singly linked list.
     * 
     * Algorithm: Leader-Trailer (Fast-Slow) Two-Pointer Approach
     * - Use a 'dummy' node pointing to head to simplify edge cases (like removing the head).
     * - Initialize 'leader' and 'trailer' pointers at the dummy node.
     * - Advance the 'leader' pointer k steps ahead.
     * - Move both 'leader' and 'trailer' pointers one node at a time until 'leader.next' is null (leader reaches last node).
     * - Since 'leader' is k nodes ahead, 'trailer' will point to the node immediately before the K-th last node.
     * - Skip the K-th last node by setting trailer.next = trailer.next.next.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public ListNode removeKthLastNode(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode leader = dummy;
        ListNode trailer = dummy;

        // Move leader pointer k steps ahead
        for (int i = 0; i < k; i++) {
            leader = leader.next;
        }

        // Move both pointers until leader reaches the end
        while (leader.next != null) {
            leader = leader.next;
            trailer = trailer.next;
        }

        // Remove the kth last node
        trailer.next = trailer.next.next;

        return dummy.next;
    }


    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4,1   ; 5,6,1 ; 8,4,5 ; true",  // Standard intersection (Sample Example)
            "2,6,4 ; 1,5   ; null  ; false", // No intersection
            "null  ; 1,2,3 ; null  ; false", // One list is empty, no intersection
            "null  ; null  ; 1,2,3 ; true",  // Complete overlap (Both start at intersection)
            "null  ; 9,11  ; 7     ; true"   // Intersect at tail node (List A starts at intersection)
    })
    void testLinkedListIntersection(String uniqueA, String uniqueB, String common, boolean hasIntersection) {
        java.util.function.Function<String, ListNode> buildSegment = (String str) -> {
            if (str == null || str.trim().equals("null") || str.trim().isEmpty()) return null;
            String[] tokens = str.split(",");
            ListNode dummy = new ListNode(0);
            ListNode curr = dummy;
            for (String token : tokens) {
                curr.next = new ListNode(Integer.parseInt(token.trim()));
                curr = curr.next;
            }
            return dummy.next;
        };

        java.util.function.Function<ListNode, ListNode> getTail = (ListNode head) -> {
            if (head == null) return null;
            ListNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            return curr;
        };

        ListNode headA = buildSegment.apply(uniqueA);
        ListNode headB = buildSegment.apply(uniqueB);
        ListNode commonPart = buildSegment.apply(common);

        if (headA != null) {
            getTail.apply(headA).next = commonPart;
        } else {
            headA = commonPart;
        }

        if (headB != null) {
            getTail.apply(headB).next = commonPart;
        } else {
            headB = commonPart;
        }

        ListNode actual = linkedListIntersection(headA, headB);

        if (hasIntersection) {
            assertEquals(commonPart, actual);
        } else {
            assertNull(actual);
        }
    }

    /**
     * Finds the intersection node of two singly linked lists.
     * 
     * Algorithm: Two-Pointer Length Synchronization
     * - Initialize 'pointerA' at headA and 'pointerB' at headB.
     * - Advance both pointers. When 'pointerA' reaches null, redirect it to headB.
     * - When 'pointerB' reaches null, redirect it to headA.
     * - This ensures both pointers travel the exact same total distance: len(uniqueA) + len(common) + len(uniqueB).
     * - They will meet at either the intersection node (if it exists) or null (if no intersection).
     * Time Complexity: O(N + M)
     * Space Complexity: O(1)
     */
    public ListNode linkedListIntersection(ListNode headA, ListNode headB) {
        ListNode pointrA = headA;
        ListNode pointrB = headB;

        while(pointrA != pointrB) {
            /*
             * If unique segment lengths are different (e.g., A = a+b, B = c+b),
             * wrapping the pointers to the opposite list forces both to travel
             * an identical total distance: (a + b) + c == (c + b) + a.
             * * This aligns the pointers perfectly on their second pass,
             * ensuring they enter the shared intersection 'b' at the exact same time.
             * If no intersection exists, both reach the end and meet at 'null' simultaneously.
             */
            pointrA = pointrA != null ? pointrA.next : headB;
            pointrB = pointrB != null ? pointrB.next : headA;
        }

        return pointrA;
    }

    /**
     * Validates the LRU Cache implementation.
     * 
     * Test Case:
     * - Initialize a cache of capacity 2.
     * - Put key-value pairs (1, 1) and (2, 2).
     * - Verify that retrieving key 1 returns 1.
     * - Put (3, 3), which should evict the least recently used key (2).
     * - Verify that retrieving key 2 returns -1 (not found).
     * - Put (4, 4), which should evict the least recently used key (1).
     * - Verify that retrieving key 1 returns -1.
     * - Verify that retrieving key 3 returns 3 and key 4 returns 4.
     */
    @Test
    void testLRUCache() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.get(1));       // returns 1
        cache.put(3, 3);                     // evicts key 2 (1 was accessed, so 2 is LRU)
        assertEquals(-1, cache.get(2));      // returns -1 (not found)
        cache.put(4, 4);                     // evicts key 1 (3 is most recent, 1 was older than 3)
        assertEquals(-1, cache.get(1));      // returns -1 (not found)
        assertEquals(3, cache.get(3));       // returns 3
        assertEquals(4, cache.get(4));       // returns 4
    }



    /**
     * Checks if a singly linked list is a palindrome.
     * 
     * Algorithm: Fast & Slow Pointer Middle Search with Second Half Reversal
     * 1. Find the end of the first half using the fast and slow pointer technique:
     *    - 'slow' moves one step at a time, 'fast' moves two steps.
     *    - When 'fast.next' or 'fast.next.next' is null, 'slow' points to the end of the first half.
     * 2. Reverse the second half of the list starting from 'slow.next'.
     * 3. Compare the values of the first half and the reversed second half.
     *    - We only need to check while the reversed second half pointer is not null.
     * 4. Restore the original list structure by reversing the second half back (best practice).
     * 5. Return whether the list is a palindrome.
     * 
     * Time Complexity: O(N) since we traverse the list a constant number of times.
     * Space Complexity: O(1) auxiliary space as we modify the pointers in-place.
     */
    public boolean palindromic_linked_list(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1. Find the end of the first half
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        ListNode secondHalfHead = reverseLinkedList(slow.next);

        // 3. Compare the first half and the reversed second half
        ListNode pointrA = secondHalfHead;
        ListNode pointrB = head;
        boolean isPalindrome = true;
        while (pointrA != null) {
            if (pointrA.val != pointrB.val) {
                isPalindrome = false;
                break;
            }
            pointrA = pointrA.next;
            pointrB = pointrB.next;
        }

        // 4. Restore the original list structure
        slow.next = reverseLinkedList(secondHalfHead);

        return isPalindrome;
    }

    @ParameterizedTest
    @CsvSource({
            "'1,2,2,1', true",   // Example 1
            "'1,2', false",      // Example 2
            "'1', true",         // Edge Case: Single element
            "'', true",          // Edge Case: Empty list
            "'1,2,3,2,1', true", // Odd length palindrome
            "'1,2,3,4,1', false",// Odd length non-palindrome
            "'7,7,7,7', true"    // All identical elements
    })
    void testPalindromicLinkedList(String listStr, boolean expected) {
        ListNode head = LinkedListUtils.createLinkedList(listStr);
        assertEquals(expected, palindromic_linked_list(head));
    }

}


