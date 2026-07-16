package main.codingInterview.practice;

import main.TestBase;
import main.codingInterview.practice.supportingfiles.ListNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Contains coding problems and associated unit tests that are solved using
 * the Fast and Slow Pointer (Tortoise and Hare) pattern.
 * This pattern is typically used for traversing linked lists to detect cycles,
 * find midpoints, or find specific elements in a single pass.
 */
public class Fast_Slow_Pointer_Problems extends TestBase {
    
    /**
     * Parameterized test to verify that the linkedListMidpoint method correctly finds the middle
     * node of a singly linked list. It constructs the linked list from a comma-separated string
     * of integer values, runs the midpoint method, and asserts the value of the returned node.
     *
     * @param listData    comma-separated string representation of list node values
     * @param expectedVal the expected integer value of the middle node
     */
    @ParameterizedTest
    @CsvSource({
            "'1,2,3,4,5', 3",       // Example 1: Odd length (5 nodes) -> middle is 3
            "'1,2,3,4,5,6', 4",     // Example 2: Even length (6 nodes) -> second middle is 4
            "'1', 1",               // Edge Case: Single node
            "'1,2', 2",             // Edge Case: Two nodes -> second middle is 2
            "'10,20,30,40', 30"     // Edge Case: Short even length -> second middle is 30
    })
    void testLinkedListMidpoint(String listData, int expectedVal) {
        // Construct the linked list from the comma-separated string
        String[] tokens = listData.split(",");
        ListNode head = new ListNode(Integer.parseInt(tokens[0]));
        ListNode current = head;
        for (int i = 1; i < tokens.length; i++) {
            current.next = new ListNode(Integer.parseInt(tokens[i]));
            current = current.next;
        }

        // Find the midpoint using the fast and slow pointer algorithm
        ListNode result = linkedListMidpoint(head);

        // Verify the midpoint node is not null and matches the expected value
        assertNotNull(result);
        assertEquals(expectedVal, result.val);
    }

    /**
     * Finds the midpoint of a singly linked list.
     * If the list has an even number of nodes, the second middle node is returned.
     *
     * Algorithmic Details:
     * - Time Complexity: O(N) where N is the number of nodes in the list.
     * - Space Complexity: O(1) auxiliary space.
     *
     * How it works:
     * - We initialize two pointers: slowPtr (moves 1 step at a time) and fastPtr (moves 2 steps at a time).
     * - By the time fastPtr reaches the end of the list, slowPtr will be at the midpoint.
     *
     * @param head the head of the singly linked list
     * @return the middle node of the linked list, or the second middle node if the length is even
     */
    public ListNode linkedListMidpoint(ListNode head) {
        ListNode slowPtr = head;
        ListNode fastPtr = head;

        // Traverse the list until fastPtr reaches the end (null) or the last node (fastPtr.next is null)
        while (fastPtr != null && fastPtr.next != null) {
            slowPtr = slowPtr.next;         // Slow pointer moves one step
            fastPtr = fastPtr.next.next;    // Fast pointer moves two steps
        }

        return slowPtr;
    }



    /**
     * Parameterized test to verify that the linkedListLoop method correctly detects
     * whether a linked list contains a cycle (loop). It retrieves test lists
     * (some with cycles, some without) from ProblemData.testLinkedListLoopData.
     *
     * @param head     the head of the linked list to be tested
     * @param expected true if the list is expected to have a cycle, false otherwise
     */
    @ParameterizedTest
    @MethodSource("main.codingInterview.practice.supportingfiles.ProblemData#testLinkedListLoopData")
    void testLinkedListLoop(ListNode head, boolean expected) {
        assertEquals(expected, linkedListLoop(head));
    }

    /**
     * Detects if a singly linked list contains a cycle (loop) using the Fast and Slow Pointer
     * (Floyd's Cycle-Finding / Tortoise and Hare) algorithm.
     *
     * Algorithmic Details:
     * - Time Complexity: O(N) where N is the number of nodes in the list.
     * - Space Complexity: O(1) auxiliary space.
     *
     * How it works:
     * - We initialize two pointers, slow and fast, at the head of the list.
     * - The slow pointer moves one step at a time, while the fast pointer moves two steps.
     * - If there is a cycle, the fast pointer will eventually wrap around and meet the slow pointer (slow == fast).
     * - If the fast pointer reaches the end of the list (null), then no cycle exists.
     *
     * @param head the head of the singly linked list
     * @return true if the linked list contains a cycle, false otherwise
     */
    public boolean linkedListLoop(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // Traverse the list using fast and slow pointers
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // If the pointers meet, a cycle is detected
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }


    /**
     * Parameterized test to verify that the isHappy method correctly identifies happy numbers.
     * It tests a variety of numbers including known happy numbers, unhappy numbers,
     * single digits, boundary cases, and larger values.
     *
     * @param n        the number to be checked
     * @param expected true if the number is happy, false otherwise
     */
    @ParameterizedTest
    @CsvSource({
            "23, true",    // Sample Example
            "1, true",     // Base happy number
            "2, false",    // Single digit unhappy number
            "4, false",    // Known unhappy loop starting point
            "7, true",     // Single digit happy number
            "19, true",    // Two digit happy number
            "243, false",  // Threshold boundary case
            "999, false"   // Upper boundary of 3 digits
    })
    public void testIsHappy(int n, boolean expected) {
        assertEquals(expected, isHappy(n));
    }

    /**
     * Determines whether a number is a "happy" number.
     * A happy number is defined by the following process:
     * - Starting with any positive integer, replace the number by the sum of the squares of its digits.
     * - Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle
     *   which does not include 1.
     * - Those numbers for which this process ends in 1 are happy.
     *
     * Algorithmic Details:
     * - Time Complexity: O(log N) as the number of digits decreases/remains bounded and converges quickly.
     * - Space Complexity: O(1) auxiliary space.
     *
     * How it works:
     * - We treat this as a cycle detection problem (Floyd's Cycle-Finding Algorithm).
     * - The slow pointer calculates the next number once per step.
     * - The fast pointer calculates the next number twice per step.
     * - If the fast pointer reaches 1, the number is happy.
     * - If the fast pointer meets the slow pointer (cycle), and it is not 1, the number is not happy.
     *
     * @param n the positive integer to check
     * @return true if n is a happy number, false otherwise
     */
    public boolean isHappy(int n) {
        if (n <= 0) {
            return false;
        }

        int slow = n;
        int fast = n;

        do {
            slow = getNextNumber(slow);
            fast = getNextNumber(getNextNumber(fast));

            if (fast == 1) {
                return true;
            }
        } while (fast != slow);

        return false;
    }

    /**
     * Helper method that calculates the sum of the squares of the digits of a given number.
     *
     * @param x the number whose digit squares are to be summed
     * @return the sum of the squares of the digits of x
     */
    public int getNextNumber(int x) {
        int sum = 0;
        while (x != 0) {
            int num = x % 10;
            sum += num * num;
            x = x / 10;
        }
        return sum;
    }

}
