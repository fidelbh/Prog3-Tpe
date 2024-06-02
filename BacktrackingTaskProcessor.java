package tpe;

import tpe.utils.CustomLinkedList;
import tpe.utils.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BacktrackingTaskProcessor {
    private int tiempoMaxEjecucionNoRefrigerados, maxAdmittedCriticalTasks;

    public BacktrackingTaskProcessor(int tiempoMaxEjecucionNoRefrigerados){
        this.tiempoMaxEjecucionNoRefrigerados = tiempoMaxEjecucionNoRefrigerados;
        this.maxAdmittedCriticalTasks = 2;
    }

    public void findSolution(){
        //Si la configuración de entrada de tareas y procesadores no permite que todas las tareas se ejecuten
        // entonces no hay solución posible, y eso es lo que debería indicar el algoritmo en cuestión.
        System.out.println(String.format("Solucion obtenida: %s"));
        System.out.println(String.format("Tiempo maximo de ejecucion: %s"));
        System.out.println(String.format("Costo de la solucion: %s"));
    }

    private int maxExeTime(){ return 0; }

    private List<Integer> backtracking(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors) {
        ArrayList<Integer> solucion = new ArrayList<>();
        int[][] processorsTracker = new int[processors.getLength()][2];
        return backtracking(tasks, processors, solucion, tasks.getFirst(), processors.getFirst(), processorsTracker);
    }

    private List<Integer> backtracking(CustomLinkedList<Tarea> tasks,
                                       CustomLinkedList<Procesador> processors,
                                       ArrayList<Integer> solution,
                                       Node<Tarea> currentTask,
                                       Node<Procesador> currentProcessor,
                                       int[][] processorsTracker){
        /*
        if (solution)
             return
        else
            while
                agregarSiguiente
                if (podar)
                    backtracking(++)
                quitarSiguiente

        return
        */

        if (solution.size() == tasks.getLength() || Objects.isNull(currentTask)) // solo llega con init y despues de validar la solution
            return solution; // first found
        else{
            Integer processorPos = 0;
            while(solution.size() < tasks.getLength() && processorPos < processors.getLength()){
                // while Objects.nonNull(currentTask)
                // currentTask = currentTask.getNext();
                solution.add(processorPos); // asignamos el procesador 0 a la pos de la tarea[pos]
                if (validateSolution(solution, currentTask, currentProcessor,processorsTracker)) {
                    backtracking(tasks, processors, solution, currentTask.getNext(), currentProcessor.getNext(), processorsTracker);
                    //backtracking(tasks, processors, solution, currentTask);
                    if (solution.size() == tasks.getLength())
                        return solution;
                }
                solution.remove(solution.size()-1);
                processorPos++;
            }
        }
        return solution; // first found or empty
    }

    private boolean validateSolution(ArrayList<Integer> solution,
                                     Node<Tarea> currentTask,
                                     Node<Procesador> currentProcessor,
                                     int[][] processorsTracker){
        Integer processorPos = solution.get(solution.size()-1);
        int newExecutionTime = processorsTracker[processorPos][1] + currentTask.getData().getTiempo();
        boolean taskIsCritical = currentTask.getData().isCritica();
        boolean processorIsRefrigerated = currentProcessor.getData().isRefrigerado();

        if ((
                (taskIsCritical && processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks) ||
                (!taskIsCritical && processorsTracker[processorPos][0] <= this.maxAdmittedCriticalTasks))
                && ((processorIsRefrigerated && newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados) ||
                !processorIsRefrigerated)){
            if (taskIsCritical)
                processorsTracker[processorPos][0]++;
            processorsTracker[processorPos][1] = newExecutionTime;
            return true;
        }
        return false;
    }

    private void solutionOutput(List<Integer> solution){
        // processorMatrix
        for (int taskPos = 0; taskPos < solution.size(); taskPos++) {
            Integer processorPos = solution.get(taskPos);
            // processorMatrix.get(processorPos).add(taskPos)
        }
    }

}
