package util;

import elements.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager<T> implements HistoryManager{

    HashMap <Integer, Node> nodeMap = new HashMap<>();
    List<Task> history = new LinkedList<>();
    List<Task> viewHistory = new ArrayList<>();

    public static final byte MAX_SIZE = 10;

    @Override
    public List<Task> getHistory(){
        return getTasks();
    }   // метод возвращает последние 10 просмотренных записей

    @Override
    public void add (Task task){
        linkLast(task);
    }

    @Override
    public void remove (int id) {
        Node curNode = nodeMap.get(id);
        removeNode(curNode);
        nodeMap.remove(id);
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
        final Node <Task> oldTail = tail;
        final Node <Task> newNode = new Node <Task> (tail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
        if (nodeMap.containsKey(task.getTaskID())) {
            removeNode(nodeMap.get(task.getTaskID()));
        }
            nodeMap.put(task.getTaskID(), newNode);
    }

    public void removeNode(Node thisNode){
        Node next = thisNode.next;
        Node prev = thisNode.prev;

        if (prev != null){prev.next = thisNode.next;}
        if (next != null){next.prev = thisNode.prev;}

    }

    public List<Task> getTasks(){
        List<Task> tasksList = new ArrayList<>();
        Node <Task> curNode = tail;
        for (int i = 0; i< MAX_SIZE; i++){
            if (curNode == null){break;}
            tasksList.add(curNode.data);
                curNode = curNode.prev;
            }
        return tasksList;
        }

    }
