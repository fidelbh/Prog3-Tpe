package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.TreeNode;

public class TareasTreeNode  {
    private CustomLinkedList<Tarea> linkedList;
    private Tarea value;
    private Integer key;
    private TareasTreeNode left;
    private TareasTreeNode right;

    public TareasTreeNode(Tarea tarea){
        setLeft(null);
        setRight(null);
        setKey(tarea.getPrioridad());
        setValue(tarea);
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public TareasTreeNode getLeft() {
        return left;
    }

    public void setLeft(TareasTreeNode left) {
        this.left = left;
    }

    public TareasTreeNode getRight() {
        return right;
    }

    public void setRight(TareasTreeNode right) {
        this.right = right;
    }

    public CustomLinkedList<Tarea> getValue(){
        // TODO: que devolvemos?
        return  new CustomLinkedList<Tarea>(linkedList);
    }

    public void setValue(Tarea tarea){
        this.linkedList = new CustomLinkedList<>();
        this.linkedList.push(tarea);
    }

    public void addValue(Tarea tarea){
        this.linkedList.push(tarea);
    }
}
