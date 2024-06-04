package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class GreedyTaskProcessor {
    private int tiempoMaxEjecucionNoRefrigerados, maxAdmittedCriticalTasks, eventsCounter;
    private int bestExecutionTime;
    private ArrayList<Integer> bestSolution;

    public GreedyTaskProcessor(int tiempoMaxEjecucionNoRefrigerados){
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    public List<Integer> Greedy(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors){
        ArrayList<Integer> solucion = new ArrayList<>();
        this.eventsCounter = 0;
        this.bestSolution = new ArrayList<>();
        this.bestExecutionTime = Integer.MAX_VALUE;
        int[][] processorsTracker = new int[processors.getLength()][2];
        int posT = 0;
        int posP = 0;

        for(Node<Tarea> t : tasks){
            posP = 0;
            boolean crit = t.getData().isCritica();
            int taskT = t.getData().getTiempo();
            for(Node<Procesador> p : processors){
                solucion.add(posP);
                boolean ref = p.getData().isRefrigerado();
                if(validateSolution(processorsTracker,posP,crit,taskT,ref)){

                }
                // End
                posP++;
            }

            // End
            posT++;
        }
        return null;
    }

    private boolean validateSolution(int[][] processorsTracker,
                                     Integer processorPos,
                                     boolean taskIsCritical,
                                     int taskTime,
                                     boolean processorIsRefrigerated){
        int newExecutionTime = processorsTracker[processorPos][1] + taskTime;
//        System.out.println(String.format("processorTracker: %s %s",
//                processorsTracker[processorPos][0], processorsTracker[processorPos][1]));
        if (
                (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                        && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados) ){
            return true;
        }
        return false;
    }

    private boolean isBetterSolution(int[][] processorsTracker) {
        int currentExecutionTime = findMaxExecutionTime(processorsTracker);
        return currentExecutionTime < this.bestExecutionTime;
    }

    private int findMaxExecutionTime(int[][] processorsTracker) {
        int res = -1;
        for (int i = 0; i < processorsTracker.length; i++) {
            if (processorsTracker[i][1] > res)
                res = processorsTracker[i][1];
        }
        return res;
    }
}
