package main.codingInterview.practice.supportingfiles;

/**
 * Represents a node in a singly linked list.
 * Each node contains an integer value and a reference to the next node in the list.
 */
public class ListNode {
    /** The integer value stored in the node. */
    public int val;
    
    /** Reference to the next node in the list. */
    public ListNode next;

    /**
     * Default constructor creating an empty node.
     */
    public ListNode() {}

    /**
     * Constructor creating a node with a specific value and next reference set to null.
     *
     * @param val the value to be stored in the node
     */
    public ListNode(int val) {
        this.val = val;
    }

    /**
     * Constructor creating a node with a specific value and a reference to the next node.
     *
     * @param val the value to be stored in the node
     * @param next reference to the next node in the list
     */
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
