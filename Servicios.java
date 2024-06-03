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
     */
	public Servicios(String pathProcesadores, String pathTareas)
	{
		CSVReader reader = new CSVReader();
		procesadoresLinkedList = reader.readProcessors(pathProcesadores);
		taskStore = reader.readTasks(pathTareas);
	}
	
	/*
     * Expresar la complejidad temporal del servicio 1.
     */
	public Tarea servicio1(String ID) {
		return taskStore.getById(ID);
	}
    
    /*
     * Expresar la complejidad temporal del servicio 2.
     */
	public List<Tarea> servicio2(boolean esCritica) {
		return taskStore.getCriticals(esCritica);
	}

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		return taskStore.getPriorityRange(prioridadInferior, prioridadSuperior);
	}

	public void backtracking(int tiempoMaxEjecucionNoRefrigerados){
		// El output en el TPE dice <Solucion> pero no se a q se refieren
		BacktrackingTaskProcessor backtrackingTaskProcessor = new BacktrackingTaskProcessor(tiempoMaxEjecucionNoRefrigerados);
		System.out.println(backtrackingTaskProcessor.backtracking(taskStore.getAll(), procesadoresLinkedList));
		// Ahi nos deberia imprimir un indexArray de la solucion
		// Puede ser q lo tomo todo el procesador 0 o q algo no anduvo xD
	}

}
