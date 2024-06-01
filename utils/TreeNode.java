package tpe.utils;


public abstract class TreeNode<E> {
    // KISS

    private E value;
    private Integer key;
    private TreeNode<E> left;
    private TreeNode<E> right;

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> right) {
        this.right = right;
    }

    public void setValue(E value){
        this.value = value;
    }

    public void setKey(Integer key){
        this.key = key;
    }
    public Integer getKey(){
        return  key;
    }
}
