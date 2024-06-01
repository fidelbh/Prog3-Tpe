package tpe.utils;


import java.util.Comparator;

public class LinkedListBTree<E> {
    private LinkedListTreeNode<E> root;

    public LinkedListBTree(){
        this.root = null;
    }

    protected LinkedListTreeNode<E> createTreeNode(Integer key, E value){
        return  new LinkedListTreeNode<E>(key, value);
    }

    public void put(Integer key, E value){
        if (this.root == null)
            this.root = createTreeNode(key, value);
        else
            this.put(this.root, key, value);
    }

    private void put(LinkedListTreeNode<E> current, Integer key, E value){
        if (current.getKey() > key) {
            if (current.getLeft() == null)
                current.setLeft(createTreeNode(key, value));
            else
                this.put(current.getLeft(), key, value);
        } else if (current.getKey() < key){
            if (current.getRight() == null)
                current.setRight(createTreeNode(key, value));
            else
                this.put(current.getRight(), key, value);
        } else if (current.getKey() == key){
            current.push(value);
        }
    }
}
