package ua.edu.ucu.immutable;

public class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int size;

    public ImmutableLinkedList() {
        this.head = null;
        this.size = 0;
    }

    ImmutableLinkedList(Node n) {
        head = n;
    }

    ImmutableLinkedList(Object[] c) {
        if (c.length > 0) {
            this.head = new Node(c[0]);
            Node current = this.head;
            for (int i = 1; i < c.length; i++) {
                current.setNext(new Node(c[i]));
                current = current.getNext();
            }
        }
        this.size = c.length;
    }


    private ImmutableLinkedList copy() {

        if (this.head == null) {
            return new ImmutableLinkedList();
        }

        ImmutableLinkedList resList = new ImmutableLinkedList();
        resList.size = size;
        resList.head = new Node(this.head.getValue());
        Node nodeOriginal = this.head.getNext();
        Node nodeNew = resList.head;

        while (nodeOriginal != null) {
            nodeNew.setNext(new Node(nodeOriginal.getValue()));
            nodeNew = nodeNew.getNext();
            nodeOriginal = nodeOriginal.getNext();
        }

        return resList;
    }

    public ImmutableLinkedList add(Object e) {
        return add(this.size, e);
    }

    public ImmutableLinkedList add(int index, Object e) {
        checkIndexBounds(index);

        int counter = 0;
        ImmutableLinkedList resList = copy();
        Node nodeTemp = resList.head;
        Node nodeNew = new Node(e);

        // if the original list is empty
        if (nodeTemp == null) {
            resList.head = new Node(e);
        }
        // special case
        else if (index == 0) {
            nodeNew.setNext(nodeTemp);
            ImmutableLinkedList lst = new ImmutableLinkedList(nodeNew);
            lst.size = this.size + 1;
            return lst;
        }
        // normal case
        else {
            while (nodeTemp != null) {
                // till last index
                if (counter == index - 1) {
                    Node current = nodeTemp.getNext();
                    nodeTemp.setNext(nodeNew);
                    nodeTemp.getNext().setNext(current);
                }

                nodeTemp = nodeTemp.getNext();
                counter++;
            }
        }

        resList.size++;
        return resList;
    }

    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(this.size, c);
    }

    public ImmutableLinkedList addAll(int index, Object[] c) {
        ImmutableLinkedList resList = copy();

        for (int i = 0; i < c.length; i++) {
            resList = resList.add(index + i, c[i]);
        }

        return resList;
    }

    public Object get(int index) {
        checkIndexBounds(index);

        ImmutableLinkedList resList = copy();

        Node current = resList.head;
        int counter = index;
        while (counter-- != 0) {
            current = current.getNext();
        }
        return current.getValue();
    }


    public ImmutableLinkedList remove(int index) {
        checkIndexBounds(index);

        ImmutableLinkedList resList = copy();
        Node current = resList.head;
        int counter = 0;

        if (index == 0) {
            ImmutableLinkedList lst =
                    new ImmutableLinkedList(current.getNext());
            lst.size = this.size - 1;
            return lst;
        }

        while (counter != index - 1) {
            current = current.getNext();
            counter++;
        }
        // remove
        Node prev = current;
        prev.setNext(current.getNext().getNext());
        resList.size--;
        return resList;
    }

    public ImmutableLinkedList set(int index, Object e) {
        checkIndexBounds(index);

        ImmutableLinkedList resList = copy();
        Node current = resList.head;

        int counter = index;
        while (counter-- != 0) {
            current = current.getNext();
        }

        current.setValue(e);
        return resList;
    }

    public int indexOf(Object e) {
        ImmutableLinkedList resList = copy();
        Node current = resList.head;

        int counter = 0;
        while (current != null) {
            if (current.getValue().equals(e)) {
                return counter;
            }
            current = current.getNext();
            counter++;
        }
        return -1;
    }

    public int size() {
        return this.size;
    }

    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object[] toArray() {
        Object[] array = new Object[this.size];
        ImmutableLinkedList resList = copy();
        Node current = resList.head;

        int i = 0;
        while (current != null) {
            array[i] = current.getValue();
            current = current.getNext();
            i++;
        }

        return array;
    }

    @Override
    public String toString() {
        ImmutableLinkedList resList = copy();
        Node current = resList.head;

        if (current == null) {
            return "[]";
        }

        StringBuilder resText = new StringBuilder();
        resText.append("[");
        while (current != null) {
            resText.append(current.getValue().toString()).append(", ");
            current = current.getNext();
        }

        return resText.substring(0, resText.length() - 2) + "]";
    }

    private void checkIndexBounds(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Node getHead() {
        return this.head;
    }

    public void setHead(Node newNode) {
        this.head = newNode;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(this.size() - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(this.size() - 1);
    }
}
