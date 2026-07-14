package main.codingInterview.practice;

import java.util.Arrays;

/**
 * Utility class providing helper methods for working with singly linked lists.
 * This includes functions to construct linked lists from arrays/strings, 
 * format lists to string representations, and print them.
 */
public class LinkedListUtils {

    /**
     * Prints the linked list to System.out.
     * Uses the comma-separated string format from linkedListToString.
     *
     * @param head the head node of the linked list
     */
    public static void printLinkedList(ListNode head) {
        System.out.println(linkedListToString(head));
    }

    /**
     * Converts a linked list into a comma-separated string representation.
     * Example: [1 -> 2 -> 3] becomes "1,2,3".
     * Returns an empty string if the list is null.
     *
     * @param head the head node of the linked list
     * @return a comma-separated string of the list values
     */
    public static String linkedListToString(ListNode head) {
        if (head == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(",");
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    /**
     * Creates a linked list from a comma-separated string of integers.
     * Example: "1,2,3" constructs a list 1 -> 2 -> 3 -> null.
     * Returns null if the input is null, empty, or blank.
     *
     * @param input the comma-separated string
     * @return the head node of the constructed linked list
     */
    public static ListNode createLinkedList(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        int[] values = Arrays.stream(input.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        return createLinkedList(values);
    }

    /**
     * Creates a linked list from an array of integers.
     * Returns null if the array is null or empty.
     *
     * @param values the array containing node values in order
     * @return the head node of the constructed linked list
     */
    public static ListNode createLinkedList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }
}
