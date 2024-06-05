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

    public Tarea(Tarea tarea){
        this.id = tarea.getId();
        this.nombre = tarea.getNombre();
        this.tiempo = tarea.getTiempo();
        this.prioridad = tarea.getPrioridad();
        this.critica = tarea.isCritica();
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

    @Override
    public String toString() {
        return "Tarea{ " +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tiempo=" + tiempo +
                ", prioridad=" + prioridad +
                ", critica=" + critica +
                "}";
    }
}