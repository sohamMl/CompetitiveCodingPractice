package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stack_Problems extends TestBase {

    /*
     * Problem: Valid Parenthesis Expression
     * * Given a string 's' containing the characters '(', ')', '{', '}', '[', and ']',
     * determine if the input string is valid.
     * * An input string is valid if:
     * 1. Open brackets must be closed by the same type of brackets.
     * 2. Open brackets must be closed in the correct order (LIFO - Last In, First Out).
     * 3. Every close bracket has a corresponding open bracket of the same type.
     * * Examples:
     * Input: s = "([]{})"  -> Output: true
     * Input: s = "([]{)}"  -> Output: false
     * * Constraints:
     * - 0 <= s.length <= 10^4 (or equivalent length based on context)
     * - s consists of parentheses only: '()[]{}'
     */

    @ParameterizedTest
    @CsvSource({
            // Examples from problem description
            "'([]{})', true",
            "'([]{)}', false",

            // Edge cases: Simple valid/invalid
            "'()', true",
            "'()[]{}', true",
            "'(]', false",
            "'([)]', false",

            // Edge cases: Nested and complex expressions
            "'{[()]}', true",
            "'{[(])}', false",
            "'((()))', true",

            // Edge cases: Unbalanced lengths (extra opening/closing)
            "'(', false",
            "')', false",
            "'(()', false",
            "'())', false",
            "']}[', false",

            // Edge case: Empty string
            "'', true"
    })
    void testValidParenthesisExpression(String input, boolean expected) {
        assertEquals(expected, validParenthesisExpression(input));
    }

    public boolean validParenthesisExpression(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(',')');
        map.put('{','}');
        map.put('[',']');

        Stack<Character> stack = new Stack<>();

        for(char c: s.toCharArray()) {
            if(map.containsKey(c)) stack.push(c);
            else {
                if(!stack.isEmpty()) {
                    if(map.get(stack.pop()) != c) return false;
                } else return false;
            }
        }

        return stack.isEmpty();
    }




    /**
     * Problem: Next Largest Number to the Right
     * * Given an integer array `nums`, return an output array `res` where, for each
     * value `nums[i]`, `res[i]` is the first number to the right that is strictly
     * larger than `nums[i]`. If no larger number exists to the right of `nums[i]`,
     * set `res[i]` to -1.
     * * Rules:
     * - Search rightwards from index i for the first element strictly greater than nums[i].
     * - If no greater element exists to the right, output -1 for that position.
     * - An empty input array should return an empty output array.
     * * Examples:
     * - Input:  [5, 2, 4, 6, 1]
     * Output: [6, 4, 6, -1, -1]
     * - Input:  [1, 2, 3, 4]
     * Output: [2, 3, 4, -1]
     * - Input:  [4, 3, 2, 1]
     * Output: [-1, -1, -1, -1]
     * * Constraints:
     * - 0 <= nums.length <= 10^5
     * - -10^9 <= nums[i] <= 10^9
     */
    @ParameterizedTest
    @CsvSource(value = {
            "'5, 2, 4, 6, 1' | '6, 4, 6, -1, -1'",
            "'1, 2, 3, 4'    | '2, 3, 4, -1'",
            "'4, 3, 2, 1'    | '-1, -1, -1, -1'",
            "'7'             | '-1'",
            "''              | ''",
            "'3, 3, 3'       | '-1, -1, -1'",
            "'-5, -2, -4'    | '-2, -1, -1'",
            "'1, -3, 2, -1'  | '2, 2, -1, -1'"
    }, delimiter = '|')
    void testNextLargestNumberToTheRight(String inputStr, String expectedStr) {
        int[] nums = (inputStr == null || inputStr.trim().isEmpty())
                ? new int[0]
                : java.util.Arrays.stream(inputStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] expected = (expectedStr == null || expectedStr.trim().isEmpty())
                ? new int[0]
                : java.util.Arrays.stream(expectedStr.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] actual = nextLargestNumberToTheRight(nums);

        Assertions.assertArrayEquals(expected, actual);
    }

    public int[] nextLargestNumberToTheRight(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] arr = new int[nums.length];
        Arrays.fill(arr, -1);

        for(int i=0; i<nums.length; i++) {
            while(!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                arr[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return arr;
    }


    /**
     * Problem Statement: Repeated Removal of Adjacent Duplicates
     * * Given a string, continually perform the operation of removing a pair of adjacent duplicate
     * characters until the string no longer contains any adjacent duplicates. Return the final string.
     * * Examples:
     * - Input: s = "aacabba" -> Output: "c"
     * - Input: s = "aaa"     -> Output: "a"
     * * Constraints:
     * - 0 <= s.length <= 10^5
     * - s consists of lowercase English letters.
     */

    @ParameterizedTest
    @CsvSource({
            "'aacabba', 'c'",
            "'aaa', 'a'",
            "'', ''",
            "'a', 'a'",
            "'aa', ''",
            "'abba', ''",
            "'abcde', 'abcde'",
            "'azxxzy', 'ay'",
            "'aaaaa', 'a'",
            "'aaaaaa', ''"
    })
    void testRepeatedRemovalOfAdjacentDuplicates(String input, String expected) {
        String actual = repeatedRemovalOfAdjacentDuplicates(input);
        assertEquals(expected, actual);
    }

    public String repeatedRemovalOfAdjacentDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c: s.toCharArray()) {
            if(!stack.isEmpty() && stack.peek() == c) stack.pop();
            else stack.push(c);
        }

        StringBuilder result = new StringBuilder();
        stack.forEach(result::append);
        return result.toString();
    }
}
