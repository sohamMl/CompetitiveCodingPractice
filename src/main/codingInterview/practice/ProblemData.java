package main.codingInterview.practice;

import org.junit.jupiter.params.provider.Arguments;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Data provider class containing test data streams for parameterized tests in Problems.java.
 * Leverages JUnit 5's MethodSource to feed inputs and expected values into test methods.
 */
public class ProblemData {
    /**
     * Provides test boards for the Sudoku board validation test.
     * Each test argument contains a 9x9 grid representation of a board and a boolean representing whether the board is valid.
     *
     * @return a stream of arguments (int[][] board, boolean expectedValidity)
     */
    public static Stream<Arguments> sudokuTestData() {
        return Stream.of(
                // Edge Case 1: Empty board (valid)
                Arguments.of(new int[][] {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}
                }, true),

                // Case 2: Valid partially filled board
                Arguments.of(new int[][] {
                        {5, 3, 0, 0, 7, 0, 0, 0, 0},
                        {6, 0, 0, 1, 9, 5, 0, 0, 0},
                        {0, 9, 8, 0, 0, 0, 0, 6, 0},
                        {8, 0, 0, 0, 6, 0, 0, 0, 3},
                        {4, 0, 0, 8, 0, 3, 0, 0, 1},
                        {7, 0, 0, 0, 2, 0, 0, 0, 6},
                        {0, 6, 0, 0, 0, 0, 2, 8, 0},
                        {0, 0, 0, 4, 1, 9, 0, 0, 5},
                        {0, 0, 0, 0, 8, 0, 0, 7, 9}
                }, true),

                // Edge Case 3: Invalid row duplicate (same row has duplicate 5s)
                Arguments.of(new int[][] {
                        {5, 3, 0, 0, 7, 0, 0, 0, 5},
                        {6, 0, 0, 1, 9, 5, 0, 0, 0},
                        {0, 9, 8, 0, 0, 0, 0, 6, 0},
                        {8, 0, 0, 0, 6, 0, 0, 0, 3},
                        {4, 0, 0, 8, 0, 3, 0, 0, 1},
                        {7, 0, 0, 0, 2, 0, 0, 0, 6},
                        {0, 6, 0, 0, 0, 0, 2, 8, 0},
                        {0, 0, 0, 4, 1, 9, 0, 0, 5},
                        {0, 0, 0, 0, 8, 0, 0, 7, 9}
                }, false),

                // Edge Case 4: Invalid column duplicate (first column has duplicate 5s)
                Arguments.of(new int[][] {
                        {5, 3, 0, 0, 7, 0, 0, 0, 0},
                        {6, 0, 0, 1, 9, 5, 0, 0, 0},
                        {5, 9, 8, 0, 0, 0, 0, 6, 0},
                        {8, 0, 0, 0, 6, 0, 0, 0, 3},
                        {4, 0, 0, 8, 0, 3, 0, 0, 1},
                        {7, 0, 0, 0, 2, 0, 0, 0, 6},
                        {0, 6, 0, 0, 0, 0, 2, 8, 0},
                        {0, 0, 0, 4, 1, 9, 0, 0, 5},
                        {0, 0, 0, 0, 8, 0, 0, 7, 9}
                }, false),

                // Edge Case 5: Invalid subgrid duplicate (top-left 3x3 box has duplicate 3s)
                Arguments.of(new int[][] {
                        {5, 3, 0, 0, 7, 0, 0, 0, 0},
                        {6, 3, 0, 1, 9, 5, 0, 0, 0},
                        {0, 9, 8, 0, 0, 0, 0, 6, 0},
                        {8, 0, 0, 0, 6, 0, 0, 0, 3},
                        {4, 0, 0, 8, 0, 3, 0, 0, 1},
                        {7, 0, 0, 0, 2, 0, 0, 0, 6},
                        {0, 6, 0, 0, 0, 0, 2, 8, 0},
                        {0, 0, 0, 4, 1, 9, 0, 0, 5},
                        {0, 0, 0, 0, 8, 0, 0, 7, 9}
                }, false)
        );
    }



    /**
     * Provides test matrices for the zero striping (set matrix zeroes) test.
     * Each test argument contains an input matrix and the expected transformed matrix.
     *
     * @return a stream of arguments (int[][] matrix, int[][] expectedMatrix)
     */
    public static Stream<Arguments> zeroStripingData() {
        return Stream.of(
                // Standard Example 1: Basic submatrix zeroing
                Arguments.of(
                        new int[][]{
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        },
                        new int[][]{
                                {1, 0, 1},
                                {0, 0, 0},
                                {1, 0, 1}
                        }
                ),
                // Standard Example 2: Multiple zeros affecting same rows/cols
                Arguments.of(
                        new int[][]{
                                {0, 1, 2, 0},
                                {3, 4, 5, 2},
                                {1, 3, 1, 5}
                        },
                        new int[][]{
                                {0, 0, 0, 0},
                                {0, 4, 5, 0},
                                {0, 3, 1, 0}
                        }
                ),
                // Edge Case: Zero originally present in the first row only
                Arguments.of(
                        new int[][]{
                                {1, 0, 3},
                                {4, 5, 6},
                                {7, 8, 9}
                        },
                        new int[][]{
                                {0, 0, 0},
                                {4, 0, 6},
                                {7, 0, 9}
                        }
                ),
                // Edge Case: Zero originally present in the first column only
                Arguments.of(
                        new int[][]{
                                {1, 2, 3},
                                {0, 5, 6},
                                {7, 8, 9}
                        },
                        new int[][]{
                                {0, 2, 3},
                                {0, 0, 0},
                                {0, 8, 9}
                        }
                ),
                // Edge Case: Zero at origin matrix[0][0] (both first row and first col)
                Arguments.of(
                        new int[][]{
                                {0, 2, 3},
                                {4, 5, 6},
                                {7, 8, 9}
                        },
                        new int[][]{
                                {0, 0, 0},
                                {0, 5, 6},
                                {0, 8, 9}
                        }
                ),
                // Edge Case: 1x1 Matrix with a zero
                Arguments.of(
                        new int[][]{{0}},
                        new int[][]{{0}}
                ),
                // Edge Case: 1x1 Matrix without a zero
                Arguments.of(
                        new int[][]{{5}},
                        new int[][]{{5}}
                ),
                // Edge Case: No zeros present in the entire matrix
                Arguments.of(
                        new int[][]{
                                {1, 2},
                                {3, 4}
                        },
                        new int[][]{
                                {1, 2},
                                {3, 4}
                        }
                ),
                // Edge Case: Entire matrix filled with zeros
                Arguments.of(
                        new int[][]{
                                {0, 0},
                                {0, 0}
                        },
                        new int[][]{
                                {0, 0},
                                {0, 0}
                        }
                )
        );
    }


    /**
     * Provides a stream of test arguments for validating the multi-level list flattening algorithm.
     * Each test case contains the head of a multi-level list and the expected flattened sequence of integers.
     *
     * @return a Stream of Arguments containing (MultiLevelListNode head, int[] expectedFlattenedArray)
     */
    public static Stream<Arguments> testFlattenMultiLevelListData() {
        // Helper to build a multi-level list from a node array where index [i] connects to [i+1] via next
        // and children are linked manually for specific test cases.
        Function<int[], MultiLevelListNode> createList = (vals) -> {
            if (vals == null || vals.length == 0) return null;
            MultiLevelListNode[] nodes = new MultiLevelListNode[vals.length];
            for (int i = 0; i < vals.length; i++) {
                nodes[i] = new MultiLevelListNode(vals[i], null, null);
            }
            for (int i = 0; i < vals.length - 1; i++) {
                nodes[i].next = nodes[i + 1];
            }
            return nodes[0];
        };

        // Case 1: Standard multi-level list
        // L1: 1 -> 2 -> 3 -> 4
        //          |
        // L2:      5 -> 6
        //               |
        // L3:           7 -> 8
        MultiLevelListNode h1 = createList.apply(new int[]{1, 2, 3, 4});
        MultiLevelListNode c1 = createList.apply(new int[]{5, 6});
        MultiLevelListNode c2 = createList.apply(new int[]{7, 8});
        h1.next.child = c1; // 2 -> 5
        c1.next.child = c2; // 6 -> 7
        int[] expected1 = {1, 2, 3, 4, 5, 6, 7, 8};

        // Case 2: Single level list (no children)
        MultiLevelListNode h2 = createList.apply(new int[]{10, 20, 30});
        int[] expected2 = {10, 20, 30};

        // Case 3: Single node with child
        MultiLevelListNode h3 = new MultiLevelListNode(1, null, new MultiLevelListNode(2, null, null));
        int[] expected3 = {1, 2};

        // Case 4: Empty list
        MultiLevelListNode h4 = null;
        int[] expected4 = {};

        return Stream.of(
                Arguments.of(h1, expected1),
                Arguments.of(h2, expected2),
                Arguments.of(h3, expected3),
                Arguments.of(h4, expected4)
        );
    }
}
