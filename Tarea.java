package tpe;


public class Tarea {
    private final String id, nombre;
    private final Integer tiempo, prioridad;
    private final Boolean critica;

    public Tarea(String id, String nombre, Integer tiempo, Integer prioridad, Boolean critica) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.prioridad = prioridad;
        this.critica = critica;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public Boolean isCritica() {
        return critica;
    }
}