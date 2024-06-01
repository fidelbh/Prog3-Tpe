package tpe.utils;


import java.util.Comparator;

public abstract class Tree<E> {
    private TreeNode<E> root;
    private Comparator<E> comparator;

    public Tree(Comparator<E> comparator){
        this.comparator = comparator;
        this.root = null;
    }

    protected abstract TreeNode<E> createTreeNode(E value);

    public void add(E value){
        if (this.root == null)
            this.root = createTreeNode(value);
        else
            this.add(this.root, value);
    }

    private void add(TreeNode<E> current, E value){
        if (current.getKey() > value.getKey()) {
//        if (this.comparator.compare(current.getKey(), value.getKey()) > 0) {
            if (current.getLeft() == null)
                current.setLeft(createTreeNode(value));
            else
                add(current.getLeft(), value);
        } else if (current.getKey() < value.getKey()){
//        } else if (this.comparator.compare(current.getKey(), value.getKey()) < 0){
            if (current.getRight() == null)
                current.setRight(createTreeNode(value));
            else
                add(current.getRight(), value);
        }
        // TODO: else if (current.getValue() == value)
        // TODO: else if (this.comparator.compare(current.getValue(), value) == 0)
    }
}
