package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

        assertArrayEquals(expected, actual);
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



    /**
     * Problem: Implement a Queue using Stacks
     * * Implement a first-in-first-out (FIFO) queue using only two stacks.
     * The implemented queue should support the following operations:
     * - enqueue(x): Adds element x to the back of the queue.
     * - dequeue(): Removes and returns the element from the front of the queue.
     * - peek(): Returns the element at the front of the queue without removing it.
     * * Operations Sequence Example:
     * Input: enqueue(1), enqueue(2), dequeue(), enqueue(3), peek()
     * Output: dequeue() -> 1, peek() -> 2
     * * Constraints:
     * - dequeue and peek operations will only be called on a non-empty queue.
     * - Elements are integers (supports negative numbers, zero, and positive values).
     */

    @ParameterizedTest
    @CsvSource({
            "'enqueue:1,enqueue:2,dequeue,enqueue:3,peek', '1,2'",
            "'enqueue:10,peek,dequeue', '10,10'",
            "'enqueue:1,enqueue:2,enqueue:3,dequeue,dequeue,dequeue', '1,2,3'",
            "'enqueue:-5,enqueue:0,enqueue:5,peek,dequeue,peek', '-5,-5,0'",
            "'enqueue:42,dequeue', '42'",
            "'enqueue:1,enqueue:2,dequeue,enqueue:3,dequeue,dequeue', '1,2,3'",
            "'enqueue:100,enqueue:-100,peek,dequeue,peek,dequeue', '100,100,-100,-100'"
    })
    void testQueueUsingStacks(String operationsStr, String expectedStr) {
        MyQueue queue = new MyQueue();
        String[] operations = operationsStr.split(",");
        String[] expectedValues = expectedStr.split(",");
        int expectedIndex = 0;

        for (String op : operations) {
            String[] tokens = op.trim().split(":");
            String command = tokens[0];

            if ("enqueue".equals(command)) {
                int val = Integer.parseInt(tokens[1]);
                queue.enqueue(val);
            } else if ("dequeue".equals(command)) {
                int expected = Integer.parseInt(expectedValues[expectedIndex++].trim());
                Assertions.assertEquals(expected, queue.dequeue());
            } else if ("peek".equals(command)) {
                int expected = Integer.parseInt(expectedValues[expectedIndex++].trim());
                Assertions.assertEquals(expected, queue.peek());
            }
        }
    }

    class MyQueue {
        private final Stack<Integer> inStack = new Stack<>();
        private final Stack<Integer> outStack = new Stack<>();


        public void enqueue(int x) {
            inStack.push(x);
        }

        private void populateOutStackIfEmpty() {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
        }

        public int dequeue() {
            populateOutStackIfEmpty();
            return outStack.pop();
        }

        public int peek() {
            populateOutStackIfEmpty();
            return outStack.peek();
        }
    }




    /**
     * Problem: Maximums of Sliding Window
     *
     * Description:
     * Given an array of integers 'nums' and a sliding window size 'k', create an array
     * recording the maximum value in each window as it slides from left to right.
     *
     * Example:
     * Input: nums = [3, 2, 4, 1, 2, 1, 1], k = 4
     * Output: [4, 4, 4, 2]
     *
     * Constraints:
     * - 1 <= nums.length <= 10^5
     * - -10^4 <= nums[i] <= 10^4
     * - 1 <= k <= nums.length
     */

    @ParameterizedTest
    @CsvSource(value = {
            "'3,2,4,1,2,1,1' | 4 | '4,4,4,2'",
            "'1,3,-1,-3,5,3,6,7' | 3 | '3,3,5,5,6,7'",
            "'1' | 1 | '1'",
            "'1,-1' | 1 | '1,-1'",
            "'9,11' | 2 | '11'",
            "'4,4,4,4' | 2 | '4,4,4'",
            "'-7,-8,-7,5,7,1,6,0' | 4 | '5,7,7,7,7'",
            "'-1,-2,-3,-4' | 2 | '-1,-2,-3'"
    }, delimiter = '|')
    void testMaximumsOfSlidingWindow(String numsInput, int k, String expectedInput) {
        int[] nums = Arrays.stream(numsInput.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] expected = Arrays.stream(expectedInput.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] actual = maxSlidingWindow(nums, k);

        assertArrayEquals(expected, actual);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<int []> dq = new ArrayDeque<>();
        if (nums.length < k) return new int[0];
        int[] result = new int[nums.length - k + 1];
        int left=0, right=0;

        while (right < nums.length) {
            //grow the window till window length is reached
            while(!dq.isEmpty() && dq.peekLast()[0]<= nums[right]) {
                dq.removeLast();
            }

            //add the new element
            dq.addLast(new int[]{nums[right], right});

            //slide the window
            if (right-left+1 == k) {
                //remove the outdated element - the first element will be the left most
                //we need to see if it is currently under the window
                if(!dq.isEmpty() && dq.peekFirst()[1] < left) {
                    dq.removeFirst();
                }

                //add the first element to the result
                result[left] = dq.peekFirst()[0];
                left++;
            }
            right++;
        }
        return result;
    }
}
