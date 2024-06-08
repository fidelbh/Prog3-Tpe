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
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		procesadoresLinkedList = reader.readProcessors(pathProcesadores);
		taskStore = reader.readTasks(pathTareas);
	}
	
	/*
     * Expresar la complejidad temporal del servicio 1:
     * La complejidad temporal del servicio 1 es O(1), ya que al usar
     * una estructura de tipo HashTable, la busqueda se hace en base al calculo del resto de una division
     * sobre el hashCode del ID proporcionado.
     */
	public Tarea servicio1(String ID) {
		return taskStore.getById(ID);
	}

    /*
     * Expresar la complejidad temporal del servicio 2:
     * O(n) porque itera sobre la lista original, agregando la tarea o no al resultado en funcion
     * de si el usuario solicita las tareas criticas o las no criticas.
     */
	public List<Tarea> servicio2(boolean esCritica) {
		return taskStore.getCriticals(esCritica);
	}

    /*
     * Expresar la complejidad temporal del servicio 3: O(n)
     * Porque al ser un arbol no balanceado, en el peor de los casos es una LinkedList O(n).
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		return taskStore.getPriorityRange(prioridadInferior, prioridadSuperior);
	}

	public Solucion backtracking(int tiempoMaxEjecucionNoRefrigerados){
		BacktrackingTaskProcessor backtrackingTaskProcessor = new BacktrackingTaskProcessor(tiempoMaxEjecucionNoRefrigerados);
		return backtrackingTaskProcessor.backtracking(taskStore.getAll(), procesadoresLinkedList);
	}

	public Solucion greedy(int tiempoMaxEjecucionNoRefrigerados){
		GreedyTaskProcessor greedyTaskProcessor = new GreedyTaskProcessor((tiempoMaxEjecucionNoRefrigerados));
		return greedyTaskProcessor.Greedy(taskStore.getAll(), procesadoresLinkedList);
	}
}
