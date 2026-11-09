package app.services;

public class Queue<T> {

    private QueueNode<T> head;
    private QueueNode<T> tail;
    private int size;

    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add an element to the end of the queue
    public void addItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        QueueNode<T> newNode = new QueueNode<>(item);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    // Remove and return the first element
    public T removeItem() {
        if (isEmpty()) {
            return null;
        }

        T item = head.data;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        size--;
        return item;
    }

    // Return the first element without removing it
    public T getFirst() {
        return isEmpty() ? null : head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public LinkedList<T> getAll() {

    LinkedList<T> items = new LinkedList<>();

    QueueNode<T> current = head;

    while (current != null) {
        items.insert(current.data);
        current = current.next;
    }

    return items;
}
}

class QueueNode<T> {

    T data;
    QueueNode<T> next;

    QueueNode(T data) {
        this.data = data;
        this.next = null;
    }
}