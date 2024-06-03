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

    public List<Integer> backtracking(CustomLinkedList<Tarea> tasks, CustomLinkedList<Procesador> processors) {
        ArrayList<Integer> solucion = new ArrayList<>();
        System.out.println(solucion);

        int[][] processorsTracker = new int[processors.getLength()][2];
        // salio la direccion de memoria
        // [i][j]
        // pos-j 0 trackea # criticos, pos-j 1 trackea tiempo de ejecucion
        // pos-i trackea el procesador
        return backtracking(tasks, processors, solucion, tasks.getFirst(), processors.getFirst(), processorsTracker);
    }

    // Procesador : P1-T2, P2-T4
    // Oka, pero primero me centraria en ver q funcione. Despues el toString

    private List<Integer> backtracking(CustomLinkedList<Tarea> tasks,
                                       CustomLinkedList<Procesador> processors,
                                       ArrayList<Integer> solution,
                                       Node<Tarea> currentTask,
                                       Node<Procesador> firstProcessor,
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
            Node<Procesador> currentProcessor = firstProcessor;
            while(solution.size() < tasks.getLength() && processorPos < processors.getLength()){
                // while Objects.nonNull(currentTask)
                // currentTask = currentTask.getNext(); posibles variaciones del while
                solution.add(processorPos); // asignamos el procesador 0 a la pos de la tarea[pos]
                int newExecutionTime = processorsTracker[processorPos][1] + currentTask.getData().getTiempo(); // revisar
                boolean taskIsCritical = currentTask.getData().isCritica();
                boolean processorIsRefrigerated = currentProcessor.getData().isRefrigerado();

                if (validateSolution(solution, currentTask, currentProcessor, processorsTracker)) {
                    System.out.println(String.format("Estado: %s", solution));
                    backtracking(tasks, processors, solution, currentTask.getNext(), firstProcessor, processorsTracker);

                    if (solution.size() == tasks.getLength())
                        return solution; // corta para evitar los remove

                    processorsTracker[processorPos][1] -= currentTask.getData().getTiempo();
                    if (taskIsCritical)
                        processorsTracker[processorPos][0]--;
                }
                solution.remove(solution.size()-1);
                processorPos++;
                currentProcessor = currentProcessor.getNext();
            }
        }
        return solution; // first found or empty
    }

    private boolean validateSolution(ArrayList<Integer> solution,
                                     Node<Tarea> currentTask,
                                     Node<Procesador> currentProcessor,
                                     int[][] processorsTracker){
        Integer processorPos = solution.get(solution.size()-1); // podriamos pasar directamente el processor pos como argumento
        int newExecutionTime = processorsTracker[processorPos][1] + currentTask.getData().getTiempo(); // revisar
        boolean taskIsCritical = currentTask.getData().isCritica();
        boolean processorIsRefrigerated = currentProcessor.getData().isRefrigerado();
        System.out.println(String.format("processorTracker: %s %s", processorsTracker[processorPos][0], processorsTracker[processorPos][1]));
        if (
                (!taskIsCritical || processorsTracker[processorPos][0] < this.maxAdmittedCriticalTasks)
                && (processorIsRefrigerated || newExecutionTime <= this.tiempoMaxEjecucionNoRefrigerados) ){
            if (taskIsCritical)
                processorsTracker[processorPos][0]++;
            processorsTracker[processorPos][1] = newExecutionTime;
            System.out.println(String.format("Valide el procesador %s para la task %s", currentProcessor.getData().getId(), currentTask.getData().getId()));
            return true;
        }
        return false;
    }

    private void solutionOutput(List<Integer> solution){
        // nada, lo deje de lado
        // processorMatrix
        for (int taskPos = 0; taskPos < solution.size(); taskPos++) {
            Integer processorPos = solution.get(taskPos);
            // processorMatrix.get(processorPos).add(taskPos)
        }
    }

}
