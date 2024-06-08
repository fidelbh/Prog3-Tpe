package tpe.utils;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedListBTree<E> {
    // Left < Node < Right
    private LinkedListTreeNode<E> root;

    public LinkedListBTree() {
        this.root = null;
    }

    protected LinkedListTreeNode<E> createTreeNode(Integer key, E value) {
        return new LinkedListTreeNode<E>(key, value);
    }

    public void put(Integer key, E value) {
        if (this.root == null)
            this.root = createTreeNode(key, value);
        else
            this.put(this.root, key, value);
    }

    private void put(LinkedListTreeNode<E> current, Integer key, E value) {
        if (current.getKey() > key) {
            if (current.getLeft() == null)
                current.setLeft(createTreeNode(key, value));
            else
                this.put(current.getLeft(), key, value);
        } else if (current.getKey() < key) {
            if (current.getRight() == null)
                current.setRight(createTreeNode(key, value));
            else
                this.put(current.getRight(), key, value);
        } else if (current.getKey() == key) {
            current.push(value);
        }
    }

    public List<E> getRange(Integer from, Integer to) {
        List<E> result = new ArrayList();
        return getRange(this.root, from, to, result);
    }

    protected List<E> getRange(LinkedListTreeNode<E> current, Integer from, Integer to, List<E> result) {
        if (current == null)
            return result;
        getRange(current.getLeft(), from, to, result);
        Integer currentKey = current.getKey();
        if (currentKey < to) {
            if (currentKey >= from)
                result.addAll(current.getAll());
            getRange(current.getRight(), from, to, result);
        }
        return result;
    }

}
