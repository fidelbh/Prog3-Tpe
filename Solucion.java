package tpe;

import java.util.ArrayList;

public class Solucion {
    private final String solucion;
    private final int tiempoMaxEjecucion;
    private final int costo;
    private final ArrayList<Integer> indexProcesadores;

    public Solucion(String solucion, int tiempoMaxEjecucion, int costo, ArrayList<Integer> indexProcesadores){
        this.solucion = solucion;
        this.tiempoMaxEjecucion = tiempoMaxEjecucion;
        this.costo = costo;
        this.indexProcesadores = new ArrayList<>(indexProcesadores);
    }

    @Override
    public String toString(){
        return String.format("Solucion obtenida: %s\n" +
                        "Tiempo maximo de ejecucion: %s\n" +
                        "Costo de la solucion: %s\n" +
                        "Indices de la solucion obtenida: %s",
                solucion, tiempoMaxEjecucion, costo, indexProcesadores);
    }
}
