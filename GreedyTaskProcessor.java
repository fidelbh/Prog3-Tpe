package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class GreedyTaskProcessor {
    private int tiempoMaxEjecucionNoRefrigerados, maxAdmittedCriticalTasks, eventsCounter;
    private static final int POS_CRITICALS = 0;
    private static final int POS_EXECUTION_TIME = 1;

    public GreedyTaskProcessor(int tiempoMaxEjecucionNoRefrigerados){
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    /*
       Explicación de la estrategia:
       Para la resolución de la segunda parte del TPE decidimos utilizar una estrategia Greedy, en base a la estrategia
       de asignar cada nueva tarea al procesador que hasta el momento acumule el menor tiempo de ejecucion.
       El metodo findQuickestProcessor es el encargado de esta tarea, que a su vez, verifica que no se encuentre en
       processorsToSkip, estructura que guarda aquellos procesadores no aptos para asignarles tareas.
    */
    public Solucion Greedy(CustomLinkedList<Tarea> tasks,
                                CustomLinkedList<Procesador> processors){
        ArrayList<Integer> solucion = new ArrayList<>();
        this.eventsCounter = 0;
        int[][] processorsTracker = new int[processors.getLength()][2];
        int posP;
        ArrayList<Integer> processorsToSkip;

        for(Node<Tarea> t : tasks){
            processorsToSkip = new ArrayList<>();
            boolean crit = t.getData().isCritica();
            int taskT = t.getData().getTiempo();
            boolean foundProcessor = false;
            while(!foundProcessor && processorsTracker.length != processorsToSkip.size()){
                posP = findQuickestProcessor(processorsTracker, processorsToSkip);
                boolean isRefrigerated = processors.getNodeBy(posP).getData().isRefrigerado();

                if (validateSolution(processorsTracker, posP, crit, taskT, isRefrigerated)){
                    solucion.add(posP);
                    updateEvent(processorsTracker, posP, crit, taskT);
                    foundProcessor = true;
                } else
                    processorsToSkip.add(posP);
            }
        }

        System.out.println("---GREEDY---");
        if (solucion.isEmpty()){
            System.out.println("No solution could be found");
            return null;
        }

        String solutionString = processSolutionOutput(solucion, tasks, processors);
        int maxExecutionTime = findMaxExecutionTime(processorsTracker);

        Solucion res = new Solucion(solutionString, maxExecutionTime, this.eventsCounter, solucion);
        System.out.println(res);
        return res;
    }

    /**
     * Busca la posicion del procesador con menor tiempo de ejecucion hasta el momento, sin cosiderar validos
     * aquellos presentes en el arreglo processorsToSkip.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *      tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorsToSkip arreglo de procesadores a no considerar.
     * @return la posicion del procesador con menor tiempo de ejecucion, o -1 si ninguno cumple las condiciones.
     */
    private int findQuickestProcessor(int[][] processorsTracker,
                                      ArrayList<Integer> processorsToSkip) {
        int res = -1;
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < processorsTracker.length; i++) {
            eventsCounter++;
            if (processorsTracker[i][POS_EXECUTION_TIME] < time && !processorsToSkip.contains(i)){
                time = processorsTracker[i][POS_EXECUTION_TIME];
                res = i;
            }
        }
        return res;
    }

    /**
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *      tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorPos posicion del procesador en analisis.
     * @param taskIsCritical indica si la tarea en consideracion es o no critica.
     * @param taskTime tiempo de ejecucion de la tarea en consideracion.
     * @param processorIsRefrigerated si el procesador en analisis es o no refrigerado.
     * @return evalua si la tarea es critica, y si asi lo fuera comprueba que el procesador tiene espacio para
     *      una nueva tarea critica. A su vez evalua si el procesador es refrigerado, y si asi lo fuera,
     *      que el incipiente tiempo de ejecucion no supere el tiempo maximo de ejecucion para procesadores
     *      no refrigerados.
     */
    private boolean validateSolution(int[][] processorsTracker,
                                     Integer processorPos,
                                     boolean taskIsCritical,
                                     int taskTime,
                                     boolean processorIsRefrigerated){
        int newExecutionTime = processorsTracker[processorPos][1] + taskTime;
        return (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados);
    }

    /**
     * Actualiza el processorsTracker para el procesador en processorPos agregando el tiempo de ejecucion
     * de la tarea actual, e incrementando el contador de criticos si la tarea es critica.
     *
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *      tiempo de ejecucion de cada procesador para la solucion actual.
     * @param processorPos posicion del procesador en analisis.
     * @param taskIsCritical indica si la tarea en consideracion es o no critica.
     * @param taskTime tiempo de ejecucion de la tarea en consideracion.
     */
    private void updateEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical,int taskTime){
        processorsTracker[processorPos][POS_EXECUTION_TIME] += taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]++;
    }

    /**
     * Busca la posicion del procesador con el tiempo de ejecucion mas alto. Si encuentra mas de uno,
     * devuelve el primero.
     * @param processorsTracker arreglo auxiliar para el seguimiento de la cantidad de tareas criticas y
     *      tiempo de ejecucion de cada procesador para la solucion actual.
     * @return la posicion del procesador con el tiempo de ejecucion mas alto. Si encuentra mas de uno,
     *      devuelve el primero.
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
     *
     * @param solution arreglo de indices que contiene que procesador fue asignado a cada tarea.
     * @param tasks CustomLinkedList de Tarea que contiene las tareas a ser asignadas.
     * @param processors CustomLinkedList de Procesador que contiene los procesadores a los cuales asignar tareas.
     * @return String de la solucion: Pn: Tn-Tn |...
     */
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
