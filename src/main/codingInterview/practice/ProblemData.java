package main.codingInterview.practice;

import org.junit.jupiter.params.provider.Arguments;

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
}
