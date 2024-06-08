package tpe.utils;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class LinkedListTreeNode<E> {
    private CustomLinkedList<E> linkedList;
    private Integer key;
    private LinkedListTreeNode<E> left, right;

    public LinkedListTreeNode(Integer key) {
        this.key = key;
        linkedList = new CustomLinkedList<>();
        left = null;
        right = null;
    }

    public LinkedListTreeNode(Integer key, E data) {
        setKey(key);
        linkedList = new CustomLinkedList<>();
        left = null;
        right = null;
        this.push(data);
    }

    public LinkedListTreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(LinkedListTreeNode<E> left) {
        this.left = left;
    }

    public LinkedListTreeNode<E> getRight() {
        return right;
    }

    public void setRight(LinkedListTreeNode<E> right) {
        this.right = right;
    }

    public void push(E data) {
        linkedList.push(data);
    }

    public void setKey(Integer key) {
        this.key = Objects.requireNonNull(key);
    }

    public Integer getKey() {
        return key;
    }

    public List<E> getAll() {
        return linkedList.getAll();
    }
}
