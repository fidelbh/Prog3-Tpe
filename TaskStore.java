package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.LinkedListBTree;

import java.util.Hashtable;

public class TaskStore {
    private CustomLinkedList<Tarea> linkedList; // Estructura primaria
    private LinkedListBTree<Tarea> priorityBTree;
    private Hashtable idHash;

    public TaskStore(){
        linkedList = new CustomLinkedList<>();
        priorityBTree = new LinkedListBTree<>();
        idHash = new Hashtable<>();
    }

    public void addTask(String id, String nombre, Integer tiempo, Integer prioridad, Boolean critica){
        Tarea tarea = new Tarea(id, nombre, tiempo, prioridad, critica);
        linkedList.push(tarea);
        priorityBTree.put(tarea.getPrioridad(), tarea);
        idHash.put(tarea.getId(), tarea);
    }

}
