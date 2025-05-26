package taskTracker.managers;
import taskTracker.tasks.*;
import taskTracker.utilities.Node;
import java.util.ArrayList;
import java.util.HashMap;


public final class InMemoryHistoryManager implements HistoryManager{
    public Node<Task> head;
    public Node<Task> tail;

    private final HashMap<Integer, Node<Task>> nodesIDs;

    public InMemoryHistoryManager() {
        nodesIDs = new HashMap<>();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node<Task> nodeToRemove = nodesIDs.get(id);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            nodesIDs.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        if(nodesIDs.get(task.getId()) != null) {
            Node<Task> existingNode = nodesIDs.get(task.getId());
            nodesIDs.put(task.getId(), linkLast(task));
            removeNode(existingNode);
        } else {
            nodesIDs.put(task.getId(), linkLast(task));
        }
    }

    private Node<Task> linkLast(Task task) {
        Node<Task> temp = new Node<>(task);
        if (tail == null) {
            head = temp;
            tail = temp;
        } else {
            tail.setNextNode(temp);
            temp.setPreviousNode(tail);
            tail = temp;
        }
        return tail;
    }

    private void removeNode(Node<Task> node) {
        if (head == null) return;
        //if (node == tail) {return;}
        if (node == head) {
            head = node.getNextNode();
            node.getNextNode().setPreviousNode(null);
            node.setNextNode(null);
        } else if (node == tail) {
            tail = node.getPreviousNode();
            node.setPreviousNode(null);
        } else {
            node.getPreviousNode().setNextNode(node.getNextNode());
            node.getNextNode().setPreviousNode(node.getPreviousNode());
            node.setPreviousNode(null);
            node.setNextNode(null);
        }
    }

    private ArrayList<Task> getTasks(){
        if (head == null)  return null;

        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> current = tail;

        while (current != null) {
            tasks.add(current.getValue());
            current = current.getPreviousNode();
        }

        return tasks;
    }

}

