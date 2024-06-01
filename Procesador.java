package tpe;

public class Procesador {
    private String id;
    private String codigo;
    private boolean esRefrigerado;
    private Integer anioFuncionamiento;

    public Procesador(String id, String codigo, boolean esRefrigerado, Integer anioFuncionamiento) {
        this.id = id;
        this.codigo = codigo;
        this.esRefrigerado = esRefrigerado;
        this.anioFuncionamiento = anioFuncionamiento;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isRefrigerado() {
        return esRefrigerado;
    }

    public Integer getAnioFuncionamiento() {
        return anioFuncionamiento;
    }
}
