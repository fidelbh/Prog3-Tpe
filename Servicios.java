package tpe;

import tpe.utils.CSVReader;
import tpe.utils.CustomLinkedList;

import java.util.List;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    private TaskStore taskStore;
    private CustomLinkedList<Procesador> procesadoresLinkedList;

    /*
     * Expresar la complejidad temporal del constructor.
     * O(4n) teniendo 2 iteradores secuenciales para el readProcessors
     * y otros 2 para el readTasks.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        procesadoresLinkedList = reader.readProcessors(pathProcesadores);
        taskStore = reader.readTasks(pathTareas);
    }

    /*
     * Expresar la complejidad temporal del servicio 1:
     * La complejidad temporal del servicio 1 es O(1), ya que al usar
     * una estructura de tipo HashTable, la busqueda a raiz del calculo del resto de una division
     * sobre el hashCode del ID proporcionado.
     */
    public Tarea servicio1(String ID) {
        return taskStore.getById(ID);
    }

    /*
     * Expresar la complejidad temporal del servicio 2:
     * O(1) porque devuelve una de dos listas precargadas con las tareas criticas o no criticas,
     * segun el valor del parametro esCritica.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        return taskStore.getCriticals(esCritica);
    }

    /*
     * Expresar la complejidad temporal del servicio 3:
     * O(n) ya que utilizamos la estructura de árbol binario, en este caso LinkedListBTree, para ordenar las tareas en relación a su prioridad.
     * Al ser un arbol no balanceado, en el peor de los casos es una LinkedList O(n) (arbol enredadera).
     *
     * Una posible optimizacion es utilizar un arbol autobalanceado, de esta forma la complejidad de filtrar por
     * prioridades se reduciria a O(log n).
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return taskStore.getPriorityRange(prioridadInferior, prioridadSuperior);
    }

    public Solucion backtracking(int tiempoMaxEjecucionNoRefrigerados) {
        BacktrackingTaskProcessor backtrackingTaskProcessor = new BacktrackingTaskProcessor(tiempoMaxEjecucionNoRefrigerados);
        return backtrackingTaskProcessor.backtracking(taskStore.getAll(), procesadoresLinkedList);
    }

    public Solucion greedy(int tiempoMaxEjecucionNoRefrigerados) {
        GreedyTaskProcessor greedyTaskProcessor = new GreedyTaskProcessor((tiempoMaxEjecucionNoRefrigerados));
        return greedyTaskProcessor.Greedy(taskStore.getAll(), procesadoresLinkedList);
    }
}
