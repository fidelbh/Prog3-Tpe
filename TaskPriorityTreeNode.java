package tpe;

import tpe.utils.CustomLinkedList;

public class TaskPriorityTreeNode {
    private CustomLinkedList<Tarea> linkedList;
    private Tarea value;
    private Integer key;
    private TaskPriorityTreeNode left;
    private TaskPriorityTreeNode right;

    public TaskPriorityTreeNode(Tarea tarea){
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

    public TaskPriorityTreeNode getLeft() {
        return left;
    }

    public void setLeft(TaskPriorityTreeNode left) {
        this.left = left;
    }

    public TaskPriorityTreeNode getRight() {
        return right;
    }

    public void setRight(TaskPriorityTreeNode right) {
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
        if(this.getKey() == tarea.getPrioridad())
            this.linkedList.push(tarea);
        else
            throw new IllegalArgumentException(String.format("Task key '%s' is no match for the list '%s'.",
                    tarea.getPrioridad(), this.getKey()));
    }
}
