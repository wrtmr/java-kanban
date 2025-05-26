package taskTracker.utilities;

public class Node<T> {

    T value;
    Node<T> next;
    Node<T> prev;

    public Node(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public Node<T> getNextNode() { return next; }

    public void setNextNode(Node<T> nextNode) { this.next = nextNode; }

    public Node<T> getPreviousNode() { return prev; }

    public void setPreviousNode(Node<T> previousNode) { this.prev = previousNode;}

    public T getValue() { return value; }

    public void setValue(T value) { this.value = value; }
}
