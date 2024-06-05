package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingTaskProcessor {
    private int tiempoMaxEjecucionNoRefrigerados, maxAdmittedCriticalTasks, eventsCounter;
    private int bestExecutionTime;
    private ArrayList<Integer> bestSolution;

    public BacktrackingTaskProcessor(int tiempoMaxEjecucionNoRefrigerados){
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    public List<Integer> backtracking(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors) {
        ArrayList<Integer> solucion = new ArrayList<>();
        this.eventsCounter = 0;
        this.bestSolution = new ArrayList<>();
        this.bestExecutionTime = Integer.MAX_VALUE;

        int[][] processorsTracker = new int[processors.getLength()][2];
        // [i][j]
        // pos-j 0 trackea # criticos, pos-j 1 trackea tiempo de ejecucion
        // pos-i trackea el procesador
        backtracking(tasks, processors, solucion, tasks.getFirst(), processors.getFirst(), processorsTracker);

        if (bestSolution.isEmpty()){
            System.out.println("No solution could be found");
            return null;
        }

        String solutionString = processSolutionOutput(bestSolution, tasks, processors);

        System.out.println(String.format("Solucion obtenida: %s", solutionString));
        System.out.println(String.format("Tiempo maximo de ejecucion: %s", bestExecutionTime));
        System.out.println(String.format("Costo de la solucion: %s", this.eventsCounter));

        return bestSolution; // TODO: return new Solucion {camino, tiempo, eventos}
    }

    private int findMaxExecutionTime(int[][] processorsTracker) {
        int res = -1;
        for (int i = 0; i < processorsTracker.length; i++) {
            if (processorsTracker[i][1] > res)
                res = processorsTracker[i][1];
        }
        return res;
    }

    // Procesador : P1-T2, P2-T4

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
//                    System.out.println(String.format("Valide el procesador %s para la task %s",
//                            currentProcessor.getData().getId(), currentTask.getData().getId()));
//                    System.out.println(String.format("Estado: %s", solution));

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

    private boolean isBetterSolution(int[][] processorsTracker) {
        int currentExecutionTime = findMaxExecutionTime(processorsTracker);
        return currentExecutionTime < this.bestExecutionTime;
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

    private void updateEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical,int taskTime){
        processorsTracker[processorPos][1] += taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][0]++;
    }

    private void undoEvent(int[][] processorsTracker, Integer processorPos, boolean taskIsCritical, int taskTime){
        processorsTracker[processorPos][1] -= taskTime;
        if (taskIsCritical)
            processorsTracker[processorPos][0]--;
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
