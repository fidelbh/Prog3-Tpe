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
     Para la resolución de la segunda parte del TPE decidimos utilizar una estrategia Greedy, con el fin de
      buscar el procesador con menor tiempo maximo de ejecucion, y asignarle una tarea, logrando obtener el menor tiempo maximo de ejecucion
      en cada procesador. El metodo findQuickestProcessor es el encargado de esta tarea,
      que a su vez, verifica que no se encuentre en processorsToSkip, estructura que guarda aquellos procesadores no aptos para
      asignarles tareas.
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
            while(!foundProcessor){
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

        String solutionString = processSolutionOutput(solucion, tasks, processors);
        int maxExecutionTime = findMaxExecutionTime(processorsTracker);

        System.out.println("---GREEDY---");
        if (solucion.isEmpty()){
            System.out.println("No solution could be found");
            return null;
        }

        Solucion res = new Solucion(solutionString, maxExecutionTime, this.eventsCounter, solucion);
        System.out.println(res);
        return res;
    }

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

    private boolean validateSolution(int[][] processorsTracker,
                                     Integer processorPos,
                                     boolean taskIsCritical,
                                     int taskTime,
                                     boolean processorIsRefrigerated){
        int newExecutionTime = processorsTracker[processorPos][1] + taskTime;
        return (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados);
    }

    private void updateEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical,int taskTime){
        processorsTracker[processorPos][POS_EXECUTION_TIME] += taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][POS_CRITICALS]++;
    }

    private int findMaxExecutionTime(int[][] processorsTracker) {
        int res = -1;
        for (int i = 0; i < processorsTracker.length; i++) {
            if (processorsTracker[i][POS_EXECUTION_TIME] > res)
                res = processorsTracker[i][POS_EXECUTION_TIME];
        }
        return res;
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
