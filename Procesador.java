package tpe;
import java.util.Date;

public class Procesador {
    private int id_procesador;
    private int codigo_procesador;
    private boolean esta_refrigerado;
    private Date anio_funcionamiento;

    public Procesador(int id_procesador, int codigo_procesador, boolean esta_refrigerado, Date anio_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.anio_funcionamiento = anio_funcionamiento;
    }

    public int getId_procesador() {
        return id_procesador;
    }

    public int getCodigo_procesador() {
        return codigo_procesador;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public void setEsta_refrigerado(boolean esta_refrigerado) {
        this.esta_refrigerado = esta_refrigerado;
    }

    public Date getAnio_funcionamiento() {
        return anio_funcionamiento;
    }

    public void setAnio_funcionamiento(Date anio_funcionamiento) {
        this.anio_funcionamiento = anio_funcionamiento;
    }
}
