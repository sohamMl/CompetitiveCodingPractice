package main.codingInterview.practice;

/**
 * Represents a node in a multi-level singly linked list.
 * Each node contains an integer value, a pointer to the next node in the same level,
 * and a pointer to a child node which may start another sub-level linked list.
 */
public class MultiLevelListNode {
    public int val;
    public MultiLevelListNode next;
    public MultiLevelListNode child;

    /**
     * Constructs a new MultiLevelListNode with the specified value, next pointer, and child pointer.
     *
     * @param val   the value of the node
     * @param next  the next node in the current level
     * @param child the head node of the child sub-level list
     */
    public MultiLevelListNode(int val, MultiLevelListNode next, MultiLevelListNode child) {
        this.val = val;
        this.next = next;
        this.child = child;
    }
}
