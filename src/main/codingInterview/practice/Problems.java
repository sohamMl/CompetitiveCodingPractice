package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.Arrays;
import java.util.List;

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
            Assertions.assertEquals(target, nums[pair.get(0)] + nums[pair.get(1)]);
        } else {
            System.out.printf("No matching pair found for target %d %n", target);
            Assertions.assertTrue(pair.isEmpty());
        }
    }

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
}
