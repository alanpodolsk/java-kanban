package manager;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager {

    HashMap<Integer, Node> nodes = new HashMap<>();

    private static final byte MAX_SIZE = 10;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }   // метод возвращает последние 10 просмотренных записей

    @Override
    public void add(Task task) {
        if (task != null){
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        Node curNode = nodes.get(id);
        if (curNode != null) {
            removeNode(curNode);
        }
        nodes.remove(id);
    }

    class Node<Task> {
        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<Task>(tail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
        if (nodes.containsKey(task.getId())) {
            removeNode(nodes.get(task.getId()));
        }
        nodes.put(task.getId(), newNode);
    }

    public void removeNode(Node thisNode) {
        Node next = thisNode.next;
        Node prev = thisNode.prev;

        if (prev != null) {
            prev.next = thisNode.next;
        } else {
            head = thisNode.next;
        }

        if (next != null) {
            next.prev = thisNode.prev;
        } else {
            tail = thisNode.prev;
        }

    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> curNode = tail;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (curNode == null) {
                break;
            }
            tasks.add(curNode.data);
            curNode = curNode.prev;
        }
        return tasks;
    }

    @Override
    public String getTasksId() {
        List<Integer> tasks = new ArrayList<>();
        Node<Task> curNode = tail;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (curNode == null) {
                break;
            }
            tasks.add(curNode.data.getId());
            curNode = curNode.prev;
        }
        return tasks.toString();
    }
}

