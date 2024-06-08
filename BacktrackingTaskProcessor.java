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

    public BacktrackingTaskProcessor(int tiempoMaxEjecucionNoRefrigerados) {
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    /**
     * Backtracking publico:
     * - Reinicializa propiedades de clase destinadas a encontrar la mejor solucion: eventsCounter, bestSolution &
     * bestExecutionTime
     * - Genera arreglos auxiliares destinados a dar seguimiento a la solucion actual: solucion & processorsTracker
     * - Llama recursivamente al backtracking privado, a fin de encontrar la mejor solucion.
     * - Verifica que el resultado obtenida en bestSolution no este vacia, indicando y devolviendo null si lo es.
     * - Encapsula el resultado final en la clase Solucion, lo imprime y lo devuelve.
     *
     * @param tasks      CustomLinkedList de Tarea que contiene las tareas a ser asignadas.
     * @param processors CustomLinkedList de Procesador que contiene los procesadores a los cuales asignar tareas.
     * @return Solucion representando la mejor asignacion de tareas a procesadores, su tiempo maximo de ejecucion,
     * el costo aproximado de su calculo y la representacion en String del resultado. De no encontrarse solucion,
     * retorna null.
     */
    public Solucion backtracking(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors) {
        this.eventsCounter = 0;
        this.bestSolution = new ArrayList<>();
        this.bestExecutionTime = Integer.MAX_VALUE;

        ArrayList<Integer> solucion = new ArrayList<>();
        int[][] processorsTracker = new int[processors.getLength()][2];
        /**
         * @int[i][j] processorsTracker es un arreglo auxiliar para dar seguimiento a la solucion parcial
         *      en todo momento.
         *      - El indice [i][] refleja la posicion del Procesador en processors
         *      - El indice [][j] distingue:
         *          -- [][POS_CRITICALS] cuantas tareas criticas lleva asignado el procesador i.
         *          -- [][POS_EXECUTION_TIME] el tiempo de ejecucion para el procesador i dada la solucion actual.
         */

        backtracking(tasks, processors, solucion, tasks.getFirst(), processors.getFirst(), processorsTracker);

        if (bestSolution.isEmpty()) {
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
     * Backtracking privado: Recursivamente, intenta asignar cada tarea a cada procesador y evalua la asignacion.
     * - Analiza si la solucion actual es completa, y si es mejor que la ultima solucion registrada.
     * - Si lo es, registra la solucion actual como la mejor hasta el momento.
     * - Caso contrario, mientras la solucion sea incompleta, recorre los procesadores evaluando su validez.
     * De ser soluciones validas, las calcula y evalua la siguiente tarea de manera recursiva. Luego deshace el
     * calculo y continua evaluando los siguientes posibles procesadores.
     *
     * @param tasks             CustomLinkedList de Tarea que contiene las tareas a ser asignadas.
     * @param processors        CustomLinkedList de Procesador que contiene los procesadores a los cuales asignar tareas.
     * @param solution          arreglo auxiliar para el seguimiento de la solucion actual.
     * @param currentTask       Nodo de la tarea en analisis.
     * @param firstProcessor    Nodo del primer procesador en la lista de procesadores.
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                          tiempo de ejecucion de cada procesador para la solucion actual.
     */
    private void backtracking(CustomLinkedList<Tarea> tasks,
                              CustomLinkedList<Procesador> processors,
                              ArrayList<Integer> solution,
                              Node<Tarea> currentTask,
                              Node<Procesador> firstProcessor,
                              int[][] processorsTracker) {
        /**
         * Posible refactor:
         * - Reemplazar tasks por int tasksLength
         * - Quitar processors y usar processorsTracker.length en lugar de processors.getLength()
         */
        if (solution.size() == tasks.getLength() && isBetterSolution(processorsTracker)) {
            this.bestExecutionTime = findMaxExecutionTime(processorsTracker);
            this.bestSolution = new ArrayList<>(solution);
        } else {
            Integer processorPos = 0;
            Node<Procesador> currentProcessor = firstProcessor;
            while (solution.size() < tasks.getLength() && processorPos < processors.getLength()) {

                solution.add(processorPos); // asignamos el procesador processorPos al index de la tarea[pos]
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

                solution.remove(solution.size() - 1); // self-cleanup

                processorPos++;
                currentProcessor = currentProcessor.getNext();
            }
        }
        return;
    }

    /**
     * Busca la posicion del procesador con el tiempo de ejecucion mas alto. Si encuentra mas de uno,
     * devuelve el primero.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                          tiempo de ejecucion de cada procesador para la solucion actual.
     * @return la posicion del procesador con el tiempo de ejecucion mas alto. Si encuentra mas de uno,
     * devuelve el primero.
     */
    private int findMaxExecutionTime(int[][] processorsTracker) {
        int res = -1;
        for (int i = 0; i < processorsTracker.length; i++) {
            if (processorsTracker[i][POS_EXECUTION_TIME] > res)
                res = processorsTracker[i][POS_EXECUTION_TIME];
        }
        return res;
    }

    /**
     * Analiza si el tiempo de ejecucion de la solucion actual es menor al menor tiempo encontrado hasta el momento.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                          tiempo de ejecucion de cada procesador para la solucion actual.
     * @return si el tiempo de ejecucion de la solucion actual es menor al menor tiempo encontrado hasta el momento.
     */
    private boolean isBetterSolution(int[][] processorsTracker) {
        int currentExecutionTime = findMaxExecutionTime(processorsTracker);
        return currentExecutionTime < this.bestExecutionTime;
    }

    /**
     * @param processorsTracker       arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                                tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorPos            posicion del procesador en analisis.
     * @param taskIsCritical          indica si la tarea en consideracion es o no critica.
     * @param taskTime                tiempo de ejecucion de la tarea en consideracion.
     * @param processorIsRefrigerated si el procesador en analisis es o no refrigerado.
     * @return evalua si la tarea es critica, y si asi lo fuera comprueba que el procesador tiene espacio para
     * una nueva tarea critica. A su vez evalua si el procesador es refrigerado, y si asi lo fuera,
     * que el incipiente tiempo de ejecucion no supere el tiempo maximo de ejecucion para procesadores
     * no refrigerados.
     */
    private boolean validateSolution(int[][] processorsTracker,
                                     Integer processorPos,
                                     boolean taskIsCritical,
                                     int taskTime,
                                     boolean processorIsRefrigerated) {
        int newExecutionTime = processorsTracker[processorPos][POS_EXECUTION_TIME] + taskTime;
        return (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados);
    }

    /**
     * Actualiza el processorsTracker para el procesador en processorPos agregando el tiempo de ejecucion
     * de la tarea actual, e incrementando el contador de criticos si la tarea es critica.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                          tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorPos      posicion del procesador en analisis.
     * @param taskIsCritical    indica si la tarea en consideracion es o no critica.
     * @param taskTime          tiempo de ejecucion de la tarea en consideracion.
     */
    private void updateEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical, int taskTime) {
        processorsTracker[processorPos][POS_EXECUTION_TIME] += taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]++;
    }

    /**
     * Actualiza el processorsTracker para el procesador en processorPos restando el tiempo de ejecucion
     * de la tarea actual, y disminuyendo el contador de criticos si la tarea es critica.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *                          tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorPos      posicion del procesador en analisis.
     * @param taskIsCritical    indica si la tarea en consideracion es o no critica.
     * @param taskTime          tiempo de ejecucion de la tarea en consideracion.
     */
    private void undoEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical, int taskTime) {
        processorsTracker[processorPos][POS_EXECUTION_TIME] -= taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]--;
    }

    /**
     * @param solution   arreglo de indices que contiene que procesador fue asignado a cada tarea.
     * @param tasks      CustomLinkedList de Tarea que contiene las tareas a ser asignadas.
     * @param processors CustomLinkedList de Procesador que contiene los procesadores a los cuales asignar tareas.
     * @return String de la solucion: Pn: Tn-Tn |...
     */
    private String processSolutionOutput(List<Integer> solution,
                                         CustomLinkedList<Tarea> tasks,
                                         CustomLinkedList<Procesador> processors) {
        String[] processorOut = new String[processors.getLength()];
        int pos = 0; // parallel index

        for (Node<Procesador> processor : processors) {
            processorOut[pos] = processor.getData().getId() + ": ";
            pos++;
        }

        pos = 0; // reset index
        for (Node<Tarea> task : tasks) {
            processorOut[solution.get(pos)] += task.getData().getId() + "-";
            pos++;
        }

        String res = "";
        for (String pOut : processorOut) {
            res += pOut.substring(0, (pOut.length() - 1)) + " | ";
        }

        return res.substring(0, res.length() - 3);
    }

}
