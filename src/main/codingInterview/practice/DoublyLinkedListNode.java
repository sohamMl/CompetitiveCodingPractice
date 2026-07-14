package main.codingInterview.practice;

public class DoublyLinkedListNode {
    private int val;
    private final int key;
    private DoublyLinkedListNode prev;
    private DoublyLinkedListNode next;

    public DoublyLinkedListNode(int key, int val) {
        this.key = key;
        this.val = val;
        this.prev = null;
        this.next = null;
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

    public int getKey() {
        return key;
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
