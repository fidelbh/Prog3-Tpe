package tpe;

import jdk.jshell.spi.ExecutionControl;
import tpe.utils.CustomLinkedList;
import tpe.utils.LinkedListBTree;
import tpe.utils.Node;

import java.util.*;

public class TaskStore {
    private CustomLinkedList<Tarea> linkedList; // Estructura primaria
    private List<Tarea> criticalTasks, nonCriticalTasks;
    private LinkedListBTree<Tarea> priorityBTree;
    private Hashtable<String, Tarea> idHash;


    public TaskStore() {
        linkedList = new CustomLinkedList<>();
        criticalTasks = new ArrayList<>();
        nonCriticalTasks = new ArrayList<>();
        priorityBTree = new LinkedListBTree<>();
        idHash = new Hashtable<>();
    }

    public void addTask(String id, String nombre, Integer tiempo, Integer prioridad, Boolean critica) {
        Tarea tarea = new Tarea(id, nombre, tiempo, prioridad, critica);
        linkedList.push(tarea);
        priorityBTree.put(tarea.getPrioridad(), tarea);
        idHash.put(tarea.getId(), tarea);
        if (tarea.isCritica())
            criticalTasks.add(tarea);
        else
            nonCriticalTasks.add(tarea);
    }

    public Tarea getById(String id) {
        Tarea tarea = idHash.get(id);
        return Objects.nonNull(tarea) ? new Tarea(tarea) : null;
    }

    public List<Tarea> getCriticals(boolean esCritica) {
        return esCritica ? criticalTasks : nonCriticalTasks;
    }


    public List<Tarea> getPriorityRange(int from, int to) {
        return priorityBTree.getRange(Integer.valueOf(from), Integer.valueOf(to));
    }

    public CustomLinkedList<Tarea> getAll() {
        return linkedList; // TODO: devolver una copia
    }
}
