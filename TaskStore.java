package tpe;

import jdk.jshell.spi.ExecutionControl;
import tpe.utils.CustomLinkedList;
import tpe.utils.LinkedListBTree;

import java.util.Hashtable;
import java.util.List;

public class TaskStore {
    private CustomLinkedList<Tarea> linkedList; // Estructura primaria
    private LinkedListBTree<Tarea> priorityBTree;
    private Hashtable<String, Tarea> idHash;


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

    public Tarea getById(String id){
        Tarea tarea = idHash.get(id);
        return new Tarea(tarea);
    }

    public List<Tarea> getCriticals(boolean esCritica) {
        return null;
    }


    public List<Tarea> getPriorityRange(int from, int to) {
        return priorityBTree.getRange(Integer.valueOf(from), Integer.valueOf(to));
    }

    public CustomLinkedList<Tarea> getAll(){
        return linkedList; // TODO: devolver una copia
    }
}
