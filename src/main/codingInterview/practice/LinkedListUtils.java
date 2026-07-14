package main.codingInterview.practice;

import java.util.Arrays;

public class LinkedListUtils {

    // Prints the linked list to System.out
    public static void printLinkedList(ListNode head) {
        System.out.println(linkedListToString(head));
    }

    // Converts the linked list to a comma-separated string representation
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

    // Creates a linked list from a comma-separated string
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

    // Creates a linked list from an array of integers
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
