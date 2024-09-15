package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProblemsTest extends TestBase {

    //https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    @ParameterizedTest
    @ValueSource(strings = {
            "8,3,4,5,7,7,8,8,10",
            "6,-1,-1,5,7,7,8,8,10",
            "0,-1,-1"
    })
    public void searchRange(String input) {
        int[] nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] arr = nums.length == 3 ? new int[0] : Arrays.stream(nums, 3, nums.length).toArray();
        int[] result = searchRange(arr, nums[0]);
        System.out.println(Arrays.toString(result));
        Assertions.assertEquals(nums[1], result[0]);
        Assertions.assertEquals(nums[2], result[1]);

    }

    public int[] searchRange(int[] nums, int target) {
        int l = -1, r = -1;
        if (nums.length == 0) return new int[]{l, r};

        int low = 0, high = nums.length - 1, mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                r = mid;
                low = mid + 1;
            } else if (target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        }

        low = 0;
        high = nums.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (nums[mid] == target) {
                l = mid;
                high = mid - 1;
            } else if (target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        }

        return new int[]{l, r};
    }

    //https://leetcode.com/problems/valid-sudoku/description/
    @ParameterizedTest
    @ValueSource(strings = {
            "true:[[\"5\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]",
            "false:[[\"8\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]",
            "false:[[\".\",\".\",\".\",\".\",\"5\",\".\",\".\",\"1\",\".\"],[\".\",\"4\",\".\",\"3\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\".\",\".\",\".\",\"3\",\".\",\".\",\"1\"],[\"8\",\".\",\".\",\".\",\".\",\".\",\".\",\"2\",\".\"],[\".\",\".\",\"2\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\".\",\"1\",\"5\",\".\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\"],[\".\",\"2\",\".\",\"9\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\"4\",\".\",\".\",\".\",\".\",\".\",\".\"]]",
    })
    public void isValidSudoku(String input) {
        Boolean expected = Boolean.parseBoolean(input.split(":")[0]);
        String data = input.split(":")[1];
        char board[][] = new char[9][9];
        String[] rowList = data.substring(2,data.length()-2).split("\\],\\[");
        String splitChar = "\",\"";
        for(int i=0;i<9;i++) {
            //System.out.println(Arrays.toString(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)));
            Character[] values = Arrays.stream(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)).map(s -> s.charAt(0)).toArray(Character[]::new);
            //System.out.println(Arrays.toString(values));
            for(int j=0;j<9;j++) board[i][j] = values[j];
        }
        //Arrays.stream(board).toList().forEach(row -> System.out.println(Arrays.toString(row)));
        Assertions.assertEquals(expected, isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        List<Set<Character>> columnList = new ArrayList<>();
        for(int i=0;i<9;i++) columnList.add(new HashSet<Character>());

        Set<Character> block_1, block_2, block_3;
        block_1 = new HashSet<Character>();
        block_2 = new HashSet<Character>();
        block_3 = new HashSet<Character>();

        for(int i=0;i<9;i++) {
            Set<Character> rowSet = new HashSet<>();

            if(i==3 || i==6) {
                block_1 = new HashSet<Character>();
                block_2 = new HashSet<Character>();
                block_3 = new HashSet<Character>();
            }

            for(int j=0;j<9;j++) {
                if(board[i][j] == '.') continue;

                if(rowSet.contains(board[i][j])) return false;
                else rowSet.add(board[i][j]);

                if(columnList.get(j).contains(board[i][j])) return false;
                else columnList.get(j).add(board[i][j]);

                if(j<3) {
                    if (block_1.contains(board[i][j])) return false;
                    else block_1.add(board[i][j]);
                } else if(j<6) {
                    if (block_2.contains(board[i][j])) return false;
                    else block_2.add(board[i][j]);
                } else {
                    if (block_3.contains(board[i][j])) return false;
                    else block_3.add(board[i][j]);
                }
            }
        }

        return true;
    }


}
