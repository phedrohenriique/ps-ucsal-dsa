package app.services;

public class LinkedList<T> {

    private NodeLinkedList<T> head;
    private NodeLinkedList<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // INSERT
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        NodeLinkedList<T> newNodeLinkedList = new NodeLinkedList<>(item);

        if (head == null) {
            head = newNodeLinkedList;
            tail = newNodeLinkedList;
        } else {
            tail.next = newNodeLinkedList;
            tail = newNodeLinkedList;
        }

        size++;
    }

    // SEARCH
    public T search(T item) {
        if (item == null) {
            return null;
        }

        NodeLinkedList<T> current = head;

        while (current != null) {
            if (current.data.equals(item)) {
                return current.data;
            }

            current = current.next;
        }

        return null;
    }

    // REMOVE
    public boolean remove(T item) {
        if (item == null || head == null) {
            return false;
        }

        if (head.data.equals(item)) {
            head = head.next;

            if (head == null) {
                tail = null;
            }

            size--;
            return true;
        }

        NodeLinkedList<T> current = head;

        while (current.next != null) {
            if (current.next.data.equals(item)) {

                if (current.next == tail) {
                    tail = current;
                }

                current.next = current.next.next;
                size--;

                return true;
            }

            current = current.next;
        }

        return false;
    }

    // GET ALL
    public Object[] getAll() {
        Object[] items = new Object[size];

        NodeLinkedList<T> current = head;
        int index = 0;

        while (current != null) {
            items[index++] = current.data;
            current = current.next;
        }

        return items;
    }

    // AUXILIARY METHODS
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public T[] toArray(T[] array) {
    if (array.length < size) {
        throw new IllegalArgumentException("Array is too small");
    }

    NodeLinkedList<T> current = head;
    int index = 0;

    while (current != null) {
        array[index++] = current.data;
        current = current.next;
    }

    return array;
}
}

// NodeLinkedList
class NodeLinkedList<T> {

    T data;
    NodeLinkedList<T> next;

    NodeLinkedList(T data) {
        this.data = data;
        this.next = null;
    }
}