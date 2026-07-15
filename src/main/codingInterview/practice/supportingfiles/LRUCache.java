package main.codingInterview.practice.supportingfiles;


import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    //key, node
    Map<Integer, DoublyLinkedListNode> cache;
    int size;
    DoublyLinkedListNode head, tail;

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        head = new DoublyLinkedListNode(-1,-1);
        tail = new DoublyLinkedListNode(-1,-1);
        head.setNext(tail);
        tail.setPrev(head);
        size = capacity;
    }

    // 1. key exists
    public int get(int key) {
        DoublyLinkedListNode node = cache.get(key);
        if (node != null) {
            //remove node
            DoublyLinkedListNode prevNode = node.getPrev();
            DoublyLinkedListNode nextNode = node.getNext();
            prevNode.setNext(node.getNext());
            nextNode.setPrev(prevNode);


            //add node at head
            DoublyLinkedListNode nextToHead = head.getNext();
            node.setNext(nextToHead);
            node.setPrev(head);
            head.setNext(node);
            nextToHead.setPrev(node);

            return node.getVal();
        }

        return -1;
    }


    //1. key exists
    //2. key doesnt exists
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            this.get(key);
            this.head.getNext().setVal(value);
        } else {
            DoublyLinkedListNode newNode = new DoublyLinkedListNode(key, value);


            if (cache.size() == this.size) {
                DoublyLinkedListNode lastNode = tail.getPrev();
                lastNode.getPrev().setNext(tail);
                tail.setPrev(lastNode.getPrev());

                lastNode.setNext(null);
                lastNode.setPrev(null);
                this.cache.remove(lastNode.getKey());
            }


            newNode.setNext(head.getNext());
            head.getNext().setPrev(newNode);
            head.setNext(newNode);
            newNode.setPrev(head);


            this.cache.put(key, newNode);

        }
    }

}

