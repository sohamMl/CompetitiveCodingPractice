package main.codingInterview.practice.supportingfiles;

/**
 * Represents a node in a doubly linked list used for key-value pair storage (e.g., in an LRU Cache).
 * Contains references to previous and next nodes, as well as the key and value.
 */
public class DoublyLinkedListNode {
    private final int key;
    private int val;
    private DoublyLinkedListNode prev;
    private DoublyLinkedListNode next;


    /**
     * Constructor creating a doubly linked list node with a key and a value.
     *
     * @param key the key to store
     * @param val the value to store
     */
    public DoublyLinkedListNode(int key, int val) {
        this.key = key;
        this.val = val;
        this.prev = null;
        this.next = null;
    }

    public int getKey() {
        return key;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public DoublyLinkedListNode getPrev() {
        return prev;
    }

    public void setPrev(DoublyLinkedListNode prev) {
        this.prev = prev;
    }

    public DoublyLinkedListNode getNext() {
        return next;
    }

    public void setNext(DoublyLinkedListNode next) {
        this.next = next;
    }
}
