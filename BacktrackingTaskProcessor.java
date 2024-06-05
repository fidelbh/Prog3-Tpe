package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingTaskProcessor {
    private int tiempoMaxEjecucionNoRefrigerados, maxAdmittedCriticalTasks, eventsCounter;
    private int bestExecutionTime;
    private ArrayList<Integer> bestSolution;
    private static final int POS_CRITICALS = 0;
    private static final int POS_EXECUTION_TIME = 1;

    public BacktrackingTaskProcessor(int tiempoMaxEjecucionNoRefrigerados){
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    /*
    Explicacion de la estrategia:
    Para la resolución de la segunda parte del TPE decidimos utilizar una estrategia mediante backtracking con el fin de encontrar
    la solucion que presente el menor tiempo maximo de ejecucion. Utilizamos findMaxExecutionTime para encontrar el mayor tiempo de ejecucion de todos los procesadores; el metodo
    isBetterSolution se encarga de verificar si la solucion encontrada es la mejor hasta el momento.
    */

    /**
     * Este método aplica la técnica de backtracking para asignar tareas a procesadores de manera óptima.
     * Evalúa todas las posibles combinaciones de asignación y selecciona la mejor solución basada en el
     * tiempo de ejecución máximo, contabilizando el número de eventos.
     *
     * @param tasks Lista enlazada personalizada que contiene las tareas a ser asignadas.
     * @param processors Lista enlazada personalizada que contiene los procesadores disponibles.
     * @return Solucion representando la mejor asignación de tareas a procesadores,
     *         o null si no se encuentra una solución viable.
     */
    public Solucion backtracking(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors) {
        ArrayList<Integer> solucion = new ArrayList<>();
        this.eventsCounter = 0;
        this.bestSolution = new ArrayList<>();
        this.bestExecutionTime = Integer.MAX_VALUE;

        int[][] processorsTracker = new int[processors.getLength()][2];
            // [i][j]
            // pos-j posCrticals trackea # criticos, pos-j posExecutionTime trackea tiempo de ejecucion
            // pos-i trackea el procesador
        backtracking(tasks, processors, solucion, tasks.getFirst(), processors.getFirst(), processorsTracker);

        if (bestSolution.isEmpty()){
            System.out.println("No solution could be found");
            return null;
        }

        String solutionString = processSolutionOutput(bestSolution, tasks, processors);
        Solucion res = new Solucion(solutionString, bestExecutionTime, this.eventsCounter, bestSolution);

        System.out.println("---BACKTRACKING---");
        System.out.println(res);

        return res;
    }

    /**
     * Método privado recursivo que implementa el algoritmo de backtracking.
     * Intenta asignar cada tarea a cada procesador y evalúa la viabilidad de la asignación.
     *
     * @param tasks Lista enlazada personalizada que contiene las tareas a ser asignadas.
     * @param processors Lista enlazada personalizada que contiene los procesadores disponibles.
     * @param solution Lista que mantiene la asignación actual de tareas a procesadores.
     * @param currentTask Nodo actual de la tarea en proceso de asignación.
     * @param firstProcessor Primer nodo del procesador en la lista enlazada.
     * @param processorsTracker Matriz que rastrea la cantidad de tareas críticas y el tiempo de ejecución por procesador.
     * @return Lista de enteros representando la asignación de tareas a procesadores en la llamada recursiva actual.
     */
    private List<Integer> backtracking(CustomLinkedList<Tarea> tasks,
                                       CustomLinkedList<Procesador> processors,
                                       ArrayList<Integer> solution,
                                       Node<Tarea> currentTask,
                                       Node<Procesador> firstProcessor,
                                       int[][] processorsTracker){
        if (solution.size() == tasks.getLength() && isBetterSolution(processorsTracker)){
            this.bestExecutionTime = findMaxExecutionTime(processorsTracker);
            this.bestSolution = new ArrayList<>(solution);
        } else {
            Integer processorPos = 0;
            Node<Procesador> currentProcessor = firstProcessor;
            while(solution.size() < tasks.getLength() && processorPos < processors.getLength()){

                solution.add(processorPos); // asignamos el procesador 0 al index de la tarea[pos]
                this.eventsCounter++;

                boolean taskIsCritical = currentTask.getData().isCritica();
                boolean processorIsRefrigerated = currentProcessor.getData().isRefrigerado();
                int taskTime = currentTask.getData().getTiempo();

                if (validateSolution(processorsTracker, processorPos, taskIsCritical,
                        taskTime, processorIsRefrigerated)) {
                    this.updateEvent(processorsTracker, processorPos, taskIsCritical, taskTime);

                    backtracking(tasks, processors, solution, currentTask.getNext(), firstProcessor, processorsTracker);

                    this.undoEvent(processorsTracker, processorPos, taskIsCritical, taskTime);
                }

                solution.remove(solution.size()-1); // self-cleanup

                processorPos++;
                currentProcessor = currentProcessor.getNext();
            }
        }
        return solution; // first found or empty
    }

    private int findMaxExecutionTime(int[][] processorsTracker) {
        int res = -1;
        for (int i = 0; i < processorsTracker.length; i++) {
            if (processorsTracker[i][POS_EXECUTION_TIME] > res)
                res = processorsTracker[i][POS_EXECUTION_TIME];
        }
        return res;
    }

    private boolean isBetterSolution(int[][] processorsTracker) {
        int currentExecutionTime = findMaxExecutionTime(processorsTracker);
        return currentExecutionTime < this.bestExecutionTime;
    }

    private boolean validateSolution(int[][] processorsTracker,
                                     Integer processorPos,
                                     boolean taskIsCritical,
                                     int taskTime,
                                     boolean processorIsRefrigerated){
        int newExecutionTime = processorsTracker[processorPos][POS_EXECUTION_TIME] + taskTime;
        return (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados);
    }

    private void updateEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical,int taskTime){
        processorsTracker[processorPos][POS_EXECUTION_TIME] += taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]++;
    }

    private void undoEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical, int taskTime){
        processorsTracker[processorPos][POS_EXECUTION_TIME] -= taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]--;
    }

    private String processSolutionOutput(List<Integer> solution,
                                       CustomLinkedList<Tarea> tasks,
                                       CustomLinkedList<Procesador> processors){
        String[] processorOut = new String[processors.getLength()];
        int pos = 0; // parallel index

        for (Node<Procesador> processor : processors){
            processorOut[pos] = processor.getData().getId() + ": ";
            pos++;
        }

        pos = 0; // reset index
        for (Node<Tarea> task : tasks){
            processorOut[solution.get(pos)] += task.getData().getId() + "-";
            pos++;
        }

        String res = "";
        for (String pOut : processorOut){
            res += pOut.substring(0, (pOut.length()-1)) + " | ";
        }

        return res.substring(0, res.length()-3);
    }

}
