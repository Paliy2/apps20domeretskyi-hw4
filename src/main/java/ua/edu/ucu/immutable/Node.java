package ua.edu.ucu.immutable;

public class Node {
    private Object value;
    private Node next;

    public Node(Object e) {
        this.value = e;
        this.next = null;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object newVal) {
        this.value = newVal;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node newNode) {
        this.next = newNode;
    }


}
