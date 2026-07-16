package main.codingInterview.practice;

import main.TestBase;
import main.codingInterview.practice.supportingfiles.ListNode;
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


}
