package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;


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

}
